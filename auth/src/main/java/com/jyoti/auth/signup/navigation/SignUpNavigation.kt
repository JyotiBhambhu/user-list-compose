package com.jyoti.auth.signup.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jyoti.auth.signup.ui.SignUpRoute

const val signUpNavigationRoute = "sign_up_route"

fun NavController.navigateToSignUp() {
    this.navigate(signUpNavigationRoute)
}

fun NavGraphBuilder.signUpScreen(
    navigateUp: () -> Unit, showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    composable(route = signUpNavigationRoute) {
        SignUpRoute(
            navigateUp = navigateUp,
            showSnackBar = showSnackBar
        )
    }
}