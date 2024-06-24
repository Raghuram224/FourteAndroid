package com.example.fourteandroid.view.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fourteandroid.R
import kotlinx.coroutines.delay

@Composable
fun Intro(
    modifier: Modifier = Modifier,
    isTimedMode: Boolean = false,
    navigation: () -> Unit,
) {
    Log.i("Timed",isTimedMode.toString() )
    LaunchedEffect(Unit) {
        delay(2000)
        navigation()
    }
    Scaffold(
        containerColor = if (isTimedMode) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    id = if (isTimedMode) R.string.buckle_up
                    else R.string.solve_them_all
                ),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontSize = 40.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewIntro() {
    Intro(navigation = {})
}