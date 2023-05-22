package com.cyberwalker.fashionstore.cart

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
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
fun CartScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: CartScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        CartScreenContent(modifier = Modifier
            .padding(innerPadding)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
            onAction = onAction)
    }
}
@Composable
private fun CartScreenContent(
    modifier: Modifier,
    onAction: (actions: CartScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .semantics { contentDescription = "Cart Screen" }
            .verticalScroll(rememberScrollState())
    ){
        Text(text = "Shopping Car", style = MaterialTheme.typography.medium_18)

    }

}

sealed class CartScreenActions {
    object LoadCart : CartScreenActions()
}