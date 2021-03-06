package ru.polytech.stonks.presentation.feathers.catalog.model

import androidx.compose.ui.text.input.TextFieldValue
import ru.polytech.stonks.domain.common.model.*
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock

data class CatalogState(
    val stocks: List<Stock> = emptyList(),
    val selectedStockType: StockType = StockType.STOCK,
    val isSearchEnabled: Boolean = false,
    val searchText: TextFieldValue = EMPTY_SEARCH_VALUE,
    val isFavorsEnabled: Boolean = false,
    val searchHints: List<String> = emptyList()
) {
    companion object {

        val EMPTY_SEARCH_VALUE = TextFieldValue("")

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
                    imageUrl = "https://pbs.twimg.com/media/EBEaNVtUcAEZp3E.png",
                    type = StockType.STOCK
                )
            }
        )
    }
}