package com.jyoti.designsystem.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

private val ColorBackgroundBody = Color(0xFFF8F8F8)

private val ColorPrimaryBase = Color(0xFF03DAC5)


enum class ColorBackground(val id: Color) {
    Body(ColorBackgroundBody)
}

enum class ColorPrimary(val id: Color) {
    Base(ColorPrimaryBase),
}

enum class ColorMain(val id: Color) {
    White(Color.White),
}