package ru.polytech.stonks.domain.common.model

data class Price(
    val value: Double,
    val currency: Currency,
    val difference: Map<Period, PriceDifference>,
) {
    val displayed: String = "$value ${currency.symbol}"
}
