import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.data.DataItem

@Composable
fun LazyRowExample(usersAnswerList: List<String>,) {
    val lazyListState = rememberLazyListState()

    val firstVisibleItemIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    // Check if the first visible item is not the first item or if there's an offset (scrolled horizontally)
    val isScrolled by remember {
        derivedStateOf {
            firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
        }
    }

    LazyRow(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        itemsIndexed(usersAnswerList) { idx, userAnswerDataItem ->
            Box(
                modifier = Modifier
                    .padding(8.dp)

            ) {
                DataItemCard(
                    modifier = Modifier
                        .size(width = 35.dp, height = 40.dp)
                        .padding(0.dp)
                        .clip(RoundedCornerShape(0)),
                    dataItem = userAnswerDataItem,

                )
            }
        }
    }

    // Display a message if the LazyRow is scrolled
    if (isScrolled) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Items have exceeded the screen width",
                color = Color.Red,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Dummy data classes and composable functions for the sake of completeness



@Composable
fun DataItemCard(
    modifier: Modifier=Modifier,
    dataItem: String,

) {
    Box(modifier = modifier
        .size(35.dp)
    ){
        Text(text = dataItem)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewLazRo() {
    val itemList = listOf(
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
        "hello",
    )
    LazyRowExample(usersAnswerList = itemList)
}
