package com.example.fourteandroid.view.data.local.repo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.fourteandroid.view.data.AnswerType
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.DataTypes
import com.example.fourteandroid.view.data.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

class GameRepository {
    private val qnOperatorsList = mutableListOf<String>()
    val responseState = MutableStateFlow<ResponseState>(ResponseState.Empty)
    val userAnswer = MutableStateFlow<Int?>(null)
    val qnNumberList = mutableListOf<DataItem>()
    val actualQn = mutableListOf<DataItem>()
    var userAnswerList = mutableStateListOf<DataItem>()
    val optionNumbers = mutableStateListOf<DataItem>()
    val correctAnswer = MutableStateFlow<Int?>(null)
    val isUserGuessed = MutableStateFlow(false)
    var userGuessedCorrectAnswerList = listOf<DataItem>()
    val score = MutableStateFlow(0)
    val operatorsList = mutableListOf(
        DataItem(
            dataType = DataTypes.Add,
            data = "+",

            ),
        DataItem(
            dataType = DataTypes.Subtract,
            data = "-",

            ),
        DataItem(
            dataType = DataTypes.Multiply,
            data = "*",

            ),
        DataItem(
            dataType = DataTypes.Division,
            data = "÷",

            )
    )
    private val operators = mapOf(
        "+" to DataTypes.Add,
        "-" to DataTypes.Subtract,
        "*" to DataTypes.Multiply,
        "/" to DataTypes.Division
    )
    private var operationsCount = 4
    private val numbersRange = 50
    private val operatorList = operators.entries.toList()
    private fun getRandomOperator() = operatorList.random()
    private fun getRandomNumber() = Random.nextInt(1, numbersRange)

    fun generateQuestionElements() {
        userGuessedCorrectAnswerList = emptyList()

        updateResponseState(ResponseState.Loading)
        for (i in 0 until operationsCount) {
            val operator = getRandomOperator()

            val number = getRandomNumber()
            val numberDataItem = DataItem(dataType = DataTypes.Number, data = number.toString())

            qnNumberList.add(numberDataItem)
            actualQn.add(numberDataItem)

            qnOperatorsList.add(operator.key)
            actualQn.add(DataItem(dataType = operator.value, data = operator.key))

//                Log.i("actual qn", actualQn.toList().toString())

        }
        //            generateAnswer()
        qnNumberList.shuffle()
        Log.i("list data", actualQn.toString())
        updateResponseState(responseState = ResponseState.QnGenerated)


    }


    private fun generateAnswer(userAnswerList: List<DataItem>, answerType: AnswerType): Int {
        if (userAnswerList.isEmpty()) return 0

        var result = 0
        var currentOperation: DataTypes? = null

        for (dataItem in userAnswerList) {
//            Log.i("step",currentOperation.toString())
            when (dataItem.dataType) {
                DataTypes.Number -> {
                    val number = dataItem.data.toInt()
                    Log.i("step ", number.toString())
                    result = if (currentOperation == null) {
                        if (answerType == AnswerType.User) {
                            ("$result" + "$number").toInt()
                        } else {
                            result + number
                        }
                    } else {
//                        Log.i("step",currentOperation.toString())
                        when (currentOperation) {
                            DataTypes.Add -> result + number
                            DataTypes.Subtract -> result - number
                            DataTypes.Multiply -> result * number
                            DataTypes.Division -> result / number
                            else -> result
                        }
                    }
                }

                else -> {
                    currentOperation = dataItem.dataType
                    Log.i("step", currentOperation.toString())
                }
            }
        }
        Log.i("step Computer", "Computer End**")


//        updateOptionNumbersList(_qnNumberList)
        Log.i("answer", result.toString())
        Log.i("answer qn operator", qnOperatorsList.toString())
        Log.i("answer qn numbers", qnNumberList.toString())
        if (userAnswerList.isEmpty()) {
            userAnswer.value = null
        } else {
//            _userAnswer.value = result
        }


        return result
    }


    private fun updateResponseState(responseState: ResponseState) {
        this.responseState.value = responseState
    }

    fun updateOptionNumbersList(list: List<DataItem>) {
        optionNumbers.clear()
        optionNumbers.addAll(list)
        updateResponseState(ResponseState.Success)

    }

    fun updateOptionNumbersValues(idx: Int, isSelected: Boolean) {
        optionNumbers[idx] = optionNumbers[idx].copy(isSelected = isSelected)
        if (optionNumbers[idx].isSelected) {
            val dataItem = optionNumbers[idx].copy(isSelected = false)
            updateUserAnswerList(dataItem)
        }
    }

    private fun updateUserAnswerList(dataItem: DataItem) {
        userAnswerList.add(dataItem)
    }

    fun removeUserAnswerList(idx: Int) {

        val dataItem = userAnswerList[idx]
        userAnswerList.remove(dataItem)

        if (dataItem.dataType == DataTypes.Number) {
            optionNumbers.forEachIndexed { index, numberItem ->
                if (numberItem.data == dataItem.data) {
                    updateOptionNumbersValues(idx = index, isSelected = false)
                }
            }
        } /*else {
                _operatorsList.forEachIndexed { index, operatorItem ->
                    if (operatorItem.data == dataItem.data) {
                        updateOperatorList(idx = index, isSelected = false)
                    }
                }
            }*/
        if (userAnswerList.isEmpty()) {
            userAnswer.value = null
        }


    }

    fun updateOperatorList(idx: Int) {
//        _operatorsList[idx] = _operatorsList[idx].copy(isSelected = isSelected)
//        if (_operatorsList[idx].isSelected) {

        updateUserAnswerList(operatorsList[idx])
//        }
    }

    fun updateCorrectAnswer(list: List<DataItem>,answerType: AnswerType) {
        correctAnswer.value =
            generateAnswer(userAnswerList = list, answerType = answerType)
    }

    fun getUserAnswer(userAnswerList: List<DataItem>,answerType: AnswerType) {

        val result = generateAnswer(userAnswerList = userAnswerList, answerType = answerType)
        Log.i("get answer", result.toString())
        if (userAnswerList.isEmpty()) {
            userAnswer.value = null
        } else {
            userAnswer.value = result
        }

        if (userAnswer.value == correctAnswer.value) {
            isUserGuessed.value = true
            score.value+=1
        }
    }
    fun updateUserGuessedAnswerList(){
        userGuessedCorrectAnswerList = userAnswerList.toList()
    }
    fun reset(){
        qnNumberList.clear()
        actualQn.clear()
        userAnswerList.clear()
        optionNumbers.clear()
        isUserGuessed.value =false

    }
}