package com.cyberwalker.fashionstore.profile

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
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.ui.theme.medium_18

@Composable
fun EditProfileScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: EditProfileScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        EditProfileScreenContent(modifier = Modifier
            .padding(innerPadding)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
            onAction = onAction)
    }
}
@Composable
private fun EditProfileScreenContent(
    modifier: Modifier,
    onAction: (actions: EditProfileScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .semantics { contentDescription = "Liked Screen" }
            .verticalScroll(rememberScrollState())
    ){
        Text(text = "Edit Your Profile", style = MaterialTheme.typography.medium_18)

    }

}

sealed class EditProfileScreenActions {
    object LoadEditProfile : EditProfileScreenActions()
}