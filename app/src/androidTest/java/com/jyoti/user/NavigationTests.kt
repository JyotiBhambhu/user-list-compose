package com.jyoti.user

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.jyoti.auth.login.navigation.loginNavigationRoute
import com.jyoti.auth.signup.navigation.signUpNavigationRoute
import com.jyoti.auth.util.AUTH_EMAIL_INPUT
import com.jyoti.auth.util.AUTH_PASSWORD_INPUT
import com.jyoti.auth.util.LOGIN_BUTTON
import com.jyoti.auth.util.LOGIN_SCREEN_ROOT
import com.jyoti.auth.util.PROGRESS_BAR
import com.jyoti.auth.util.SIGN_UP_BUTTON
import com.jyoti.user.contacts.contactlist.navigation.contactsNavigationRoute
import com.jyoti.user.navigation.UserAppNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            UserAppNavHost(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onNodeWithTag(LOGIN_SCREEN_ROOT).assertIsDisplayed()
    }

    @Test
    fun appNavHost_verifySignUpButton() {
        composeTestRule.onNodeWithTag(SIGN_UP_BUTTON).performClick()
        val route = navController.currentDestination?.route
        Assert.assertEquals(route, signUpNavigationRoute)
    }

    @Test
    fun appNavHost_verifyLoginButton() {
        composeTestRule.onNodeWithTag(PROGRESS_BAR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(AUTH_EMAIL_INPUT).performTextInput("eve.holt@reqres.in")
        composeTestRule.onNodeWithTag(AUTH_PASSWORD_INPUT).performTextInput("cityslicka")
        composeTestRule.onNodeWithTag(LOGIN_BUTTON).performClick()

//        composeTestRule.onNodeWithTag(PROGRESS_BAR).assertIsDisplayed()
    }
}