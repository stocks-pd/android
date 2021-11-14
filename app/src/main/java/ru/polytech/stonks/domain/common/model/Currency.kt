package ru.polytech.stonks.domain.common.model

sealed class Currency(val symbol: String) {

    object Ruble : Currency("₽")

    object Dollar : Currency("$")

    object Euro : Currency("€")

    object Unknown : Currency("x")

    companion object {
        fun fromString(string: String): Currency = when (string) {
            "USD" -> Dollar
            "RUB" -> Ruble
            "EUR" -> Euro
            else -> Unknown
        }
    }
}
