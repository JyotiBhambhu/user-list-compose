package com.jyoti.auth.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginRoute(
    onSignUpClicked: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Not logged in")
        Button(onClick = navigateToHome) {
            Text(text = "Login")
        }
        Button(onClick = onSignUpClicked) {
            Text(text = "Sign up")
        }
    }
}