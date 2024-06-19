package com.example.fourteandroid.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable data object HomeScreen:Screens()
    @Serializable data object GameScreen :Screens()
    @Serializable data object GameOverScreen:Screens()
}