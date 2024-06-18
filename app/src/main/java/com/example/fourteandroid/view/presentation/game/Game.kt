import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.fourteandroid.view.data.ResponseState
import com.example.fourteandroid.view.presentation.game.DataItemCard
import com.example.fourteandroid.view.presentation.game.Loading
import com.example.fourteandroid.view.viewModels.GameViewModel
import kotlinx.coroutines.delay


@Composable
fun Game(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    val operatorsList = gameViewModel.operatorsList
    val responseState by gameViewModel.responseState.collectAsState()
    val userAnswerList = gameViewModel.userAnswerList
    val actualQn =gameViewModel.actualQn
    val optionNumbersList = gameViewModel.qnNumberList
    val userAnswer by gameViewModel.userAnswer.collectAsState()

    LaunchedEffect(Unit) {

        gameViewModel.generateQuestionElements()
//        gameViewModel.generateAnswer()

    }
    LaunchedEffect(responseState) {
        Log.i("answer state", responseState.toString())
        Log.i("answer  list", userAnswerList.toList().toString())

        if (responseState==ResponseState.QnGenerated){
            gameViewModel.updateOptionNumbersList(list = optionNumbersList)
//            gameViewModel.generateAnswer(userAnswerList =actualQn )
        }

    }
    LaunchedEffect(userAnswerList.size) {
        if (userAnswerList.isNotEmpty()){
            gameViewModel.generateAnswer(userAnswerList = userAnswerList)
        }

    }
    val optionNumbers = gameViewModel.optionNumbers
    val usersAnswerList = gameViewModel.userAnswerList


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
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = if (userAnswer!=null) userAnswer.toString() else "empty",
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
                            itemsIndexed(usersAnswerList) { idx, userAnswerDataItem ->
                                DataItemCard(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(0.dp),
                                    dataItem = userAnswerDataItem,
                                    selectAction = { gameViewModel.removeUserAnswerList(idx = idx) },
                                    shape = RoundedCornerShape(0)
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
                        modifier = Modifier
                            .wrapContentSize(),
                        columns = GridCells.Fixed(2),
                    ) {

                        itemsIndexed(optionNumbers) { idx, numberDataItem ->
                            DataItemCard(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(MaterialTheme.dimens.gameDimensions.padding08),
                                dataItem = numberDataItem,
                                selectAction = {
                                    if (!numberDataItem.isSelected) {
                                        gameViewModel.updateOptionNumbersValues(
                                            idx = idx,
                                            isSelected = true
                                        )
                                    }/*else{
                                        gameViewModel.updateOptionNumbersValues(
                                            idx = idx,
                                            isSelected = false
                                        )
                                        gameViewModel.removeUserAnswerList(idx = idx)
                                    }*/
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
                        itemsIndexed(operatorsList) { idx,operatorDataItem ->
                            DataItemCard(
                                modifier = Modifier
                                    .size(80.dp)
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.primary,
                                dataItem = operatorDataItem,
                                shape = RoundedCornerShape(0),
                                selectAction = {
                                    if (!operatorDataItem.isSelected){
                                        gameViewModel.updateOperatorList(idx =idx, isSelected = true )
                                    }

                                }
                            )
                        }
                    }
                }
            }


        }
    }

}

