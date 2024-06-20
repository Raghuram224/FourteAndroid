package com.example.fourteandroid.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.fourteandroid.navigation.Screens
import com.example.fourteandroid.view.CountDownTimer
import com.example.fourteandroid.data.AnswerType
import com.example.fourteandroid.data.DataItem
import com.example.fourteandroid.data.TimerStatus
import com.example.fourteandroid.data.local.repo.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _timer = MutableStateFlow(0)
    private val timerCount = 30

//    val timer = _timer.asStateFlow()
    val optionNumbers: List<DataItem> = gameRepository.optionNumbers
    val userAnswerList: List<DataItem> = gameRepository.userAnswerList
    val actualQn: List<DataItem> = gameRepository.actualQn
    val userAnswer = gameRepository.userAnswer.asStateFlow()
    val qnNumberList: List<DataItem> = gameRepository.qnNumberList
    val operatorsList: List<DataItem> = gameRepository.operatorsList
    val correctAnswer = gameRepository.correctAnswer.asStateFlow()
    val isUserGuessed = gameRepository.isUserGuessed.asStateFlow()
    val responseState = gameRepository.responseState.asStateFlow()
    val timerStatus = gameRepository.timerStatus.asStateFlow()
    val timer = _timer.asStateFlow()

    //    val optionNumbers = _optionNumbers.shuffled()
    val isTimedMode = savedStateHandle.toRoute<Screens.GameScreen>().isTimedMode

    val timeController = CountDownTimer(
        initialMillisInFuture = 30000, // 30 seconds
        countDownInterval = 1000, // 1 second
        onTick = { millisUntilFinished ->
            Log.i("seconds remaining: ",(millisUntilFinished / 1000).toString())
            _timer.value = (millisUntilFinished / 1000).toInt()
        },
        onFinish = {
            println("done!")
        }
    )
    fun generateQuestions() {
        viewModelScope.launch {
            gameRepository.generateQuestionElements()
        }
    }

    fun updateOptionNumbersList(numberList: List<DataItem>) {
        gameRepository.updateOptionNumbersList(list = numberList)
    }

    fun updateCorrectAnswer(answerList: List<DataItem>, answerType: AnswerType) {
        gameRepository.updateCorrectAnswer(list = answerList, answerType)
    }

    fun getAnswer(answerList: List<DataItem>, answerType: AnswerType) {
        gameRepository.getUserAnswer(userAnswerList = answerList, answerType = answerType)
    }

    fun removeUserAnswerList(idx: Int) {
        gameRepository.removeUserAnswerList(idx = idx)
    }

    fun updateOptionNumbersValues(idx: Int, isSelected: Boolean) {
        gameRepository.updateOptionNumbersValues(idx = idx, isSelected = isSelected)
    }

    fun updateOperatorList(idx: Int) {
        gameRepository.updateOperatorList(idx = idx)
    }

    fun updateUserGuessedCorrectAnswerList() {
        gameRepository.updateUserGuessedAnswerList()
    }

    fun reset() {
        gameRepository.reset()
    }

    //Timed mode

//    fun startTimer() {
//        viewModelScope.launch {
//            if (gameRepository.timerStatus.value != TimerStatus.Running){
//                gameRepository.countdownTimer(timerCount).collect {
//                    _timer.value = it
//                }
//            }
//
//        }
//
//
//    }
//    fun pauseTimer(){
//        gameRepository.pauseTimer()
//    }
//    fun  resumeTimer(){
//        gameRepository.resumeTimer()
//    }

    override fun onCleared() {
        super.onCleared()
        gameRepository.changeMode()
    }
    fun updateTimerStatus(status: TimerStatus){
        gameRepository.updateTimerStatus(status = status)
    }





}


