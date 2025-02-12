package com.fulbiopretell.retoyape2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fulbiopretell.retoyape2025.ui.navigation.main.MainNavigation
import com.fulbiopretell.retoyape2025.ui.theme.RetoYape2025Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Deshabilito splash screen
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

        enableEdgeToEdge()
        setContent {
            RetoYape2025Theme {
                MainNavigation()
            }
        }
    }
}