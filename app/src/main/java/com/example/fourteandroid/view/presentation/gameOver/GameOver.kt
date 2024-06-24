package com.example.fourteandroid.view.presentation.gameOver

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.R
import com.example.fourteandroid.data.TimerStatus
import com.example.fourteandroid.ui.theme.Purple
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.view.presentation.game.DataItemCard
import com.example.fourteandroid.view.presentation.game.ExitAlertDialogExample
import com.example.fourteandroid.viewModels.GameOverViewModel

@Composable
fun GameOver(
    modifier: Modifier = Modifier,
    gameOverViewModel: GameOverViewModel,
    backToGameNavigationEndlessMode: () -> Unit,
    menuNavigation:()->Unit,
){
    val answer by gameOverViewModel.correctAnswer.collectAsState()
    val score by gameOverViewModel.score.collectAsState()
    val isExitPopupOpen = remember {
        mutableStateOf(false)
    }
    BackHandler {
        isExitPopupOpen.value = true
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!gameOverViewModel.isTimedMode) {
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
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
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
                    items(gameOverViewModel.userGuessedAnswerList) { dataItem ->
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
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.gameOverDimensions.padding08),
                    text = stringResource(id = R.string.score_board),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = Purple,
                        textAlign = TextAlign.Center
                    )
                )
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
                onClick = {
//                    if (gameOverViewModel.isTimedMode){
//                        backToGameNavigationTimedMode()
//                    }else{
                        backToGameNavigationEndlessMode()
//                    }
                    gameOverViewModel.reset()
                },
                shape = RoundedCornerShape(5),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.gameOverDimensions.padding08),
                    text = stringResource(id = if (gameOverViewModel.isTimedMode) R.string.play_again else R.string.next),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Button(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.gameOverDimensions.padding16),
                onClick = {
//                    if (gameOverViewModel.isTimedMode){
//                        backToGameNavigationTimedMode()
//                    }else{

//                    }
                    menuNavigation()
                },
                shape = RoundedCornerShape(5),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.gameOverDimensions.padding08),
                    text = stringResource(id = R.string.back_to_menu),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }


        }

        if (isExitPopupOpen.value) {
            ExitAlertDialogExample(
                onDismissRequest = {
                    isExitPopupOpen.value = false

                },
                onConfirmation = {
                    menuNavigation()

                },
                dialogTitle = "Exit the mode",
                dialogText = "Do you want to exit the mode",
                icon = Icons.Default.ExitToApp
            )
//            }
        }
    }
}
