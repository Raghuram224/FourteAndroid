package com.example.fourteandroid.view.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fourteandroid.R
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.data.DataItem
import com.example.fourteandroid.data.DataTypes
import com.example.fourteandroid.view.presentation.game.DataItemCard

@Composable
fun Home(
    modifier: Modifier = Modifier,
    endlessGameNavigation:()->Unit,
    timedNavigation:()->Unit,
    ) {
    val appTitle = listOf(
        DataItem(
            dataType = DataTypes.Number,
            data = "F"
        ),
        DataItem(
            dataType = DataTypes.Add,
            data = "O"
        ),
        DataItem(
            dataType = DataTypes.Number,
            data = "U"
        ),
        DataItem(
            dataType = DataTypes.Add,
            data = "R"
        ),
        DataItem(
            dataType = DataTypes.Number,
            data = "T"
        ),
        DataItem(
            dataType = DataTypes.Subtract,
            data = "E"
        ),
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyRow {
                items(appTitle){dataItem->
                    DataItemCard(
                        modifier = Modifier
                            .size(50.dp),
                        dataItem =dataItem ,
                        shape = RoundedCornerShape(0),
                        selectAction = { /*TODO*/ }
                    )
                }
            }


            Button(
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimens.coreDimensions.padding16,
                        horizontal = MaterialTheme.dimens.coreDimensions.padding64,)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { endlessGameNavigation() },
                shape = RoundedCornerShape(0),

            ) {
                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(

                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(MaterialTheme.dimens.coreDimensions.padding04),
                        text = stringResource(id = R.string.endless),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

            }
            Button(
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimens.coreDimensions.padding16,
                        horizontal = MaterialTheme.dimens.coreDimensions.padding64,)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = { timedNavigation() },
                shape = RoundedCornerShape(0)
            ) {
                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(

                       painter = painterResource(id = R.drawable.timer_ic),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(MaterialTheme.dimens.coreDimensions.padding04),
                        text = stringResource(id = R.string.timed),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHome() {
    Home(endlessGameNavigation = {}, timedNavigation = {})
}