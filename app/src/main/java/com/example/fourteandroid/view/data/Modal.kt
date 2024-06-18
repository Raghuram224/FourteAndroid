package com.example.fourteandroid.view.data

enum class DataTypes {
    Number,
    Add,
    Subtract,
    Multiply,
    Division
}

data class DataItem(
    val dataType: DataTypes,
    val data: String,
    var isSelected:Boolean =false,
    )


enum class ResponseState{
    Empty,
    Loading,
    Failed,
    Success
}