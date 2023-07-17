package com.jyoti.user.contacts.contactlist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun ContactsRoute(
    onClickAddUser: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No users found to show here!")
        Button(onClick = onClickAddUser) {
            Text(text = "Add New User")
        }
    }
}


@Preview
@Composable
private fun ContactRoutePreview() {
    ContactsRoute {}
}