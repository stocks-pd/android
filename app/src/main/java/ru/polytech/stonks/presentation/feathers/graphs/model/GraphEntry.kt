package ru.polytech.stonks.presentation.feathers.graphs.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GraphEntry(
    @SerialName("ds") val date: Long,
    @SerialName("yhat") val middle: Double,
    @SerialName("yhat_lower") val low: Double,
    @SerialName("yhat_upper") val high: Double,
    @SerialName("risk") val risk: Double,
)
