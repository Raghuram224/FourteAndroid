package com.example.fourteandroid.navigation

import Game
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fourteandroid.view.presentation.home.Home

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController =navController , startDestination = Screens.HomeScreen){
        composable<Screens.HomeScreen> {
            Home(
            startGameNavigation = { navController.navigate(Screens.GameScreen) }
            )
        }
        composable<Screens.GameScreen> {
            Game()
        }
    }
}