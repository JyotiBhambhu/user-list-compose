package com.jyoti.user.contacts

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jyoti.user.contacts.contactlist.ui.ContactsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsScreenTest {
    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        composeTestRule.setContent {
            ContactsScreen(
                loadMore = false,
                selectedId = -1,
                contacts = listOf(),
                fetchContacts = {},
                onContactSelect = {}
            )
        }
    }

    @Test
    fun validateView() {
        composeTestRule.onNodeWithTag("Call").assertDoesNotExist()
    }

}