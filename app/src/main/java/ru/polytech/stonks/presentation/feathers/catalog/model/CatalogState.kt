package ru.polytech.stonks.presentation.feathers.catalog.model

import ru.polytech.stonks.domain.common.model.Currency
import ru.polytech.stonks.domain.common.model.Period
import ru.polytech.stonks.domain.common.model.Price
import ru.polytech.stonks.domain.common.model.PriceDifference
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock

data class CatalogState(
    val stocks: List<Stock> = emptyList()
) {
    companion object {
        val stub = CatalogState(
            stocks = List(40) {
                Stock(
                    ticker = "GGLE",
                    name = "Google",
                    price = Price(
                        value = 123.12,
                        currency = Currency.Dollar,
                        difference = mapOf(
                            Period.Day to PriceDifference(percent = 23.1, isGrows = true)
                        )
                    ),
                    imageUrl = "https://pbs.twimg.com/media/EBEaNVtUcAEZp3E.png"
                )
            }
        )
    }
}