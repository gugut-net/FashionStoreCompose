/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.cyberwalker.fashionstore.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.detail.DetailScreen
import com.cyberwalker.fashionstore.detail.DetailScreenActions
import com.cyberwalker.fashionstore.dump.animatedComposable
import com.cyberwalker.fashionstore.home.HomeScreen
import com.cyberwalker.fashionstore.home.HomeScreenActions
import com.cyberwalker.fashionstore.login.SignInScreen
import com.cyberwalker.fashionstore.login.SignInScreenActions
import com.cyberwalker.fashionstore.login.SignUpScreen
import com.cyberwalker.fashionstore.login.SignUpScreenActions
import com.cyberwalker.fashionstore.splash.SplashScreen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

sealed class Screen(val name: String, val route: String) {

    object Splash : Screen("splash", "splash")
    object Home : Screen("home", "home")
    object Detail : Screen("detail", "detail")
    object SignIn: Screen("signin","signin")
    object SignUp: Screen("signup","signup")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FashionNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    actions: NavActions = remember(navController) {
        NavActions(navController)
    }
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        animatedComposable(Screen.Splash.route) {
            SplashScreen(onAction = actions::navigateToHome)
        }
        animatedComposable(Screen.SignUp.route) {
            SignUpScreen(
                onAction = actions::navigateToSignIn
            )
        }

        animatedComposable(Screen.Home.route) {
            HomeScreen(onAction = actions::navigateFromHome,navController = navController)
        }

        animatedComposable(Screen.Detail.route) {
            DetailScreen(onAction = actions::navigateFromDetails)
        }

        animatedComposable(Screen.SignIn.route) {
            SignInScreen(
                onAction = actions::navigateFromSignIn,
                onAction0 = actions::navigateToSignUp)
        }
    }
}

class NavActions(private val navController: NavController) {
    fun navigateToHome(_A: SplashScreenActions) {
        navController.navigate(Screen.SignIn.name) {
            popUpTo(Screen.Splash.route){
                inclusive = true
            }
        }
    }

    fun navigateFromHome(actions: HomeScreenActions) {
        when (actions) {
            HomeScreenActions.Details -> {
                navController.navigate(Screen.Detail.name)
            }
        }
    }

    fun navigateFromDetails(actions: DetailScreenActions) {
        when(actions) {
            DetailScreenActions.Back -> navController.popBackStack()
        }
    }

    fun navigateFromSignIn(actions: SignInScreenActions) {
        when (actions) {
            SignInScreenActions.LoadHome -> {
                navController.navigate(Screen.Home.name)
            }
        }
    }
    fun navigateToSignIn(actions: SignUpScreenActions) {
        when (actions) {
            SignUpScreenActions.LoadSignUp -> {
                navController.navigate(Screen.SignIn.name)
            }
        }
    }

    fun navigateToSignUp(_A: SignUpScreenActions) {
        navController.navigate(Screen.SignUp.name) {
            popUpTo(Screen.SignIn.route){
                inclusive = true
            }
        }
    }
}