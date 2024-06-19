package com.example.fourteandroid.navigation

import Game
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fourteandroid.view.presentation.gameOver.GameOver
import com.example.fourteandroid.view.presentation.home.Home
import com.example.fourteandroid.view.viewModels.GameOverViewModel
import com.example.fourteandroid.view.viewModels.GameViewModel

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen) {
        composable<Screens.HomeScreen> {
            Home(
                startGameNavigation = { navController.navigate(Screens.GameScreen) }
            )
        }
        composable<Screens.GameScreen> {
            val gameViewModel = hiltViewModel<GameViewModel>()
            Game(
                gameViewModel = gameViewModel,
                gameOverNavigation = {

                    navController.navigate(Screens.GameOverScreen) {
                        navController.currentDestination?.id?.let { it1 ->
                            navController.popBackStack(
                                it1, inclusive = true
                            )
                        }
                    }
                })
        }
        composable<Screens.GameOverScreen> {
            val gameOverViewModel = hiltViewModel<GameOverViewModel>()
            GameOver(
                gameOverViewModel = gameOverViewModel,
                backToGameNavigation = {
                    navController.navigate(Screens.GameScreen) {
                        navController.currentDestination?.id?.let { it1 ->
                            navController.popBackStack(
                                it1, inclusive = true
                            )
                        }
                    }

                }
            )
        }
    }
}