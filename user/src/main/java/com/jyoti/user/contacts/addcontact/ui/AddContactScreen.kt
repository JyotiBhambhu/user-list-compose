package com.jyoti.user.contacts.addcontact.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jyoti.core.base.LoadState
import com.jyoti.core.util.toEvent
import com.jyoti.designsystem.component.InputField
import com.jyoti.user.R
import com.jyoti.user.contacts.addcontact.ui.redux.AddContactIntent
import com.jyoti.user.contacts.utils.CLEAR_TEXT
import com.jyoti.core.R as CoreR

@Composable
internal fun AddContactRoute(
    viewModel: AddContactsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val genericError = stringResource(id = CoreR.string.something_went_wrong)
    val userAdded = stringResource(id = R.string.user_added, state.name)

    LaunchedEffect(key1 = state.navigateUp, block = {
        if (state.navigateUp == true.toEvent()) {
            navigateUp()
            showSnackBar(userAdded, SnackbarDuration.Short)
        }
    })

    LaunchedEffect(key1 = state.loadState, block = {
        if (state.loadState == LoadState.ERROR) {
            val message = state.addUserError.ifEmpty { genericError }
            showSnackBar(message, SnackbarDuration.Short)
        }
    })

    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddUserScreen(
            nameError = state.nameError,
            jobError = state.jobError,
            onAddUserClicked = { name, job ->
                viewModel.onIntent(AddContactIntent.createUser(name, job))
            }
        )
    }
}

@Composable
fun AddUserScreen(
    nameError: Boolean,
    jobError: Boolean,
    onAddUserClicked: (String, String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }

    AddUserInputFields(
        nameError = if (nameError) stringResource(id = R.string.invalid_name) else "",
        jobError = if (jobError) stringResource(id = R.string.invalid_job) else "",
        onNameChanged = {
            name = it
        },
        onJobChanged = {
            job = it
        }
    ) {
        onAddUserClicked(name, job)
    }
    Button(onClick = {
        onAddUserClicked(name, job)
    }) {
        Text(text = stringResource(id = R.string.add_user))
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddUserInputFields(
    nameError: String,
    jobError: String,
    onNameChanged: (String) -> Unit,
    onJobChanged: (String) -> Unit,
    onDone: () -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }
    var job by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    InputField(
        value = name,
        label = R.string.name,
        onValueChanged = {
            name = it
            onNameChanged(it)
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Clear,
                contentDescription = CLEAR_TEXT,
                modifier = Modifier
                    .clickable {
                        name = ""
                    }
            )
        },
        error = nameError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
    )
    InputField(
        value = job,
        label = R.string.job,
        onValueChanged = {
            job = it
            onJobChanged(it)
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Clear,
                contentDescription = CLEAR_TEXT,
                modifier = Modifier
                    .clickable {
                        name = ""
                    }
            )
        },
        error = jobError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onDone()
        }),
    )
}
