package ru.polytech.stonks.domain.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class PriceDifference(
    @SerialName("changesPercentage") val percent: Double,
    @SerialName("change") val change: Double,
    @SerialName("isGrows") val isGrows: Boolean,
    @SerialName("period") val periodText: String,
) {
    @Transient val displayed: String = "${if (isGrows) "↑" else "↓"} $percent%"
    @Transient val period: Period = Period.fromString(periodText)
}