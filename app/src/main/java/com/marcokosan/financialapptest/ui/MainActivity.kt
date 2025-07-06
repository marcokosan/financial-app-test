package com.marcokosan.financialapptest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.marcokosan.financialapptest.navigation.AppNavHost
import com.marcokosan.financialapptest.ui.theme.DesignSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by viewModel.isDarkTheme

            DesignSystemTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                AppNavHost(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }
}