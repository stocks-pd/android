package ru.polytech.stonks.domain.feathurs.catalog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.polytech.stonks.domain.common.model.Price
import ru.polytech.stonks.domain.common.model.StockType

@Serializable
data class Stock(
    @SerialName("symbol") val ticker: String,
    @SerialName("company") val name: String,
    @SerialName("price") val price: Price,
    @SerialName("image") val imageUrl: String,
    @SerialName("type") val typeText: String
) {
    @Transient val type: StockType = StockType.fromString(typeText)
}