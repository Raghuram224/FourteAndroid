package com.example.fourteandroid.navigation

import Game
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.fourteandroid.view.presentation.gameOver.GameOver
import com.example.fourteandroid.view.presentation.home.Home
import com.example.fourteandroid.view.presentation.home.Intro
import com.example.fourteandroid.viewModels.GameOverViewModel
import com.example.fourteandroid.viewModels.GameViewModel

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()


    val animationDuration = 500

    NavHost(navController = navController, startDestination = Screens.HomeScreen) {
        composable<Screens.HomeScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
        ) {
            Home(
                endlessGameNavigation = { navController.navigate(Screens.IntroScreen(isTimedMode = false)) },
                timedNavigation = { navController.navigate(Screens.IntroScreen(isTimedMode = true)) }
            )
        }
        composable<Screens.GameScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
        ) {
            val gameViewModel = hiltViewModel<GameViewModel>()
            Game(
                gameViewModel = gameViewModel,
                gameOverNavigationEndlessMode = {
                    navController.navigate(Screens.GameOverScreen(isTimedMode = false))
                },
                gameOverNavigationTimedMode = {
                    navController.navigate(Screens.GameOverScreen(isTimedMode = true))
                },
                exitNavigation = {
                    navController.navigate(Screens.HomeScreen){
                        navController.currentDestination?.id?.let { it1 ->
                            navController.popBackStack(
                                it1, inclusive = true)
                        }
                    }
                }
            )
        }
        composable<Screens.GameOverScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
        ) {
            val gameOverViewModel = hiltViewModel<GameOverViewModel>()
            GameOver(
                gameOverViewModel = gameOverViewModel,
                backToGameNavigationEndlessMode = {
//                    navController.navigateUp()
//                    navController.navigateUp()
                    navController.navigate(Screens.GameScreen(isTimedMode = gameOverViewModel.isTimedMode)){
                        navController.currentDestination?.let { it1 ->
                            navController.popBackStack(
                                it1.id, inclusive = true
                            )
                        }
                    }
                },
                menuNavigation = {
                    navController.navigate(Screens.HomeScreen)
                }
            )
        }
        composable<Screens.IntroScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                )
            },
        ) { entry ->
            val isTimedMode = entry.toRoute<Screens.IntroScreen>().isTimedMode
            Intro(
                isTimedMode = isTimedMode,
                navigation = {
                    navController.navigate(Screens.GameScreen(isTimedMode = isTimedMode)) {
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