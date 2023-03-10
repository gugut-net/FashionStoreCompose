package com.cyberwalker.fashionstore.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.ui.theme.ltgray_dot
import com.cyberwalker.fashionstore.ui.theme.poppinsFamily
import com.cyberwalker.fashionstore.utils.Util.ServerClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    //navController : NavController,
    viewModel: SignInViewModel = hiltViewModel(),
    onAction: (actions: SignInScreenActions) -> Unit,
    onAction0: (actions: SignUpScreenActions) -> Unit
) {

    val googleSignInState = viewModel.googleState.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        }


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        item {
            Image(
                painter = painterResource
                    (id = R.mipmap.ic_launcher),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(200.dp)
                    .size(150.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .focusRequester(emailFocusRequester)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = ltgray_dot,
                    cursorColor = Color.Black,
                    disabledLabelColor = ltgray_dot,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = ltgray_dot,
                    cursorColor = Color.Black,
                    disabledLabelColor = ltgray_dot,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp), singleLine = true,
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Do something */ }),
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.loginUser(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    modifier = Modifier
                        .padding(7.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()
                }

            }
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .clickable {
                        onAction0(SignUpScreenActions.LoadSignUp)
                    },
                text = "New User? Sign Up ",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = poppinsFamily
            )
            Text(
                text = "or connect with",
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(ServerClient)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    launcher.launch(googleSignInClient.signInIntent)

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
//                            .size(100.dp)
//                            .width(50.dp)
//                            .height(20.dp),
                        tint = Color.Unspecified
                    )
                }
                LaunchedEffect(key1 = state.value?.isSuccess) {
                    scope.launch {
                        if (state.value?.isSuccess?.isNotEmpty() == true) {
                            val success = state.value?.isSuccess
                            onAction(SignInScreenActions.LoadHome)
                        }
                    }
                }

                LaunchedEffect(key1 = state.value?.isError) {
                    scope.launch {
                        if (state.value?.isError?.isNotEmpty() == true) {
                            val error = state.value?.isError
                            Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                LaunchedEffect(key1 = googleSignInState.success) {
                    scope.launch {
                        if (googleSignInState.success != null) {
                            Toast.makeText(context, "Sign In Success", Toast.LENGTH_LONG).show()
                            onAction(SignInScreenActions.LoadHome)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                if (googleSignInState.loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

sealed class SignInScreenActions {
    object LoadHome : SignInScreenActions()
}
