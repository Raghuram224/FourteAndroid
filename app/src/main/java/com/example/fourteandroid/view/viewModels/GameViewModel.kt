package com.example.fourteandroid.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourteandroid.view.data.AnswerType
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.local.repo.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    val optionNumbers: List<DataItem> = gameRepository.optionNumbers
    val userAnswerList: List<DataItem> = gameRepository.userAnswerList
    val actualQn: List<DataItem> = gameRepository.actualQn
    val userAnswer = gameRepository.userAnswer.asStateFlow()
    val qnNumberList: List<DataItem> = gameRepository.qnNumberList
    val operatorsList: List<DataItem> = gameRepository.operatorsList
    val correctAnswer = gameRepository.correctAnswer.asStateFlow()
    val isUserGuessed = gameRepository.isUserGuessed.asStateFlow()
    val responseState = gameRepository.responseState.asStateFlow()
//    val optionNumbers = _optionNumbers.shuffled()
    fun generateQuestions(){
        viewModelScope.launch {
            gameRepository.generateQuestionElements()
        }
    }
    fun updateOptionNumbersList(numberList:List<DataItem>){
        gameRepository.updateOptionNumbersList(list = numberList)
    }
    fun updateCorrectAnswer(answerList:List<DataItem>,answerType: AnswerType){
        gameRepository.updateCorrectAnswer(list = answerList,answerType)
    }
    fun getAnswer(answerList: List<DataItem>, answerType: AnswerType){
        gameRepository.getUserAnswer(userAnswerList = answerList,answerType =answerType)
    }

    fun removeUserAnswerList(idx:Int){
        gameRepository.removeUserAnswerList(idx = idx)
    }
    fun updateOptionNumbersValues(idx: Int,isSelected:Boolean){
        gameRepository.updateOptionNumbersValues(idx = idx,isSelected =isSelected)
    }
    fun updateOperatorList(idx: Int){
        gameRepository.updateOperatorList(idx = idx)
    }
    fun updateUserGuessedCorrectAnswerList(){
        gameRepository.updateUserGuessedAnswerList()
    }
    fun reset(){
        gameRepository.reset()
    }
}
//@HiltViewModel
//class GameViewModel @Inject constructor(
//    gameRepository: GameRepository
//) : ViewModel() {
//    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Empty)
//    val responseState: StateFlow<ResponseState> = _responseState.asStateFlow()
//    private val _userAnswer = MutableStateFlow<Int?>(null)
//    private val qnOperatorsList = mutableListOf<String>()
//    private val _qnNumberList = mutableListOf<DataItem>()
//    private val _actualQn = mutableListOf<DataItem>()
//    private val _userAnswerList = mutableStateListOf<DataItem>()
//    private val _optionNumbers = mutableStateListOf<DataItem>()
//    private val _correctAnswer = MutableStateFlow<Int?>(null)
//    private val _isUserGuessed = MutableStateFlow(false)
//    private val _operatorsList = mutableListOf(
//        DataItem(
//            dataType = DataTypes.Add,
//            data = "+",
//
//            ),
//        DataItem(
//            dataType = DataTypes.Subtract,
//            data = "-",
//
//            ),
//        DataItem(
//            dataType = DataTypes.Multiply,
//            data = "*",
//
//            ),
//        DataItem(
//            dataType = DataTypes.Division,
//            data = "÷",
//
//            )
//    )
//    private val operators = mapOf(
//        "+" to DataTypes.Add,
//        "-" to DataTypes.Subtract,
//        "*" to DataTypes.Multiply,
//        "/" to DataTypes.Division
//    )
//    private var operationsCount = 4
//    private val numbersRange = 50
//    private val operatorList = operators.entries.toList()
//
//    val optionNumbers: List<DataItem> = _optionNumbers
//    val userAnswerList: List<DataItem> = _userAnswerList
//    val actualQn: List<DataItem> = _actualQn
//    val userAnswer = _userAnswer.asStateFlow()
//    val qnNumberList: List<DataItem> = _qnNumberList
//    val operatorsList: List<DataItem> = _operatorsList
//    val correctAnswer = _correctAnswer.asStateFlow()
//    val isUserGuessed = _isUserGuessed.asStateFlow()
//    private fun getRandomOperator() = operatorList.random()
//    private fun getRandomNumber() = Random.nextInt(1, numbersRange)
//
//    fun generateQuestionElements() {
//        viewModelScope.launch {
//            updateResponseState(ResponseState.Loading)
//            for (i in 0 until operationsCount) {
//                val operator = getRandomOperator()
//
//                val number = getRandomNumber()
//                val numberDataItem = DataItem(dataType = DataTypes.Number, data = number.toString())
//
//                _qnNumberList.add(numberDataItem)
//                _actualQn.add(numberDataItem)
//
//                qnOperatorsList.add(operator.key)
//                _actualQn.add(DataItem(dataType = operator.value, data = operator.key))
//
//                Log.i("actual qn", actualQn.toList().toString())
//
//            }
//            //            generateAnswer()
//
//            Log.i("list data", _actualQn.toString())
//            updateResponseState(responseState = ResponseState.QnGenerated)
//        }
//
//    }
//
//
//    private fun generateAnswer(userAnswerList: List<DataItem>, answerType: AnswerType): Int {
//        if (userAnswerList.isEmpty()) return 0
//
//        var result = 0
//        var currentOperation: DataTypes? = null
//
//        for (dataItem in userAnswerList) {
////            Log.i("step",currentOperation.toString())
//            when (dataItem.dataType) {
//                DataTypes.Number -> {
//                    val number = dataItem.data.toInt()
//                    Log.i("step ", number.toString())
//                    result = if (currentOperation == null) {
//                        if (answerType == AnswerType.User) {
//                            ("$result" + "$number").toInt()
//                        } else {
//                            result + number
//                        }
//                    } else {
////                        Log.i("step",currentOperation.toString())
//                        when (currentOperation) {
//                            DataTypes.Add -> result + number
//                            DataTypes.Subtract -> result - number
//                            DataTypes.Multiply -> result * number
//                            DataTypes.Division -> result / number
//                            else -> result
//                        }
//                    }
//                }
//
//                else -> {
//                    currentOperation = dataItem.dataType
//                    Log.i("step", currentOperation.toString())
//                }
//            }
//        }
//        Log.i("step Computer", "Computer End**")
//
//
////        updateOptionNumbersList(_qnNumberList)
//        Log.i("answer", result.toString())
//        Log.i("answer qn operator", qnOperatorsList.toString())
//        Log.i("answer qn numbers", _qnNumberList.toString())
//        if (userAnswerList.isEmpty()) {
//            _userAnswer.value = null
//        } else {
////            _userAnswer.value = result
//        }
//
//
//        return result
//    }
//
//
//    private fun updateResponseState(responseState: ResponseState) {
//        _responseState.value = responseState
//    }
//
//    fun updateOptionNumbersList(list: List<DataItem>) {
//        _optionNumbers.clear()
//        _optionNumbers.addAll(list)
//        updateResponseState(ResponseState.Success)
//
//    }
//
//    fun updateOptionNumbersValues(idx: Int, isSelected: Boolean) {
//        _optionNumbers[idx] = _optionNumbers[idx].copy(isSelected = isSelected)
//        if (_optionNumbers[idx].isSelected) {
//            val dataItem = _optionNumbers[idx].copy(isSelected = false)
//            updateUserAnswerList(dataItem)
//        }
//    }
//
//    private fun updateUserAnswerList(dataItem: DataItem) {
//        _userAnswerList.add(dataItem)
//    }
//
//    fun removeUserAnswerList(idx: Int) {
//        viewModelScope.launch {
//            val dataItem = userAnswerList[idx]
//            _userAnswerList.remove(dataItem)
//
//            if (dataItem.dataType == DataTypes.Number) {
//                _optionNumbers.forEachIndexed { index, numberItem ->
//                    if (numberItem.data == dataItem.data) {
//                        updateOptionNumbersValues(idx = index, isSelected = false)
//                    }
//                }
//            } /*else {
//                _operatorsList.forEachIndexed { index, operatorItem ->
//                    if (operatorItem.data == dataItem.data) {
//                        updateOperatorList(idx = index, isSelected = false)
//                    }
//                }
//            }*/
//            if (userAnswerList.isEmpty()) {
//                _userAnswer.value = null
//            }
//        }
//
//    }
//
//    fun updateOperatorList(idx: Int, isSelected: Boolean) {
////        _operatorsList[idx] = _operatorsList[idx].copy(isSelected = isSelected)
////        if (_operatorsList[idx].isSelected) {
//
//        updateUserAnswerList(operatorsList[idx])
////        }
//    }
//
//    fun updateCorrectAnswer(list: List<DataItem>) {
//        _correctAnswer.value =
//            generateAnswer(userAnswerList = list, answerType = AnswerType.Computer)
//    }
//
//    fun getUserAnswer(userAnswerList: List<DataItem>) {
//
//        val result = generateAnswer(userAnswerList = userAnswerList, answerType = AnswerType.User)
//        Log.i("get answer", result.toString())
//        if (userAnswerList.isEmpty()) {
//            _userAnswer.value = null
//        } else {
//            _userAnswer.value = result
//        }
//
//        if (userAnswer.value == correctAnswer.value) {
//            _isUserGuessed.value = true
//        }
//    }
//}

