package com.example.fourteandroid.view.viewModels

import androidx.lifecycle.ViewModel
import com.example.fourteandroid.view.data.local.repo.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameOverViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    val userGuessedAnswerList = gameRepository.userGuessedCorrectAnswerList
    val correctAnswer = gameRepository.correctAnswer.asStateFlow()
    val score = gameRepository.score.asStateFlow()

    fun reset(){
        gameRepository.reset()
    }

}