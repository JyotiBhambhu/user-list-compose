package com.jyoti.auth.login.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jyoti.auth.login.ui.LoginRoute

const val loginNavigationRoute = "login_route"

fun NavController.navigateToLogin() {
    this.navigate(loginNavigationRoute) {
        popUpTo(0)
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    onSignUpClicked: () -> Unit,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    composable(route = loginNavigationRoute) {
        LoginRoute(
            navigateToHome = navigateToHome,
            onSignUpClicked = onSignUpClicked,
            showSnackBar = showSnackBar
        )
    }
}