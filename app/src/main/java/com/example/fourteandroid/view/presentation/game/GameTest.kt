/*
package com.example.fourteandroid.view.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun GameTest(modifier: Modifier = Modifier) {
    val dataItems = listOf(
        DataItem(
            dataType = DataTypes.Number,
            data = "5",
            isSelected = false
        ),
        DataItem(
            dataType = DataTypes.Add,
            data = "+",

            ),
        DataItem(
            dataType = DataTypes.Number,
            data = "10",
            isSelected = false
        ),
        DataItem(
            dataType = DataTypes.Subtract,
            data = "-",

            ),

        )
    val operatorsList = listOf(
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

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.gameDimensions.pageHorizontalPadding16,
                    vertical = MaterialTheme.dimens.gameDimensions.pageVerticalPadding08
                )
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "6",
                        style = TextStyle(
                            fontSize = 40.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center

                        )
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(dataItems) { dataItem ->
                            DataItemCard(
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(0.dp),
                                dataItem = dataItem,
                                selectAction = {}
                            )
                        }
                    }
                }


            }

            Box(
                modifier = Modifier
                    .weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(dataItems) { dataItem ->
                        DataItemCard(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(MaterialTheme.dimens.gameDimensions.padding08),
                            dataItem = dataItem,
                            selectAction = {}
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.1f),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    items(operatorsList) { operatorDataItem ->
                        DataItemCard(
                            modifier = Modifier
                                .size(80.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                            dataItem = operatorDataItem,
                            selectAction = {}
                        )
                    }
                }
            }


        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewGameTest() {
    GameTest()
}*/
