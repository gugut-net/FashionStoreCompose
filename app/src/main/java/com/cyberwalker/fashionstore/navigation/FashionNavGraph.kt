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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cyberwalker.fashionstore.card.PaymentScreen
import com.cyberwalker.fashionstore.card.PaymentScreenActions
import com.cyberwalker.fashionstore.cart.CartScreen
import com.cyberwalker.fashionstore.cart.CartScreenActions
import com.cyberwalker.fashionstore.cart.CartViewModel
import com.cyberwalker.fashionstore.ui.contactus.SupportScreen
import com.cyberwalker.fashionstore.ui.contactus.SupportScreenActions
import com.cyberwalker.fashionstore.detail.DetailScreen
import com.cyberwalker.fashionstore.detail.DetailScreenActions
import com.cyberwalker.fashionstore.dump.animatedComposable
import com.cyberwalker.fashionstore.home.HomeScreen
import com.cyberwalker.fashionstore.home.HomeScreenActions
import com.cyberwalker.fashionstore.liked.LikedScreen
import com.cyberwalker.fashionstore.liked.LikedScreenActions
import com.cyberwalker.fashionstore.login.SignInScreen
import com.cyberwalker.fashionstore.login.SignInScreenActions
import com.cyberwalker.fashionstore.login.SignUpScreen
import com.cyberwalker.fashionstore.login.SignUpScreenActions
import com.cyberwalker.fashionstore.ui.profile.EditProfileScreen
import com.cyberwalker.fashionstore.ui.profile.EditProfileScreenActions
import com.cyberwalker.fashionstore.ui.profile.ProfileScreen
import com.cyberwalker.fashionstore.ui.profile.ProfileScreenActions
import com.cyberwalker.fashionstore.promo.PromoScreen
import com.cyberwalker.fashionstore.promo.PromoScreenActions
import com.cyberwalker.fashionstore.ui.search.SearchScreen
import com.cyberwalker.fashionstore.ui.search.SearchScreenActions
import com.cyberwalker.fashionstore.splash.SplashScreen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

sealed class Screen(val name: String, val route: String) {

    object Splash : Screen("splash", "splash")
    object Home : Screen("home", "home")
    object Detail : Screen("detail", "detail")
    object SignIn: Screen("signin","signin")
    object SignUp: Screen("signup","signup")
    object Profile: Screen("profile", "profile")
    object Search: Screen("search", "search")
    object Liked: Screen("liked", "liked")
    object Promo: Screen("promo", "promo")
    object Cart: Screen("cart", "cart")
    object EditProfile : Screen("editProfile", "editProfile")
    object PaymentMethod : Screen("paymentMethod", "paymentMethod")
    object SupportScreen : Screen("support", "support")

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
            SplashScreen(
                onAction = actions::navigateToSignIn
            )
        }

        animatedComposable(Screen.SignIn.route) {
            SignInScreen(
                onAction = actions::navigateFromSignIn,
                onAction0 = actions::navigateToSignUp
            )
        }

        animatedComposable(Screen.SignUp.route) {
            SignUpScreen(
                onAction = actions::navigateToSignIn
            )
        }

        animatedComposable(Screen.Home.route) {
            HomeScreen(
                onAction = actions::navigateFromHome,
                navController = navController
            )
        }

        animatedComposable(Screen.Detail.route) {
            DetailScreen(
                onAction = actions::navigateFromDetails
            )
        }

        animatedComposable(Screen.Liked.route) {
            LikedScreen(
                onAction = actions::navigateToLiked,
                navController = navController
            )
        }

        animatedComposable(Screen.Cart.route) {
            val cartNavController = rememberNavController()
            val viewModel: CartViewModel = viewModel(
                factory = CartViewModel.provideFactory())
            CartScreen(
                onSnackClick = {},
                modifier = Modifier,
                viewModel = viewModel,
                onAction = actions::navigateToCart,
                navController = cartNavController
            )
        }

        animatedComposable(Screen.Search.route) {
            SearchScreen(
                onAction = actions::navigateToSearch,
                navController = navController
            )
        }

        animatedComposable(Screen.Profile.route) {
            ProfileScreen(
                onAction = actions::navigateToProfile,
                navController = navController
            )
        }

        animatedComposable(Screen.Promo.route) {
            PromoScreen(
                onAction = actions::navigateToPromo,
                navController = navController
            )
        }

        animatedComposable(Screen.EditProfile.route) {
            EditProfileScreen(
                onAction = actions::navigateToEditProfile,
                navController = navController
            )
        }

        animatedComposable(Screen.PaymentMethod.route) {
            PaymentScreen(
                onAction = actions::navigateToPaymentMethod,
                navController = navController
            )
        }

        animatedComposable(Screen.SupportScreen.route) {
            SupportScreen(
                onAction = actions::navigateToSupport,
                navController = navController,
                firebaseMessaging = Firebase.messaging
            )
        }
    }
}

