package ru.polytech.stonks.domain.common.model

sealed class Period {
    object Day : Period()
    object Month: Period()
    object Year: Period()
}
