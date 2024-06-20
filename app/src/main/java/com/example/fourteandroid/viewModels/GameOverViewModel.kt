package com.example.fourteandroid.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.fourteandroid.navigation.Screens
import com.example.fourteandroid.data.local.repo.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameOverViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val isTimedMode = savedStateHandle.toRoute<Screens.GameOverScreen>().isTimedMode

    val userGuessedAnswerList = gameRepository.userGuessedCorrectAnswerList
    val correctAnswer = gameRepository.correctAnswer.asStateFlow()
    val score = gameRepository.score.asStateFlow()

    fun reset(){
        gameRepository.reset()
    }



}