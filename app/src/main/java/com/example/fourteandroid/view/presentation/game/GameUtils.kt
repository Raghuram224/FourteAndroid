package com.example.fourteandroid.view.presentation.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.DataTypes

@Composable
fun DataItemCard(
    modifier: Modifier = Modifier,
    dataItem: DataItem,
    color: Color = MaterialTheme.colorScheme.secondary,
    selectAction:()->Unit,
) {
    Card(
        modifier = modifier
            .padding(MaterialTheme.dimens.gameDimensions.padding08)
            .clickable { selectAction() },
        colors = CardDefaults.cardColors(
            containerColor = if (dataItem.isSelected) MaterialTheme.colorScheme.tertiary
            else color
        )
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