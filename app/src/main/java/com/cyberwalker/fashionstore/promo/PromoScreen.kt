package com.cyberwalker.fashionstore.promo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.detail.DetailScreenActions
import com.cyberwalker.fashionstore.detail.DetailViewModel

@Composable
fun PromoScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: PromoScreenActions) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        PromoScreenContent(modifier = Modifier.padding(innerPadding), onAction = onAction)
    }
}
@Composable
private fun PromoScreenContent(
    modifier: Modifier,
    onAction: (actions: PromoScreenActions) -> Unit
) {

}

sealed class PromoScreenActions {
    object LoadPromo : PromoScreenActions()
}