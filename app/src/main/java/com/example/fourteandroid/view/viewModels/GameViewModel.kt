package com.example.fourteandroid.view.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.DataTypes
import com.example.fourteandroid.view.data.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _responseState = MutableStateFlow(ResponseState.Empty)

    private val operators = mapOf(
        "+" to DataTypes.Add,
        "-" to DataTypes.Subtract,
        "*" to DataTypes.Multiply,
        "/" to DataTypes.Division
    )
    private val operationsCount = 4
    private val numbersRange = 50

    private val operatorList = operators.entries.toList()

    private val qnOperatorsList = mutableListOf<String>()
    private val qnNumberList = mutableListOf<DataItem>()

    private val actualQn = mutableListOf<DataItem>()
    private val _optionNumbers = mutableStateListOf<DataItem>()

    val responseState = _responseState.asStateFlow()
    val optionNumbers:List<DataItem> = _optionNumbers

    private fun getRandomOperator(): Map.Entry<String, DataTypes> {
        return operatorList[Random.nextInt(operatorList.size)]
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(1, numbersRange)
    }

    @OptIn(InternalCoroutinesApi::class)
    fun generateQuestionElements() {
        updateResponseState(responseState = ResponseState.Loading)
        synchronized(Unit) {

            for (i in 0 until operationsCount) {
                val operator = getRandomOperator()
                val number = getRandomNumber()

                qnNumberList.add(
                    DataItem(
                        dataType = DataTypes.Number,
                        data = number.toString()
                    )
                )
                qnOperatorsList.add(operator.key)

                actualQn.add(
                    DataItem(
                        dataType = DataTypes.Number,
                        data = number.toString()
                    )
                )
                actualQn.add(
                    DataItem(
                        dataType = operator.value,
                        data = operator.key
                    )
                )

            }
            generateAnswer()
        }
        Log.i("list data", actualQn.toString())


    }

    private fun generateAnswer(): Int {
        if (actualQn.isEmpty()) return 0

        var result = 0
        var currentOperation: DataTypes? = null

        actualQn.forEach { dataItem ->
            when (dataItem.dataType) {
                DataTypes.Number -> {
                    val number = dataItem.data.toInt()
                    result = if (currentOperation == null) {
                        number
                    } else {
                        when (currentOperation) {
                            DataTypes.Add -> result + number
                            DataTypes.Subtract -> result - number
                            DataTypes.Multiply -> result * number
                            DataTypes.Division -> result / number
                            DataTypes.Number -> 0
                            null -> 0
                        }
                    }

                }

                else -> {
                    currentOperation = dataItem.dataType
                }
            }

            updateOptionNumbersList(list = qnNumberList)

        }
        Log.i("answer", result.toString())
        Log.i("answer", qnOperatorsList.toString())
        Log.i("answer", qnNumberList.toString())

        return result
    }

    private fun updateResponseState(responseState: ResponseState) {
        _responseState.value = responseState
    }

    private fun updateOptionNumbersList(list: List<DataItem>) {
        _optionNumbers.clear()
        _optionNumbers.addAll(list)
        updateResponseState(responseState = ResponseState.Success)
//        qnNumberList.clear()
//        qnOperatorsList.clear()
    }


}

