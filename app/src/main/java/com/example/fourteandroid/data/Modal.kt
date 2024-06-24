package com.example.fourteandroid.data

enum class DataTypes {
    Number,
    Add,
    Subtract,
    Multiply,
    Division,
    OpenParenthesis,
    CloseParenthesis
}

data class DataItem(
    val dataType: DataTypes,
    val data: String,
    var isSelected:Boolean =false,
    )


enum class ResponseState{
    Empty,
    Loading,
    QnGenerated,
    Success
}
enum class AnswerType{
    Computer,
    User
}
enum class TimerStatus{
    Empty,
    Running,
    Paused,
    Finished,
}

enum class BoxSize{
    LessThanQuarter,
    LessThanHalf,
    GreaterThanHalf,
}