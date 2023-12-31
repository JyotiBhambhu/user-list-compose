package com.jyoti.auth.signup.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jyoti.auth.R
import com.jyoti.auth.signup.ui.redux.SignUpIntent
import com.jyoti.auth.util.AUTH_EMAIL_INPUT
import com.jyoti.auth.util.AUTH_PASSWORD_INPUT
import com.jyoti.auth.util.AUTH_RE_ENTER_PASSWORD_INPUT
import com.jyoti.auth.util.CLEAR_TEXT
import com.jyoti.auth.util.HIDE_PASSWORD
import com.jyoti.auth.util.SHOW_PASSWORD
import com.jyoti.core.base.LoadState
import com.jyoti.core.util.toEvent
import com.jyoti.designsystem.component.InputField
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.core.R as CoreR

/**
 * This screens UI is quite redundant as we have only 2 fields similar to login screen,
 * but create this as new screen because in real scenarios
 * this screen will be quite different than login
 */
@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.navigateUp, block = {
        if (state.navigateUp == true.toEvent()) {
            navigateUp()
        }
    })

    val genericError = stringResource(id = CoreR.string.something_went_wrong)
    LaunchedEffect(key1 = state.loadState, block = {
        if (state.loadState == LoadState.ERROR) {
            val message = state.signUpError.ifEmpty { genericError }
            showSnackBar(message, SnackbarDuration.Short)
        }
    })

    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpScreen(
            emailError = state.emailError,
            passwordError = state.passwordError,
            confirmPasswordError = state.confirmPasswordError,
            onSignUpClicked = { username, password, confirmPassword ->
                viewModel.onIntent(SignUpIntent.DoSignUp(username, password, confirmPassword))
            }
        )
    }
}

@Composable
fun SignUpScreen(
    emailError: Boolean,
    passwordError: Boolean,
    confirmPasswordError: Boolean,
    onSignUpClicked: (String, String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    SignUpInputFields(
        emailError = if (emailError) stringResource(id = R.string.invalid_email) else "",
        passwordError = if (passwordError) stringResource(id = R.string.invalid_password) else "",
        confirmPasswordError = if(confirmPasswordError) stringResource(id = R.string.password_mismatch) else "",
        onEmailChanged = {
            email = it
        },
        onPasswordChanged = {
            password = it
        },
        onConfirmPasswordChanged = {
            confirmPassword = it
        }
    ) {
        onSignUpClicked(email, password, confirmPassword)
    }

    Button(onClick = {
        onSignUpClicked(email, password, confirmPassword)
    }) {
        Text(text = stringResource(id = R.string.signup))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpInputFields(
    emailError: String,
    passwordError: String,
    confirmPasswordError: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onDone: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isPasswordVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    InputField(
        testTag = AUTH_EMAIL_INPUT,
        value = email,
        label = R.string.email,
        onValueChanged = {
            email = it
            onEmailChanged(it)
        },
        trailingIcon = {
            Icon(
                UserAppIcons.CLEAR,
                contentDescription = CLEAR_TEXT,
                modifier = Modifier
                    .clickable {
                        email = ""
                    }
            )
        },
        error = emailError,
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
        testTag = AUTH_PASSWORD_INPUT,
        value = password,
        label = R.string.password,
        onValueChanged = {
            password = it
            onPasswordChanged(it)
        },
        trailingIcon = {
            val image =
                if (isPasswordVisible) UserAppIcons.EYE else UserAppIcons.EYE_OFF
            val description =
                if (isPasswordVisible) HIDE_PASSWORD else SHOW_PASSWORD
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        error = passwordError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )

    InputField(
        testTag = AUTH_RE_ENTER_PASSWORD_INPUT,
        value = confirmPassword,
        label = R.string.confirm_password,
        onValueChanged = {
            confirmPassword = it
            onConfirmPasswordChanged(it)
        },
        trailingIcon = {
            val image =
                if (isConfirmPasswordVisible) UserAppIcons.EYE else UserAppIcons.EYE_OFF
            val description =
                if (isConfirmPasswordVisible) HIDE_PASSWORD else SHOW_PASSWORD
            IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        error = confirmPasswordError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onDone()
        }),
        visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
