package ru.polytech.stonks.domain.common.model

sealed class Currency(val symbol: String) {

    object Ruble : Currency("₽")

    object Dollar : Currency("$")
}
