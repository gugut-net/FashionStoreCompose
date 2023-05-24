package com.cyberwalker.fashionstore.contactus


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.ui.theme.medium_18
import com.facebook.appevents.codeless.internal.UnityReflection.sendMessage


@Composable
fun SupportScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: SupportScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        SupportScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(bottom = 16.dp),
            onAction = onAction,
            onSendMessage = { message ->
                sendMessage(message, "unityMethod")
            }
        )
    }
}

@Composable
private fun SupportScreenContent(
    modifier: Modifier,
    onAction: (actions: SupportScreenActions) -> Unit,
    onSendMessage: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(messages) { index, message ->
                Text(
                    text = message.text,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Divider()

        MessageInput(onSendMessage = onSendMessage)
    }
}


// Function to send the message and perform necessary operations
fun sendMessage(message: String, unityMethod: String) {
    // Implement your logic to send the message and perform necessary operations
    // Use the 'message' parameter to access the entered message text
    println("Sending message: $message")

    // Use the 'unityMethod' parameter as needed
    println("Unity method to call: $unityMethod")

    // Add your custom logic here to send the message and perform necessary operations
    messages.add(Message(message))
}


@Composable
fun MessagesList(messages: List<Message>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (message in messages) {
            Text(text = message.text)
        }
    }
}

@Composable
fun MessageInput(onSendMessage: (String) -> Unit) {
    var inputText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            singleLine = true,
            placeholder = { Text(text = "Type a message...") }
        )
        Button(
            onClick = {
                onSendMessage(inputText)
                inputText = ""
            },
            enabled = inputText.isNotBlank()
        ) {
            Text(text = "Send")
        }
    }
}

// Data class representing a message
data class Message(val text: String)

// Example list of messages
val messages = mutableListOf(
    Message("Hello"),
    Message("How are you?"),
    Message("I'm good, thank you!")
)

@Composable
fun SupportScreenPreview() {
    SupportScreen(
        navController = rememberNavController(),
        onAction = {},
        scrollState = rememberScrollState()
    )
}

sealed class SupportScreenActions {
    object LoadSupport : SupportScreenActions()
}
