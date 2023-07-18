package com.jyoti.designsystem.component

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.designsystem.theme.ColorMain
import com.jyoti.designsystem.theme.ColorPrimary

@Composable
fun UserAppFAB(
    icon: ImageVector = UserAppIcons.Add,
    contentDes: String = "FAB",
    onClickAction: () -> Unit = {}
) {
    FloatingActionButton(
        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50)),
        containerColor = ColorPrimary.Base.id,
        contentColor = ColorMain.White.id,
        onClick = onClickAction
    ) {
        Icon(icon, contentDescription = contentDes)
    }
}

@Composable
@Preview
fun UserAppFABPreview(){
    UserAppFAB()
}