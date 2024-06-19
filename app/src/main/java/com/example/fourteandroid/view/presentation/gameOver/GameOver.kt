package com.example.fourteandroid.view.presentation.gameOver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.R
import com.example.fourteandroid.ui.theme.Purple
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.view.data.DataItem
import com.example.fourteandroid.view.data.DataTypes
import com.example.fourteandroid.view.presentation.game.DataItemCard

@Composable
fun GameOver(
    modifier: Modifier = Modifier,
    backToGameNavigation:()->Unit
) {
    val answer = 11
    val score =2
    val dataItemsList = listOf(
        DataItem(dataType = DataTypes.Number, data = "49", isSelected = false),
        DataItem(dataType = DataTypes.Add, data = "+", isSelected = false),
        DataItem(dataType = DataTypes.Number, data = "26", isSelected = false),
        DataItem(dataType = DataTypes.Subtract, data = "-", isSelected = false),
        DataItem(dataType = DataTypes.Number, data = "6", isSelected = false)
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding08),
                text = stringResource(id = R.string.congrats_you_got_it),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Purple,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding16),
                text = stringResource(id = R.string.answer),
                style = TextStyle(
                    fontSize =MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,

                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding16),
                text = answer.toString(),
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(dataItemsList) { dataItem ->
                    DataItemCard(
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxWidth(),
                        dataItem = dataItem,
                        selectAction = { /*TODO*/ },
                        shape = RoundedCornerShape(0)
                    )
                }

            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding16),
                text = score.toString(),
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.score),
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            )

            Button(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding16),
                onClick = { backToGameNavigation() },
                shape = RoundedCornerShape(5)
            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.gameOverDimensions.padding08),
                    text = stringResource(id = R.string.next),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }


        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewGameOver() {
    GameOver(backToGameNavigation = {})
}