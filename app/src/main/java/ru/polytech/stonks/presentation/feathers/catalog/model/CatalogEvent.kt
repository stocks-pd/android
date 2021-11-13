package ru.polytech.stonks.presentation.feathers.catalog.model

import androidx.compose.ui.text.input.TextFieldValue
import ru.polytech.stonks.domain.common.model.StockType
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock

sealed class CatalogEvent {
    class OnTypeChanged(val type: StockType) : CatalogEvent()
    object OnFavorsClicked : CatalogEvent()
    object OnSortsClicked : CatalogEvent()
    object OnFiltersClicked : CatalogEvent()
    object OnSearchClicked : CatalogEvent()
    object OnClearSearchClicked : CatalogEvent()
    class OnSearchTextValueChanged(val newValue: TextFieldValue) : CatalogEvent()
    object OnClearSearchHistoryClicked : CatalogEvent()
    class OnHintClicked(val hint: String) : CatalogEvent()

    class OnItemClicked(val item: Stock) : CatalogEvent()
}