package com.cyberwalker.fashionstore.ui.search

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.dump.BottomNav
import com.cyberwalker.fashionstore.ui.theme.medium_18


@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: SearchScreenActions) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
    navController: NavHostController
) {
    val searchQueryState = remember { mutableStateOf("") }
    val searchDropdownOptions = listOf("Option 1", "Option 2", "Option 3") // Replace with your dropdown options

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        SearchScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            onAction = onAction,
            searchQuery = searchQueryState.value,
            onSearchQueryChange = { searchQueryState.value = it },
            searchDropdownOptions = searchDropdownOptions,
            onSearchOptionSelected = { selectedOption ->
                // Handle selected option
            }
        )
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier,
    onAction: (actions: SearchScreenActions) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchDropdownOptions: List<String>,
    onSearchOptionSelected: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .semantics { contentDescription = "Search Screen" }
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Search", style = MaterialTheme.typography.medium_18)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text(text = "Search query") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                IconButton(
                    onClick = { onSearchQueryChange("") } // Clear search query
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        )
        DropdownMenu(
            expanded = false, // Replace with your state for dropdown visibility
            onDismissRequest = { /* Handle dismiss request */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            searchDropdownOptions.forEach { option ->
                DropdownMenuItem(onClick = {
                    onSearchOptionSelected(option)
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

sealed class SearchScreenActions {
    object LoadSearch : SearchScreenActions()
}