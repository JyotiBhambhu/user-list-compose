package com.jyoti.user.contacts.contactlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jyoti.user.contacts.contactlist.ui.ContactsRoute

private const val contactsGraphRoutePattern = "contacts_graph"
const val contactsNavigationRoute = "user_contacts_route"

fun NavController.navigateToContactsGraph(navOptions: NavOptions? = null) {
    this.navigate(contactsGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.contactsGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = contactsGraphRoutePattern,
        startDestination = contactsNavigationRoute,
    ) {
        composable(route = contactsNavigationRoute) {
            ContactsRoute()
        }
        nestedGraphs()
    }
}