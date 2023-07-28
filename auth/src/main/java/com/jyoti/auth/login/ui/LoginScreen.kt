package com.jyoti.auth.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jyoti.auth.R
import com.jyoti.auth.login.ui.redux.LoginIntent
import com.jyoti.auth.util.AUTH_EMAIL_INPUT
import com.jyoti.auth.util.AUTH_PASSWORD_INPUT
import com.jyoti.auth.util.CLEAR_TEXT
import com.jyoti.auth.util.HIDE_PASSWORD
import com.jyoti.auth.util.LOGIN_BUTTON
import com.jyoti.auth.util.LOGIN_SCREEN_ROOT
import com.jyoti.auth.util.PROGRESS_BAR
import com.jyoti.auth.util.SHOW_PASSWORD
import com.jyoti.auth.util.SIGN_UP_BUTTON
import com.jyoti.core.base.LoadState
import com.jyoti.core.util.toEvent
import com.jyoti.designsystem.component.InputField
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.core.R as CoreR

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClicked: () -> Unit,
    navigateToHome: () -> Unit,
    showSnackBar: (message: String, duration: SnackbarDuration) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.navigateToHome, block = {
        if (state.navigateToHome == true.toEvent()) {
            navigateToHome()
        }
    })

    val genericError = stringResource(id = CoreR.string.something_went_wrong)
    LaunchedEffect(key1 = state.loadState, block = {
        if (state.loadState == LoadState.ERROR) {
            val message = state.loginError.ifEmpty { genericError }
            showSnackBar(message, SnackbarDuration.Short)
        }
    })

    Column(
        modifier = Modifier.fillMaxSize()
            .testTag(LOGIN_SCREEN_ROOT)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state.loadState == LoadState.LOADING){
            CircularProgressIndicator(modifier = Modifier.testTag(PROGRESS_BAR))
        }else{
            LoginScreen(
                emailError = state.emailError,
                passwordError = state.passwordError,
                onLoginClicked = { username, password ->
                    viewModel.onIntent(LoginIntent.DoLogin(username, password))
                },
                onSignUpClicked
            )
        }

    }
}

@Composable
fun LoginScreen(
    emailError: Boolean,
    passwordError: Boolean,
    onLoginClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit
) {
    var email by remember {
        mutableStateOf("eve.holt@reqres.in")
    }
    var password by remember {
        mutableStateOf("cityslicka")
    }

    LoginInputFields(
        emailError = if(emailError) stringResource(id = R.string.invalid_email) else "",
        passwordError = if(passwordError) stringResource(id = R.string.invalid_password) else "",
        email = email,
        password = password,
        onEmailChanged = {
            email = it
        },
        onPasswordChanged = {
            password = it
        },
        clearEmail = {
            email = ""
        },
    ) {
        onLoginClicked(email, password)
    }
    Button(modifier = Modifier.testTag(LOGIN_BUTTON), onClick = {
        onLoginClicked(email, password)
    }) {
        Text(text = stringResource(id = R.string.login))
    }

    Button(modifier = Modifier.testTag(SIGN_UP_BUTTON), onClick = onSignUpClicked) {
        Text(text = stringResource(id = R.string.signup))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginInputFields(
    email: String,
    password: String,
    emailError: String,
    passwordError: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    clearEmail: () -> Unit,
    onDone: () -> Unit
) {

    var isPasswordVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    InputField(
        testTag = AUTH_EMAIL_INPUT,
        value = email,
        label = R.string.email,
        onValueChanged = {
            onEmailChanged(it)
        },
        trailingIcon = {
            Icon(
                UserAppIcons.CLEAR,
                contentDescription = CLEAR_TEXT,
                modifier = Modifier
                    .clickable {
                        clearEmail()
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
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onDone()
        }),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

