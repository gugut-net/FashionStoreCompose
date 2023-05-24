package com.cyberwalker.fashionstore.card

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.ui.theme.medium_18

@Composable
fun PaymentScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: PaymentScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        PaymentScreenContent(modifier = Modifier
            .padding(innerPadding)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
            onAction = onAction)
    }
}

@Composable
private fun PaymentScreenContent(
    modifier: Modifier,
    onAction: (actions: PaymentScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .semantics { contentDescription = "Payment Screen" }
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Payment", style = MaterialTheme.typography.medium_18)

        // Card Holder Name
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = "", // Provide the initial value here or use a state variable
            onValueChange = { /* Update the card holder name */ },
            label = { Text(text = "Card Holder Name") }
        )

        // Card Number
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = "", // Provide the initial value here or use a state variable
            onValueChange = { /* Update the card number */ },
            label = { Text(text = "Card Number") }
        )

        // Expiry Date and Year
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                value = "", // Provide the initial value here or use a state variable
                onValueChange = { /* Update the expiry date */ },
                label = { Text(text = "Expiry Date") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                value = "", // Provide the initial value here or use a state variable
                onValueChange = { /* Update the expiry year */ },
                label = { Text(text = "Expiry Year") }
            )
        }

        // CVV
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = "", // Provide the initial value here or use a state variable
            onValueChange = { /* Update the CVV */ },
            label = { Text(text = "CVV") }
        )

        // Add or Edit Card Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { /* Perform add card action */ }
            ) {
                Text(text = "Add Card")
            }
            TextButton(
                onClick = { /* Perform edit card action */ }
            ) {
                Text(text = "Edit Card")
            }
        }
    }
}

sealed class PaymentScreenActions {
    object LoadPayment : PaymentScreenActions()
}