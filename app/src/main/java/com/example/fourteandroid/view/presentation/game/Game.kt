import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.R
import com.example.fourteandroid.data.AnswerType
import com.example.fourteandroid.data.BoxSize
import com.example.fourteandroid.data.ResponseState
import com.example.fourteandroid.data.TimerStatus
import com.example.fourteandroid.ui.theme.Purple
import com.example.fourteandroid.ui.theme.dimens
import com.example.fourteandroid.view.presentation.game.DataItemCard
import com.example.fourteandroid.view.presentation.game.ExitAlertDialogExample
import com.example.fourteandroid.view.presentation.game.Loading
import com.example.fourteandroid.view.presentation.game.TimedModeDialog
import com.example.fourteandroid.view.presentation.game.TimerTransition
import com.example.fourteandroid.viewModels.GameViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(
    gameViewModel: GameViewModel,
    gameOverNavigationEndlessMode: () -> Unit,
    gameOverNavigationTimedMode: () -> Unit,
    exitNavigation: () -> Unit,
) {
    Log.i("game mode", gameViewModel.isTimedMode.toString())
    val operatorsList = gameViewModel.operatorsList
    val responseState by gameViewModel.responseState.collectAsState()
    val userAnswerList = gameViewModel.userAnswerList
    val actualQn = gameViewModel.actualQn
    val optionNumbersList = gameViewModel.qnNumberList
    val userAnswer by gameViewModel.userAnswer.collectAsState()
    val correctAnswer by gameViewModel.correctAnswer.collectAsState()
    val isUserGuessed by gameViewModel.isUserGuessed.collectAsState()
    val time by gameViewModel.timer.collectAsState()
    val timerStatus by gameViewModel.timerStatus.collectAsState()
    val isTimedModeDialogOpen = remember {
        mutableStateOf(false)
    }
    val isExitPopupOpen = remember {
        mutableStateOf(false)
    }

    val currentUserAnswerRowSize = remember {
        mutableIntStateOf(0)
    }
    val optionNumbers = gameViewModel.optionNumbers
    val usersAnswerList = gameViewModel.userAnswerList


    val listState = rememberLazyListState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val boxState = remember {
        mutableStateOf(BoxSize.LessThanQuarter)
    }
    val quarterScreenWidthDp = screenWidth / 4
    val halfScreenWidthDp = screenWidth/2
//    val userAnswerBoxSize = 35
    val userAnswerBoxSize = when(boxState.value){
        BoxSize.LessThanQuarter -> 70.dp
        BoxSize.LessThanHalf -> 50.dp
        BoxSize.GreaterThanHalf -> 35.dp
    }

    val boxAnimateSize = animateDpAsState(targetValue = userAnswerBoxSize, label = "")

    LaunchedEffect(boxState) {
        Log.i("box state",boxState.value.toString())
    }



    LaunchedEffect(Unit) {
        gameViewModel.reset()
        gameViewModel.generateQuestions()
    }
    LaunchedEffect(timerStatus) {
        Log.i("timer", timerStatus.toString())
        if (gameViewModel.isTimedMode && timerStatus != TimerStatus.Running && !gameViewModel.isTimerStarted) {
            Log.i("timer inside", timerStatus.toString())
            gameViewModel.timeController.start()
            gameViewModel.updateTimerStatus(status = TimerStatus.Running)
            gameViewModel.isTimerStarted = true

        }
        if (timerStatus == TimerStatus.Finished) {
            gameOverNavigationTimedMode()

        }
    }


    LaunchedEffect(responseState) {
        if (responseState == ResponseState.QnGenerated) {

            gameViewModel.updateOptionNumbersList(numberList = optionNumbersList)
        } else if (responseState == ResponseState.Success) {
            gameViewModel.updateCorrectAnswer(
                answerList = actualQn,
                answerType = AnswerType.Computer
            )
        }

    }


    LaunchedEffect(userAnswerList.size) {

        val currentSize = (userAnswerList.size * userAnswerBoxSize.value).toInt() + 35
        currentUserAnswerRowSize.intValue = currentSize

        boxState.value = when {
            currentSize < quarterScreenWidthDp -> BoxSize.LessThanQuarter
            currentSize < halfScreenWidthDp -> BoxSize.LessThanHalf
            else -> BoxSize.GreaterThanHalf
        }

        if (userAnswerList.isNotEmpty()) {
            if (userAnswer == correctAnswer) {
                Log.i("correct answer", "right answer")
            }
            gameViewModel.getAnswer(answerList = userAnswerList, answerType = AnswerType.User)

        } else {
            Log.i("get answer else", "nothing")
        }

    }
    LaunchedEffect(isUserGuessed) {
        if (isUserGuessed) {
            if (!gameViewModel.isTimedMode) {
                gameViewModel.updateUserGuessedCorrectAnswerList()
                gameOverNavigationEndlessMode()
            } else {
                gameViewModel.reset()
                gameViewModel.generateQuestions()

            }
        }

    }
    BackHandler {
        isExitPopupOpen.value =true
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = {
                        if (gameViewModel.isTimedMode) {
                            gameViewModel.timeController.pause()
                            gameViewModel.updateTimerStatus(status = TimerStatus.Paused)
                        }
                        isExitPopupOpen.value = true

                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (responseState == ResponseState.Loading) {
                Loading()

            } else {
                Box(
                    modifier = Modifier
                        .weight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(MaterialTheme.dimens.gameDimensions.padding08),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(MaterialTheme.dimens.gameDimensions.padding08),
                                text = if (correctAnswer != null) correctAnswer.toString() else "?",
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = Purple

                                )
                            )
                            Column(
                                modifier = Modifier
                                    .weight(0.1f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (gameViewModel.isTimedMode) {
                                    Text(
                                        text = stringResource(id = R.string.time),
                                        style = TextStyle(
                                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.secondary,

                                            )
                                    )
                                    TimerTransition(
                                        timer = time.toString()

                                    )

                                }
                            }

                        }
                        Text(
                            modifier = Modifier
                                .padding(MaterialTheme.dimens.gameDimensions.padding16)
                                .fillMaxWidth(),
                            text = if (userAnswer != null && usersAnswerList.isNotEmpty()) userAnswer.toString() else "?",
                            style = TextStyle(
                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary

                            )
                        )

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            state = listState,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            itemsIndexed(usersAnswerList) { idx, userAnswerDataItem ->
                                DataItemCard(
                                    modifier = Modifier
                                        .size(boxAnimateSize.value)
                                        .padding(0.dp)
                                        .animateItem()
                                        .animateContentSize(),
                                    dataItem = userAnswerDataItem,
                                    shape = RoundedCornerShape(0),
                                    fontSize = 20.sp,
                                    selectAction = { gameViewModel.removeUserAnswerList(idx = idx) },
                                )


                            }
                        }

                    }


                }

                Box(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.dimens.gameDimensions.pageHorizontalPadding16,
                            vertical = MaterialTheme.dimens.gameDimensions.pageVerticalPadding08
                        )
                        .weight(0.4f),
                    contentAlignment = Alignment.Center
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .wrapContentSize(),
                        columns = GridCells.Fixed(2),
                    ) {

                        itemsIndexed(optionNumbers) { idx, numberDataItem ->
                            DataItemCard(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(MaterialTheme.dimens.gameDimensions.padding08)
                                    .animateItem(),
                                dataItem = numberDataItem,
                                selectAction = {
                                    if (!numberDataItem.isSelected) {
                                        if (currentUserAnswerRowSize.intValue.dp < screenWidth.dp) {
                                            gameViewModel.updateOptionNumbersValues(
                                                idx = idx,
                                                isSelected = true
                                            )
                                        } else {
                                            Log.i(
                                                "Row size",
                                                currentUserAnswerRowSize.intValue.toString()
                                            )
                                            Log.i("Row size total width", screenWidth.toString())
                                            Log.i("Row size", "Row exceeded")
                                        }

                                    }
                                }
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
                        itemsIndexed(operatorsList) { idx, operatorDataItem ->
                            DataItemCard(
                                modifier = Modifier
                                    .size(70.dp)
                                    .fillMaxWidth()
                                    .animateItem(),
                                color = MaterialTheme.colorScheme.primary,
                                dataItem = operatorDataItem,
                                shape = RoundedCornerShape(0),
                                selectAction = {
                                    if (currentUserAnswerRowSize.intValue.dp < screenWidth.dp) {
                                        gameViewModel.updateOperatorList(idx = idx)

                                    } else {
                                        Log.i("Row size", currentUserAnswerRowSize.intValue.toString())
                                        Log.i("Row size", "row exceeded")
                                    }


                                }
                            )
                        }
                    }
                }
            }

            if (isTimedModeDialogOpen.value) {
                TimedModeDialog(
                    onDismissRequest = {}
                )
            }
            if (isExitPopupOpen.value) {
                ExitAlertDialogExample(
                    onDismissRequest = {
                        isExitPopupOpen.value = false
                        gameViewModel.timeController.resume()
                    },
                    onConfirmation = {
                        exitNavigation()
                        gameViewModel.updateTimerStatus(status = TimerStatus.Empty)
                    },
                    dialogTitle = "Exit the mode",
                    dialogText = "Do you want to exit the mode",
                    icon = Icons.Default.ExitToApp
                )
//            }
            }

        }

    }

}



