package ru.polytech.stonks.domain.common.model

data class PriceDifference(
    val percent: Double,
    val isGrows: Boolean,
) {
    val displayed: String = "${if (isGrows) "↑" else "↓"} $percent%"
}