class NavActions(private val navController: NavController) {
    fun navigateToSignIn(actions: SplashScreenActions) {
        navController.navigate(Screen.SignIn.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    fun navigateFromHome(actions: HomeScreenActions) {
        when (actions) {
            HomeScreenActions.Details -> {
                navController.navigate(Screen.Detail.route)
            }
        }
    }

    fun navigateFromDetails(actions: DetailScreenActions) {
        when (actions) {
            DetailScreenActions.Back -> navController.popBackStack()
        }
    }

    fun navigateToLiked(actions: LikedScreenActions) {
        when (actions) {
            LikedScreenActions.LoadLiked -> {
                navController.navigate(Screen.Liked.route)
            }
        }
    }

//    fun navigateToCart(actions: CartScreenActions) {
//        navController.navigate(Screen.Cart.route) // Navigate to the Cart screen
//    }

    fun navigateToCart(actions: CartScreenActions) {
        when (actions) {
            CartScreenActions.LoadCart -> {
                navController.navigate(Screen.Cart.route)
            }
        }
    }

    fun navigateToSearch(actions: SearchScreenActions) {
        when (actions) {
            SearchScreenActions.LoadSearch -> {
                navController.navigate(Screen.Search.route)
            }
        }
    }

    fun navigateToPromo(actions: PromoScreenActions) {
        when (actions) {
            PromoScreenActions.LoadPromo -> {
                navController.navigate(Screen.Promo.route)
            }
        }
    }

    fun navigateToProfile(actions: ProfileScreenActions) {
        when (actions) {
            ProfileScreenActions.LoadPayment -> {
                navController.navigate(Screen.Profile.route)
            }

            else -> {}
        }
    }

    fun navigateFromSignIn(actions: SignInScreenActions) {
        when (actions) {
            SignInScreenActions.LoadHome -> {
                navController.navigate(Screen.Home.route)
            }
        }
    }

    fun navigateToSignIn(actions: SignUpScreenActions) {
        when (actions) {
            SignUpScreenActions.LoadSignUp -> {
                navController.navigate(Screen.SignIn.route)
            }
        }
    }

    fun navigateToSignUp(actions: SignUpScreenActions) {
        navController.navigate(Screen.SignUp.route) {
            popUpTo(Screen.SignIn.route) {
                inclusive = true
            }
        }
    }

    fun navigateToEditProfile(actions: EditProfileScreenActions) {
        when (actions) {
            EditProfileScreenActions.LoadEditProfile -> {
                navController.navigate(Screen.EditProfile.route)
            }
        }
    }

    fun navigateFromProfile(actions: PaymentScreenActions) {
        when (actions) {
            PaymentScreenActions.LoadPayment -> {
                navController.navigate(Screen.PaymentMethod.route)
            }
        }
    }

    fun navigateToPaymentMethod(actions: PaymentScreenActions) {
        when (actions) {
            PaymentScreenActions.LoadPayment -> {
                navController.navigate(Screen.PaymentMethod.route)
            }
        }
    }

    fun navigateToSupport(actions: SupportScreenActions) {
        when (actions) {
            SupportScreenActions.LoadSupport -> {
                navController.navigate(Screen.SupportScreen.route)
            }
        }
    }
}