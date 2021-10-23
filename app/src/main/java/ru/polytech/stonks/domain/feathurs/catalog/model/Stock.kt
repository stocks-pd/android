package ru.polytech.stonks.domain.feathurs.catalog.model

import ru.polytech.stonks.domain.common.model.Price
import ru.polytech.stonks.domain.common.model.StockType

data class Stock(
    val ticker: String,
    val name: String,
    val price: Price,
    val imageUrl: String,
    val type: StockType
)