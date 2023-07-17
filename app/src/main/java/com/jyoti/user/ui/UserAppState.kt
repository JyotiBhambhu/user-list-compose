package com.jyoti.user.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jyoti.core.network.NetworkMonitor
import com.jyoti.user.contacts.addcontact.navigation.navigateToAddContactScreen
import com.jyoti.user.contacts.contactlist.navigation.contactsNavigationRoute
import com.jyoti.user.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberUserAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
): UserAppState {
    return remember(networkMonitor, coroutineScope, navController, snackBarHostState) {
        UserAppState(networkMonitor, coroutineScope, navController, snackBarHostState)
    }
}

@Stable
class UserAppState(
    networkMonitor: NetworkMonitor,
    private val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
) {
    val isOffline = networkMonitor.isOnline.map(Boolean::not).stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            contactsNavigationRoute -> TopLevelDestination.CONTACTS
            else -> null
        }

    fun navigateToAddContact() {
        navController.navigateToAddContactScreen()
    }

    fun showSnackbar(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}