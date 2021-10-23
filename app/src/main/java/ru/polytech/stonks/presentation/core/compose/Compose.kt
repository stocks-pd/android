package ru.polytech.stonks.presentation.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.polytech.stonks.resourses.AppColors
import ru.polytech.stonks.resourses.Montserrat

object Compose {

    @Composable
    fun ToolbarWithText(text: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = AppColors.white),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = Montserrat.Bold700.SP18,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

    @Composable
    fun Snap() {
        Surface(color = AppColors.white) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Экран находится в разработке",
                    style = Montserrat.SemiBold600.SP16,
                    color = AppColors.grayPlaceholder
                )
            }
        }
    }
}

@Preview
@Composable
fun ToolbarWithTextPreview() {
    Compose.ToolbarWithText(
        text = "Toolbar"
    )
}

@Preview
@Composable
fun SnapPreview() {
    Compose.Snap()
}