package com.jyoti.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

object UserAppIcons {
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.KeyboardArrowLeft
    val ArrowForward = Icons.Rounded.KeyboardArrowRight
    val ArrowDropDown = Icons.Rounded.KeyboardArrowDown
    val Profile = Icons.Rounded.Person
    val MENU = Icons.Rounded.Menu
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}