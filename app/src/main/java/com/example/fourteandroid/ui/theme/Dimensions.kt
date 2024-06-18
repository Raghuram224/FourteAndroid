package com.example.fourteandroid.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class GameDimensions(
    val padding02: Dp = 4.dp,
    val padding04: Dp = 4.dp,
    val padding08: Dp = 8.dp,
    val padding10: Dp = 8.dp,
    val padding12: Dp = 12.dp,
    val padding16: Dp = 16.dp,
    val padding20: Dp = 20.dp,

    val pageHorizontalPadding16: Dp = 16.dp,
    val pageVerticalPadding16: Dp = 16.dp,
    val pageVerticalPadding08: Dp = 8.dp,
    val pageHorizontalPadding08: Dp = 8.dp,
    val itemHorizontalPadding04: Dp = 4.dp,
    val itemHorizontalPadding08: Dp = 8.dp,
    val itemVerticalPadding08: Dp = 8.dp,
    val itemVerticalPadding04: Dp = 8.dp,
    val itemHorizontalPadding16: Dp = 16.dp,
    val itemVerticalPadding16: Dp = 16.dp
)

data class CoreDimensions(
    val padding02: Dp = 4.dp,
    val padding04: Dp = 4.dp,
    val padding08: Dp = 8.dp,
    val padding10: Dp = 8.dp,
    val padding12: Dp = 12.dp,
    val padding16: Dp = 16.dp,
    val padding20: Dp = 20.dp,

    val pageHorizontalPadding16: Dp = 16.dp,
    val pageVerticalPadding16: Dp = 16.dp,
    val pageVerticalPadding08: Dp = 8.dp,
    val pageHorizontalPadding08: Dp = 8.dp,
    val itemHorizontalPadding04: Dp = 4.dp,
    val itemHorizontalPadding08: Dp = 8.dp,
    val itemVerticalPadding08: Dp = 8.dp,
    val itemVerticalPadding04: Dp = 8.dp,
    val itemHorizontalPadding16: Dp = 16.dp,
    val itemVerticalPadding16: Dp = 16.dp,



    // Medium icons
    val iconSize30: Dp = 30.dp,
    val iconSize35:Dp = 35.dp,
    val iconSize20: Dp = 20.dp,
    val iconSize25:Dp = 25.dp,

    //card elevations
    val elevation10:Dp=10.dp,
    val elevation20:Dp=20.dp,
)

data class Dimensions(
    val gameDimensions: GameDimensions = GameDimensions(),
    val coreDimensions: CoreDimensions = CoreDimensions(),

    )

val LocalDimension = compositionLocalOf { Dimensions() }

val MaterialTheme.dimens:Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimension.current