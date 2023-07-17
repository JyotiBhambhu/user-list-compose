package com.jyoti.user.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.jyoti.auth.login.navigation.loginNavigationRoute
import com.jyoti.auth.login.navigation.loginScreen
import com.jyoti.auth.signup.navigation.navigateToSignUp
import com.jyoti.auth.signup.navigation.signUpScreen
import com.jyoti.user.contacts.addcontact.navigation.addContactScreen
import com.jyoti.user.contacts.addcontact.navigation.navigateToAddContactGraph
import com.jyoti.user.contacts.contactlist.navigation.contactsGraph
import com.jyoti.user.contacts.contactlist.navigation.navigateToContactsGraph

@Composable
fun UserAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(
            navigateToHome = {
                navController.navigateToContactsGraph(
                    navOptions {
                        popUpTo(
                            loginNavigationRoute
                        ) { inclusive = true }
                    }
                )
            },
            onSignUpClicked = navController::navigateToSignUp
        )
        signUpScreen(
            navigateUp = navController::popBackStack,
        )
        contactsGraph(
            onClickAddUser = navController::navigateToAddContactGraph,
            nestedGraphs = {
                addContactScreen(navigateUp = navController::popBackStack, showSnackBar)
            }
        )
    }
}