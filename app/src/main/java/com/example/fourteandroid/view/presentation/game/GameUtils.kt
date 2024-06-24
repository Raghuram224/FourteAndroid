package com.example.fourteandroid.view.presentation.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.data.DataItem
import com.example.fourteandroid.data.DataTypes

/*@Composable
fun DataItemCard(
    modifier: Modifier = Modifier,
    dataItem: DataItem,
    color: Color = MaterialTheme.colorScheme.secondary,
    selectAction:()->Unit,
    shape: Shape = RoundedCornerShape(10)
) {
    Card(
        modifier = modifier
           *//* .padding(MaterialTheme.dimens.gameDimensions.padding08)*//*
            .clickable { selectAction() },
        colors = CardDefaults.cardColors(
            containerColor = if (dataItem.isSelected) MaterialTheme.colorScheme.tertiary
            else if (dataItem.dataType != DataTypes.Number)  MaterialTheme.colorScheme.primary else color
        ),
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.coreDimensions.padding04),
                text = dataItem.data,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }

    }
}*/

@Composable
fun DataItemCard(
    modifier: Modifier = Modifier,
    dataItem: DataItem,
    color: Color = MaterialTheme.colorScheme.secondary,
    selectAction:()->Unit,
    shape: Shape = RoundedCornerShape(10),
    fontSize: TextUnit = 35.sp
) {
    Card(
        modifier = modifier

            .clickable { selectAction() },
        colors = CardDefaults.cardColors(
            containerColor = if (dataItem.isSelected) MaterialTheme.colorScheme.tertiary
            else if (dataItem.dataType != DataTypes.Number)  MaterialTheme.colorScheme.primary else color
        ),
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.coreDimensions.padding04),
                text = dataItem.data,
                style = TextStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewDataItem() {
    val dataItem = DataItem(
        dataType = DataTypes.Number,
        data = "5",
        isSelected = false
    )
    DataItemCard(
        modifier = Modifier
            .size(100.dp),
        dataItem = dataItem,
        selectAction = {})
}


@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.dimens.coreDimensions.padding16,
                horizontal = MaterialTheme.dimens.coreDimensions.padding04
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier,

            )
    }
}

@Composable
fun TimedModeDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(MaterialTheme.dimens.gameDimensions.padding16),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "You got it!",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,

                )
            )
        }
    }
}

@Composable
fun ExitAlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        containerColor = Color.White,
        icon = {
            Icon(icon, contentDescription = "Example Icon", tint = MaterialTheme.colorScheme.primary)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}