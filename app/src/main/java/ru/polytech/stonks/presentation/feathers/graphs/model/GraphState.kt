package ru.polytech.stonks.presentation.feathers.graphs.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

class GraphState(var drawMode: DrawMode = DrawMode.THREE_GRAPHS) {

    private var entries by mutableStateOf(emptyList<GraphEntry>())
    private val points by mutableStateOf(hashMapOf<CanvasPoint, GraphEntry>())
    private var normalized by mutableStateOf(emptyList<Pair<NormalizedEntry, GraphEntry>>())

    private var size: Size? = null

    fun updatePoints(entries: List<GraphEntry>) {
        this.entries = entries

        val maxY = entries.maxOf { it.high }
        val maxX = entries.maxOf { it.date }
        val minY = entries.minOf { it.low }
        val minX = entries.minOf { it.date }

        normalized = entries
            .map { it.normalize(maxX, maxY, minX, minY) to it }
            .sortedBy { it.first.date }
    }

    fun DrawScope.drawGraph(
        strokeWidth: Float = 6f,
        cap: StrokeCap = StrokeCap.Round,
        brush: Brush? = null,
    ) {
        this@GraphState.size = size

        normalized.forEachIndexed { index, entry ->

            val next = normalized[if (index == normalized.lastIndex) index else index + 1]

            drawLine(
                brush = brush ?: Brush.linearGradient(
                    0f to Color.Black,
                    1f to Color.Black,
                ),
                start = Offset(
                    entry.first.date.toFloat() * (size.width),
                    entry.first.middle.toFloat() * (size.height)
                ),
                end = Offset(
                    next.first.date.toFloat() * (size.width),
                    next.first.middle.toFloat() * (size.height)
                ),
                strokeWidth = strokeWidth,
                cap = cap,
            )

            if (drawMode == DrawMode.THREE_GRAPHS) {
                drawLine(
                    brush = brush ?: Brush.linearGradient(
                        0f to Color.Green,
                        1f to Color.Green,
                    ),
                    start = Offset(
                        entry.first.date.toFloat() * (size.width),
                        entry.first.high.toFloat() * (size.height)
                    ),
                    end = Offset(
                        next.first.date.toFloat() * (size.width),
                        next.first.high.toFloat() * (size.height)
                    ),
                    strokeWidth = strokeWidth,
                    cap = cap,
                )

                drawLine(
                    brush = brush ?: Brush.linearGradient(
                        0f to Color.Red,
                        1f to Color.Red,
                    ),
                    start = Offset(
                        entry.first.date.toFloat() * (size.width),
                        entry.first.low.toFloat() * (size.height)
                    ),
                    end = Offset(
                        next.first.date.toFloat() * (size.width),
                        next.first.low.toFloat() * (size.height)
                    ),
                    strokeWidth = strokeWidth,
                    cap = cap,
                )
            }
        }
    }

    private fun GraphEntry.normalize(
        maxX: Long,
        maxY: Double,
        minX: Long,
        minY: Double
    ): NormalizedEntry {

        return NormalizedEntry(
            date = ((date - minX.toDouble()) / (maxX - minX)),
            middle = (middle - minY) / (maxY - minY),
            low = (low - minY) / (maxY - minY),
            high = (high - minY) / (maxY - minY),
            risk = risk,
        )
    }

    fun getPoint(coordinates: Offset): GraphEntry {
        return normalized.map { entry ->
            Offset(
                entry.first.date.toFloat() * (size?.width ?: 1f),
                entry.first.middle.toFloat() * (size?.height ?: 1f)
            ) to entry.second
        }
            .fold(initial = Offset(-1f, -1f) to normalized[0].second) { offset, entry ->
                if (entry.first.x <= coordinates.x) {
                    entry
                } else {
                    offset
                }
            }
            .second
    }

    data class CanvasPoint(val x: Dp, val y: Dp)

    private data class NormalizedEntry(
        // values from 0.0 to 1.0
        val date: Double,
        val middle: Double,
        val low: Double,
        val high: Double,
        val risk: Double,
    )

    enum class DrawMode {
        ONE_GRAPH,
        THREE_GRAPHS,
    }
}
