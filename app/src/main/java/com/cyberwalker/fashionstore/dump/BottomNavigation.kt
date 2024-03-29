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
package com.cyberwalker.fashionstore.dump

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.ui.theme.bottomNavbg
import com.cyberwalker.fashionstore.ui.theme.highlight

enum class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home_feed, Icons.Outlined.Home, "home"),
    SEARCH(R.string.home_search, Icons.Outlined.Search, "search"),
    LIKED(R.string.home_liked, Icons.Outlined.Favorite, "liked"),
    PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, "profile")
}

@Composable
fun BottomNav(modifier: Modifier = Modifier, navController: NavController, isDark: Boolean = isSystemInDarkTheme()) {
    val items = listOf(
        BottomNavItem.HOME,
        BottomNavItem.SEARCH,
        BottomNavItem.LIKED,
        BottomNavItem.PROFILE,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.bottomNavbg,
        contentColor = highlight
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.title)) },
                selectedContentColor = highlight,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    when(item) {
                        BottomNavItem.HOME -> {
                            navController.navigate(BottomNavItem.HOME.route)
                        }
                        BottomNavItem.SEARCH -> {
                            navController.navigate(BottomNavItem.SEARCH.route)
                        }
                        BottomNavItem.LIKED -> {
                            navController.navigate(BottomNavItem.LIKED.route)
                        }
                        BottomNavItem.PROFILE -> {
                            navController.navigate(BottomNavItem.PROFILE.route)
                        }
                    }
                }
            )
        }
    }
}