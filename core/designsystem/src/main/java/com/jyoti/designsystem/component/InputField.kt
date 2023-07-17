package com.jyoti.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputField(
    testTag: String,
    value: String,
    @StringRes label: Int,
    error: String,
    onValueChanged: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        value = value,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        label = {
            Text(
                text = stringResource(id = label),
            )
        },
        onValueChange = onValueChanged,
        trailingIcon = trailingIcon,
        isError = error.isNotEmpty(),
        supportingText = {
            Text(
                text = error,
            )
        }
    )
}