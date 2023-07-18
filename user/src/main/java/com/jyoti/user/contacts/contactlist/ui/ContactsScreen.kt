package com.jyoti.user.contacts.contactlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jyoti.core.base.LoadState
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.designsystem.theme.ColorListBackground
import com.jyoti.designsystem.theme.ColorListOverlay
import com.jyoti.designsystem.theme.ColorMain
import com.jyoti.user.contacts.contactlist.ui.model.ContactsUiModel
import com.jyoti.user.contacts.contactlist.ui.redux.ContactsIntent

@Composable
internal fun ContactsRoute(
    viewModel: ContactsViewModel = hiltViewModel(),
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val genericError = stringResource(id = com.jyoti.core.R.string.something_went_wrong)

    LaunchedEffect(Unit) {
        viewModel.onIntent(ContactsIntent.FetchUsers(state.page))
    }

    LaunchedEffect(key1 = state.loadState, block = {
        if (state.loadState == LoadState.ERROR) {
            val message = state.fetchUserError.ifEmpty { genericError }
            showSnackBar(message, SnackbarDuration.Short)
        }
    })

    ContactsScreen(loadMore = state.loadMore, selectedId = state.selectedId,
        contacts = state.contacts,
        fetchContacts = {
            viewModel.onIntent(ContactsIntent.FetchUsers(state.page))
        }) {
        viewModel.onIntent(ContactsIntent.SelectContact(it))
    }

}

@Composable
fun ContactsScreen(
    loadMore: Boolean,
    selectedId: Int,
    contacts: List<ContactsUiModel.Contact>,
    fetchContacts: () -> Unit,
    onContactSelect: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    // Load more data when scrolling reaches the end
    val endReached by remember(scrollState) {
        derivedStateOf {
            val totalItemCount = scrollState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= totalItemCount - 1
        }
    }
    LaunchedEffect(key1 = loadMore, block = {
        if (loadMore && endReached) {
            fetchContacts()
        }
    })

    LazyColumn(state = scrollState) {
        itemsIndexed(contacts) { index, contact ->
            val col =
                if (index % 2 == 0) ColorListBackground.LIGHT.id else ColorListBackground.DARK.id
            ContactItem(contact, selectedId, col) { onContactSelect(contact.id) }
        }
    }
}

@Composable
fun ContactItem(
    contact: ContactsUiModel.Contact,
    selectedId: Int,
    itemBgColor: Color,
    click: () -> Unit
) {

    val isSel = (selectedId == contact.id)

    val backgroundColor = if (isSel) {
        ColorListOverlay._300.id
    } else {
        itemBgColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { click() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(100.dp),
        ) {
            AsyncImage(
                model = contact.url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            if (!isSel) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorListOverlay._100.id)
                ) {}
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = contact.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        if (isSel) {
            // Display call and message icons for selected item
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 20.dp)
            ) {
                Icon(
                    imageVector = UserAppIcons.CALL,
                    contentDescription = "Call",
                    tint = ColorMain.White.id,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    imageVector = UserAppIcons.MESSAGE,
                    contentDescription = "Message",
                    tint = ColorMain.White.id
                )
            }
        } else {
            // Display online status for non-selected items
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color.Green)

            )
        }
    }
}