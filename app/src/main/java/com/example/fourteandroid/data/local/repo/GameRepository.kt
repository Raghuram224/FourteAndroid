package com.example.fourteandroid.data.local.repo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.fourteandroid.data.AnswerType
import com.example.fourteandroid.data.DataItem
import com.example.fourteandroid.data.DataTypes
import com.example.fourteandroid.data.ResponseState
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
//            data = "÷",
            data = "/",

            ),
        DataItem(
            dataType = DataTypes.OpenParenthesis,
            data = "(",

            ),
        DataItem(
            dataType = DataTypes.CloseParenthesis,
            data = ")",

            )
    )
    private val operators = mapOf(
        "+" to DataTypes.Add,
        "-" to DataTypes.Subtract,
        "*" to DataTypes.Multiply,
        "/" to DataTypes.Division,
        /*         "(" to DataTypes.OpenParenthesis,
                 ")" to DataTypes.CloseParenthesis*/
    )


    private var operationsCount = 4
    private val numbersRange = 50
    private val operatorList = operators.entries.toList()
    private fun getRandomOperator() = operatorList.random()
    private fun getRandomNumber() = Random.nextInt(1, numbersRange)

    fun generateQuestionElements() {
        userGuessedCorrectAnswerList = emptyList()
        val qnCount = operationsCount - 1
        updateResponseState(ResponseState.Loading)
        for (i in 0 until operationsCount) {
            val operator = getRandomOperator()

            val number = getRandomNumber()
            val numberDataItem = DataItem(dataType = DataTypes.Number, data = number.toString())

            qnNumberList.add(numberDataItem)
            actualQn.add(numberDataItem)

            if (i < qnCount) {
                qnOperatorsList.add(operator.key)
                actualQn.add(DataItem(dataType = operator.value, data = operator.key))

            }

        }
        qnNumberList.shuffle()
        Log.i("list data", actualQn.toString())
        updateResponseState(responseState = ResponseState.QnGenerated)


    }

    private fun generateAnswer(userAnswerList: List<DataItem>, answerType: AnswerType): Int? {

        if (userAnswerList.isEmpty()) return null
        val expressionList = userAnswerList.map { it.data }
        Log.i("expression list", expressionList.toString())
        val postfix = infixToPostfix(expressionList)
        println("Postfix: $postfix")

        val result = postfix?.let { evaluatePostfix(it) }
        Log.i("expression result", result.toString())
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
        }
        if (userAnswerList.isEmpty()) {
            userAnswer.value = null
        }


    }

    fun updateOperatorList(idx: Int) = updateUserAnswerList(operatorsList[idx])


    fun updateCorrectAnswer(list: List<DataItem>, answerType: AnswerType) {
        correctAnswer.value =
            generateAnswer(userAnswerList = list, answerType = answerType)
        Log.i("correct answer !!", correctAnswer.value.toString())
    }

    fun getUserAnswer(userAnswerList: List<DataItem>, answerType: AnswerType) {

        val result = generateAnswer(userAnswerList = userAnswerList, answerType = answerType)
        Log.i("get answer", result.toString())
        if (userAnswerList.isEmpty()) {
            userAnswer.value = null
        } else {
            userAnswer.value = result
        }

        if (userAnswer.value == correctAnswer.value) {
            isUserGuessed.value = true
            score.value += 1
        }
    }

    fun updateUserGuessedAnswerList() {
        userGuessedCorrectAnswerList = userAnswerList.toList()
    }

    fun reset() {
        qnNumberList.clear()
        actualQn.clear()
        userAnswerList.clear()
        optionNumbers.clear()
        isUserGuessed.value = false
//       updateTimerStatus(status = TimerStatus.Empty)
//        isTimerStarted = false

    }


    fun changeMode() {
        reset()
        score.value = 0
    }

}