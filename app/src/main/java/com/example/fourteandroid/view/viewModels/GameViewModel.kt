package com.example.fourteandroid.view.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.DataTypes
import com.example.fourteandroid.view.data.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Empty)
    val responseState: StateFlow<ResponseState> = _responseState.asStateFlow()
    private val _userAnswer = MutableStateFlow<Int?>(null)
    private val _operatorsList = mutableStateListOf(
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
            data = "/",

            )
    )
    val operatorsList: List<DataItem> = _operatorsList


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
    private val _qnNumberList = mutableListOf<DataItem>()
    private val _actualQn = mutableListOf<DataItem>()

    private val _optionNumbers = mutableStateListOf<DataItem>()
    val optionNumbers: List<DataItem> = _optionNumbers

    private val _userAnswerList = mutableStateListOf<DataItem>()
    val userAnswerList: List<DataItem> = _userAnswerList
    val actualQn:List<DataItem> = _actualQn
    val userAnswer = _userAnswer.asStateFlow()
    val  qnNumberList:List<DataItem> = _qnNumberList
    private fun getRandomOperator() = operatorList.random()
    private fun getRandomNumber() = Random.nextInt(1, numbersRange)

    fun generateQuestionElements() {
        viewModelScope.launch {
            updateResponseState(ResponseState.Loading)
            repeat(operationsCount) {
                val operator = getRandomOperator()
                val number = getRandomNumber()

                val numberDataItem = DataItem(dataType = DataTypes.Number, data = number.toString())
                _qnNumberList.add(numberDataItem)
                qnOperatorsList.add(operator.key)

                _actualQn.add(numberDataItem)
                Log.i("counter",it.toString())
                if (it<operationsCount-1){
                    _actualQn.add(DataItem(dataType = operator.value, data = operator.key))
                }

            }
//            generateAnswer()

            Log.i("list data", _actualQn.toString())
            updateResponseState(responseState = ResponseState.QnGenerated)
        }

    }

    fun generateAnswer(userAnswerList:List<DataItem>): Int {
        if (userAnswerList.isEmpty()) return 0

        var result = 0
        var currentOperation: DataTypes? = null

        for (dataItem in userAnswerList) {
            when (dataItem.dataType) {
                DataTypes.Number -> {
                    val number = dataItem.data.toInt()
                    result = if (currentOperation == null) {
                        result+number
                    } else {
                        when (currentOperation) {
                            DataTypes.Add -> result + number
                            DataTypes.Subtract -> result - number
                            DataTypes.Multiply -> result * number
                            DataTypes.Division -> result / number
                            else -> result
                        }
                    }
                }

                else -> currentOperation = dataItem.dataType
            }
        }

//        updateOptionNumbersList(_qnNumberList)
        Log.i("answer", result.toString())
        Log.i("answer qn operator", qnOperatorsList.toString())
        Log.i("answer qn numbers", _qnNumberList.toString())
        if (userAnswerList.isEmpty()){
            _userAnswer.value = null
        }else{
            _userAnswer.value = result
        }


        return result
    }

    private fun updateResponseState(responseState: ResponseState) {
        _responseState.value = responseState
    }

    fun updateOptionNumbersList(list: List<DataItem>) {
        _optionNumbers.clear()
        _optionNumbers.addAll(list)
        updateResponseState(ResponseState.Success)
    }

    fun updateOptionNumbersValues(idx: Int, isSelected: Boolean) {
        _optionNumbers[idx] = _optionNumbers[idx].copy(isSelected = isSelected)
        if (_optionNumbers[idx].isSelected) {
            val dataItem = _optionNumbers[idx].copy(isSelected = false)
            updateUserAnswerList(dataItem)
        }
    }

    private fun updateUserAnswerList(dataItem: DataItem) {
        _userAnswerList.add(dataItem)
    }

    fun removeUserAnswerList(idx: Int) {
        viewModelScope.launch {
            val dataItem = userAnswerList[idx]
            _userAnswerList.remove(dataItem)

            if (dataItem.dataType == DataTypes.Number) {
                _optionNumbers.forEachIndexed { index, numberItem ->
                    if (numberItem.data == dataItem.data) {
                        updateOptionNumbersValues(idx = index, isSelected = false)
                    }
                }
            } else {
                _operatorsList.forEachIndexed { index, operatorItem ->
                    if (operatorItem.data == dataItem.data) {
                        updateOperatorList(idx = index, isSelected = false)
                    }
                }
            }
            if (userAnswerList.isEmpty()){
                _userAnswer.value = null
            }
        }

    }

    fun updateOperatorList(idx: Int, isSelected: Boolean) {
        _operatorsList[idx] = _operatorsList[idx].copy(isSelected = isSelected)
        if (_operatorsList[idx].isSelected) {
            val dataItem = _operatorsList[idx].copy(isSelected = false)
            updateUserAnswerList(dataItem)
        }
    }
}

