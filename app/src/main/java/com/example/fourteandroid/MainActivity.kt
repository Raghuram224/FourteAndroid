package com.example.fourteandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fourteandroid.navigation.AppNavGraph
import com.example.fourteandroid.ui.theme.FourteAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourteAndroidTheme(dynamicColor = false, darkTheme = false) {
                AppNavGraph()
            }
        }
    }
}
