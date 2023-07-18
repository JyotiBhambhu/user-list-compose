package com.jyoti.user.contacts.addcontact.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jyoti.user.contacts.addcontact.ui.AddContactRoute

const val addContactNavigationRoute = "add_contact_route"

fun NavController.navigateToAddContactScreen(navOptions: NavOptions? = null) {
    this.navigate(addContactNavigationRoute, navOptions)
}

fun NavGraphBuilder.addContactScreen(
    navigateUp: () -> Unit,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    composable(route = addContactNavigationRoute) {
        AddContactRoute(navigateUp = navigateUp, showSnackBar = showSnackBar)
    }
}