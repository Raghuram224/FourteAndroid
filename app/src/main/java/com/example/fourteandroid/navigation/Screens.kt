package com.example.fourteandroid.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object HomeScreen : Screens()

    @Serializable
    data class GameScreen(
        val isTimedMode: Boolean = false
    ) : Screens()

    /*    @Serializable
        data object GameScreen:Screens()*/
    @Serializable
    data class GameOverScreen(
        val isTimedMode: Boolean = false
    ) : Screens()

    @Serializable
    data class IntroScreen(
        val isTimedMode: Boolean = false
    ) : Screens()
}