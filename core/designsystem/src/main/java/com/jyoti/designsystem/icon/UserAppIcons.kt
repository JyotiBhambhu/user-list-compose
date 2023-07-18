package com.jyoti.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector

object UserAppIcons {
    val Add = Icons.Rounded.Add
    val MENU = Icons.Rounded.Menu
    val MESSAGE = Icons.Rounded.Message
    val CALL = Icons.Rounded.Call
    val EYE = Icons.Rounded.Visibility
    val EYE_OFF = Icons.Rounded.VisibilityOff
    val CLEAR = Icons.Rounded.Clear
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}