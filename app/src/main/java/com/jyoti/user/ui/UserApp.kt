package com.jyoti.user.ui

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.jyoti.core.network.NetworkMonitor
import com.jyoti.designsystem.component.UserTopAppBar
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.user.navigation.UserAppNavHost

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun UserApp(
    networkMonitor: NetworkMonitor,
    appState: UserAppState = rememberUserAppState(
        networkMonitor = networkMonitor,
    ),
){
    com.jyoti.designsystem.theme.UserAppBackground {

        val activity = LocalView.current.context as Activity
        val backgroundArgb = com.jyoti.designsystem.theme.ColorPrimary.Base.id.toArgb()
        activity.window.statusBarColor = backgroundArgb

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(appState.snackbarHostState) },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                val destination = appState.currentTopLevelDestination
                if (destination != null) {
                    UserTopAppBar(
                        titleRes = destination.titleTextId,
                        navigationIcon = UserAppIcons.MENU,
                        navigationIconContentDescription = "Menu"
                    )
                }
                UserAppNavHost(
                    navController = appState.navController,
                )
            }
        }

    }
}