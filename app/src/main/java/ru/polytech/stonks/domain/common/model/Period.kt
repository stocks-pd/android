package ru.polytech.stonks.domain.common.model

sealed class Period {
    object Day : Period()
    object Month: Period()
    object Year: Period()

    companion object {
        fun fromString(string: String): Period = when (string) {
            "DAY" -> Day
            "MONTH" -> Month
            "YEAR" -> Year
            else -> Day
        }
    }
}
