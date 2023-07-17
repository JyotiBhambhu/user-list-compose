package com.jyoti.user.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jyoti.user.contacts.ContactsRoute

//private const val contactsGraphRoutePattern = "contacts_graph"
const val contactsNavigationRoute = "user_contacts_route"

fun NavController.navigateToContactsGraph(navOptions: NavOptions? = null) {
    this.navigate(contactsNavigationRoute, navOptions)
}

fun NavGraphBuilder.contactsGraph(
    onClickAddUser: () -> Unit,
    onLogout: () -> Unit,
//    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
//    navigation(
//        route = contactsNavigationRoute,
//        startDestination = contactsNavigationRoute,
//    ) {
        composable(route = contactsNavigationRoute) {
            ContactsRoute(onClickAddUser = onClickAddUser, navigateToLogin = onLogout)
        }
//        nestedGraphs()
//    }
}