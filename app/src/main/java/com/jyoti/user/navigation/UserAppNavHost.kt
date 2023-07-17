package com.jyoti.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jyoti.user.contacts.navigation.contactsGraph
import com.jyoti.user.contacts.navigation.contactsNavigationRoute

@Composable
fun UserAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = contactsNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        contactsGraph(
            onClickAddUser = {},
            onLogout = {},
//            nestedGraphs = {
//            }
        )
    }
}