package ru.polytech.stonks.presentation.feathers.graphs.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.polytech.stonks.presentation.core.compose.Click
import ru.polytech.stonks.presentation.feathers.graphs.model.GraphEntry
import ru.polytech.stonks.presentation.feathers.graphs.model.GraphState
import ru.polytech.stonks.resourses.AppColors

object GraphUI {

    @Composable
    fun Graph(
        graphState: GraphState = GraphState(),
        height: Dp = 512.dp,
    ) {
        var offset by remember { mutableStateOf(Offset(0f, 0f)) }
        var isBaloonVisible by remember { mutableStateOf(false) }
        var baloonPoint by remember { mutableStateOf<GraphEntry?>(null) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height + 32.dp)
                .background(color = AppColors.white),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .pointerInput("key") {
                        this.detectTapGestures(
                            onTap = {
                                baloonPoint = graphState.getPoint(it)
                                isBaloonVisible = true
                                offset = it
                            }
                        )
                    },
                onDraw = {
                    with(graphState) {
                        drawGraph()
                    }
                }
            )

            Baloon(
                offset = offset,
                isVisible = isBaloonVisible,
                point = baloonPoint,
                onCloseClick = { isBaloonVisible = false }
            )

        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun Baloon(
        offset: Offset,
        isVisible: Boolean,
        point: GraphEntry?,
        onCloseClick: Click
    ) {
        if (point != null) {
            AnimatedVisibility(visible = isVisible) {
                Box(
                    modifier = Modifier
                        .size(width = 72.dp, height = 56.dp)
                        .absoluteOffset {
                            IntOffset(
                                offset.x.toInt(),
                                offset.y.toInt()
                            )
                        }
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.Green)
                        .clickable { onCloseClick() }
                ) {
                    Text(
                        text = "price ${point.middle} risk ${point.risk}"
                    ) // todo нужно сделать по дизайну, сорян нет интернета в поезде :)
                }
            }
        }
    }

}

@Preview(showBackground = true, heightDp = 920)
@Composable
private fun GraphPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.gray),
    ) {
        Text(
            text = "Some view",
            fontSize = 25.sp,
            modifier = Modifier.padding(32.dp)
        )

        val state = GraphState(drawMode = GraphState.DrawMode.THREE_GRAPHS)
        state.updatePoints(Json.decodeFromString(pointsJson))

        GraphUI.Graph(
            graphState = state,
            height = 256.dp,
        )

        Text(
            text = "Some view",
            fontSize = 25.sp,
            modifier = Modifier.padding(32.dp)
        )
    }
}

private val pointsJson = """
    [
        {
            "ds": 1639094400000,
            "yhat": 923.3624380647,
            "yhat_lower": 733.2894253011,
            "yhat_upper": 1112.4341666347,
            "risk": 7.34
        },
        {
            "ds": 1639353600000,
            "yhat": 925.7431871186,
            "yhat_lower": 739.7972701309,
            "yhat_upper": 1122.6983027601,
            "risk": 7.34
        },
        {
            "ds": 1639440000000,
            "yhat": 925.7705324242,
            "yhat_lower": 743.2878324792,
            "yhat_upper": 1096.4407748635,
            "risk": 7.34
        },
        {
            "ds": 1639526400000,
            "yhat": 927.6368933047,
            "yhat_lower": 728.2173801529,
            "yhat_upper": 1111.6103253441,
            "risk": 7.34
        },
        {
            "ds": 1639612800000,
            "yhat": 927.9096265773,
            "yhat_lower": 734.9329950464,
            "yhat_upper": 1115.2309153601,
            "risk": 7.34
        },
        {
            "ds": 1639699200000,
            "yhat": 925.6980399308,
            "yhat_lower": 736.4916049058,
            "yhat_upper": 1108.3675751425,
            "risk": 7.34
        },
        {
            "ds": 1639958400000,
            "yhat": 925.303405028,
            "yhat_lower": 738.1640191102,
            "yhat_upper": 1117.2612556494,
            "risk": 7.34
        }
    ]
""".trimIndent()