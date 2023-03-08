package com.cyberwalker.fashionstore.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.ui.theme.ltgray_dot
import com.cyberwalker.fashionstore.ui.theme.poppinsFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onAction: (actions: SignUpScreenActions) -> Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var verifyPassword by rememberSaveable { mutableStateOf("")}

    val scope = rememberCoroutineScope()
    val state by viewModel.signUpState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher),
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
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ltgray_dot,
                cursorColor = Color.Black,
                disabledLabelColor = ltgray_dot,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ), shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ltgray_dot,
                cursorColor = Color.Black,
                disabledLabelColor = ltgray_dot,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),

        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = verifyPassword,
            onValueChange = { verifyPassword = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ltgray_dot,
                cursorColor = Color.Black,
                disabledLabelColor = ltgray_dot,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            label = { Text(text = "Verify password") },
            visualTransformation = PasswordVisualTransformation(),
        )

        Button(
            onClick = {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onAction(SignUpScreenActions.LoadSignUp)
                            Log.d("Welcome", "Signup successful")
                        } else {
                            // Handle signup failure
                            Log.e("Sorry", "Signup failed: ${task.exception}")
                        }
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
                text = "Sign Up",
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
        Text(
            text = "Already have an account? Sign In",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = poppinsFamily,
            modifier = Modifier
                .clickable {
                    onAction(SignUpScreenActions.LoadSignUp)
                }
                .padding(15.dp)
        )
    }
}
sealed class SignUpScreenActions {
    object LoadSignUp : SignUpScreenActions()
}