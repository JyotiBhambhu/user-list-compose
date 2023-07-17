package com.jyoti.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jyoti.designsystem.icon.UserAppIcons
import com.jyoti.designsystem.theme.ColorMain
import com.jyoti.designsystem.theme.ColorPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = ColorPrimary.Base.id,
        titleContentColor = ColorMain.White.id,
        navigationIconContentColor = ColorMain.White.id,
    ),
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("UserTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserTopAppBarPreview() {
    UserTopAppBar(
        titleRes = android.R.string.untitled,
        navigationIcon = UserAppIcons.MENU,
        navigationIconContentDescription = "Navigation icon",
    )
}