package com.jyoti.designsystem.theme

import androidx.compose.ui.graphics.Color

private val ColorOverlayBase = Color(0xAA5D3EBC)
private val ColorOverlay300 = Color(0x99927ED2)
private val ColorOverlay100 = Color(0x77CDC3EA)
private val ColorOverlay50 = Color(0x66F3F0FE)

private val ColorBackgroundBody = Color(0xFFF8F8F8)

private val ColorPrimaryBase = Color(0xFF03DAC5)

private val ColorBackgroundLight = Color(0xFFF8F8F8)
private val ColorBackgroundGrey = Color(0xFFDEDEDC)


enum class ColorBackground(val id: Color) {
    Body(ColorBackgroundBody)
}

enum class ColorListBackground(val id: Color) {
    LIGHT(ColorBackgroundLight), DARK(ColorBackgroundGrey)
}

enum class ColorListOverlay(val id: Color) {
    _50(ColorOverlay50), _100(ColorOverlay100), _300(ColorOverlay300), Base(ColorOverlayBase)
}

enum class ColorPrimary(val id: Color) {
    Base(ColorPrimaryBase),
}

enum class ColorMain(val id: Color) {
    White(Color.White),
}