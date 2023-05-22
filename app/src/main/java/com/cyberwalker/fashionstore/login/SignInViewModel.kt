package com.cyberwalker.fashionstore.login


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.source.AuthRepository
import com.cyberwalker.fashionstore.utils.Resource
import com.facebook.AccessToken
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val auth = Firebase.auth

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser?> = _user

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

//    private val _facebookState = mutableStateOf(FacebookSignInState())
//    val facebookState: State<FacebookSignInState> = _facebookState


    init {
        auth.addAuthStateListener { firebaseAuth ->
            _user.value = firebaseAuth.currentUser
        }
    }

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        repository.googleSignIn(credential).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }

        }
    }

//    fun facebookSignIn(token: AccessToken) = viewModelScope.launch {
//        repository.facebookSignIn(token).collect { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _facebookState.value = FacebookSignInState(success = result.data)
//                }
//                is Resource.Loading -> {
//                    _facebookState.value = FacebookSignInState(loading = true)
//                }
//                is Resource.Error -> {
//                    _facebookState.value = FacebookSignInState(error = result.message!!)
//                }
//            }
//        }
//    }


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign In Success "))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {

                    _signInState.send(SignInState(isError = result.message))
                }
            }

        }
    }

    fun getUser(): String? {
        return auth.currentUser?.email
    }

}