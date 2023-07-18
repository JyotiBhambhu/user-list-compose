package com.jyoti.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jyoti.core.networkMonitor.NetworkMonitor
import com.jyoti.core.networkMonitor.NoOpsNetworkMonitor
import com.jyoti.designsystem.theme.MyApplicationTheme
import com.jyoti.user.ui.UserApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                UserApp(
                    networkMonitor = networkMonitor,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        UserApp(
            networkMonitor = NoOpsNetworkMonitor(),
        )
    }
}