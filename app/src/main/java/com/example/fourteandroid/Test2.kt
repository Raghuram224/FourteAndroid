import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun ScrollableLazyRowExample() {
    val items = List(10) { "Item $it" }
    val listState = rememberLazyListState()
    val localDensity = LocalDensity.current
    val totalWidth by remember {
        derivedStateOf {
            with(localDensity) {
                listState.layoutInfo.visibleItemsInfo.sumOf { it.size }.toDp()
            }
        }
    }

    Column {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Gray)
                ) {
                    BasicText(text = item, style = TextStyle(fontSize = 24.sp))
                }
            }
        }

        Text("Total width of items: $totalWidth")
        Text("Parent width: fillMaxWidth()")
        if (totalWidth > 300.dp) {
           Text(text = "Total width,${totalWidth.toString()}")
            Text("The list is scrollable.")
        } else {
            Text("The list is not scrollable yet.")
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,

)
@Composable
fun ScrollableLazyRowExamplePreview() {
    ScrollableLazyRowExample()
}
