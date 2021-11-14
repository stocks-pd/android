package ru.polytech.stonks.domain.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Price(
    @SerialName("value") val value: Double,
    @SerialName("currency") val currencyText: String,
    @SerialName("differences") val difference: List<PriceDifference>,
) {
    @Transient val displayed: String = "$value $currencyText"
    @Transient val currency: Currency = Currency.fromString(currencyText)
}
