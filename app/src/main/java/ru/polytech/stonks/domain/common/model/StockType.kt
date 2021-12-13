package ru.polytech.stonks.domain.common.model

enum class StockType {
    STOCK, FUND, OBLIGATION, CURRENCY;

    companion object {
        fun fromString(string: String): StockType = when (string) {
            "stock" -> STOCK
            "etf" -> CURRENCY
            "fund" -> FUND
            "trust" -> OBLIGATION
            else -> STOCK
        }
    }
}