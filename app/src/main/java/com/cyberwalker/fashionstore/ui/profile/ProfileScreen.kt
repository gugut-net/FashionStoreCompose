package com.cyberwalker.fashionstore.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.ui.theme.medium_18


@Composable
fun ProfileScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: ProfileScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        ProfileScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            onAction = onAction, navController = navController
        )
    }
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier,
    onAction: (actions: ProfileScreenActions) -> Unit,
    navController: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .semantics { contentDescription = "Profile Screen" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.size(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Profile", style = MaterialTheme.typography.medium_18)
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .clickable { /* Handle avatar click */ },
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_4),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* Handle profile update */
                                onAction(ProfileScreenActions.LoadEditProfile)
                                navController.navigate(Screen.EditProfile.route)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Edit Profile")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle payment method click */
                            onAction(ProfileScreenActions.LoadPayment)
                            navController.navigate(Screen.PaymentMethod.route)
                        },
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Payment Method")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle shopping history click */
                            onAction(ProfileScreenActions.LoadShoppingHistory)
                            navController.navigate(Screen.PaymentMethod.route)},
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Shopping History")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle shopping history click */
                            onAction(ProfileScreenActions.LoadSettings)
                            navController.navigate(Screen.PaymentMethod.route)},
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Settings")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle shopping history click */ },
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Shopping History")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle shopping history click */
                            onAction(ProfileScreenActions.LoadSupport)
                            navController.navigate(Screen.SupportScreen.route)},
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Contact us")
                    }
                }
            }
        }
    }
}

sealed class ProfileScreenActions {
    object LoadPayment : ProfileScreenActions()
    object LoadEditProfile : ProfileScreenActions()
    object LoadShoppingHistory : ProfileScreenActions()
    object LoadSettings : ProfileScreenActions()
    object LoadSupport : ProfileScreenActions()
}

