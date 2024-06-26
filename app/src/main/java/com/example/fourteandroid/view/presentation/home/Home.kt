package com.example.fourteandroid.view.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    endlessGameNavigation: () -> Unit,
    timedNavigation: () -> Unit,
) {

    val isAnimationStarted = remember {
        mutableStateOf(false)
    }

    val logoPadding = if (isAnimationStarted.value) 300.dp else 0.dp
    val logoPaddingValues = animateDpAsState(
        targetValue = logoPadding,
        animationSpec = tween(1000)
        )
    val modePadding = if (isAnimationStarted.value) 300.dp else 0.dp
    val modePaddingValues = animateDpAsState(
        targetValue = modePadding,
        animationSpec = tween(1000)
        )

    LaunchedEffect(Unit) {
        isAnimationStarted.value =true
    }
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,/**/
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyRow(
                modifier = Modifier
                    .padding(top = logoPaddingValues.value)
                    .animateContentSize()
            ) {
                items(appTitle) { dataItem ->
                    DataItemCard(
                        modifier = Modifier
                            .size(50.dp),
                        dataItem = dataItem,
                        shape = RoundedCornerShape(0),
                        selectAction = { /*TODO*/ }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = modePaddingValues.value)
                    .fillMaxWidth(),
//               verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(0.2f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(MaterialTheme.dimens.coreDimensions.padding32)

                ) {
                    Button(
                        modifier = Modifier
                            .padding(
                                MaterialTheme.dimens.coreDimensions.padding08
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { endlessGameNavigation() },
                        shape = RoundedCornerShape(0),

                        ) {
                        Row(
                            modifier = Modifier,
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
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                    }
                    Button(
                        modifier = Modifier
                            .padding(
                                MaterialTheme.dimens.coreDimensions.padding08
                            )
                            .fillMaxWidth(),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = { timedNavigation() },
                        shape = RoundedCornerShape(0)
                    ) {
                        Row(
                            modifier = Modifier,
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
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .weight(0.2f)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHome() {
    Home(endlessGameNavigation = {}, timedNavigation = {})
}