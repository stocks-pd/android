package ru.polytech.stonks.presentation.feathers.catalog

import ru.polytech.stonks.presentation.core.base.BaseViewModel
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogAction
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogEvent
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogState
import javax.inject.Inject

class CatalogViewModel @Inject constructor(

) : BaseViewModel<CatalogState, CatalogAction, CatalogEvent>(CatalogState.stub) {

    override fun obtainEvent(event: CatalogEvent) {
        when (event) {
            CatalogEvent.OnFavorsClicked -> updateState(
                state.value.copy(isFavorsEnabled = !state.value.isFavorsEnabled)
            )
            CatalogEvent.OnFiltersClicked -> { }
            is CatalogEvent.OnItemClicked -> { }
            CatalogEvent.OnSearchClicked -> {
                if (state.value.isSearchEnabled) {
                    // todo cash query
                    updateState(
                        state.value.copy(isSearchEnabled = false)
                    )
                } else {
                    updateState(
                        state.value.copy(isSearchEnabled = true)
                    )
                }
            }
            CatalogEvent.OnSortsClicked -> { }
            is CatalogEvent.OnTypeChanged -> updateState(
                state.value.copy(selectedStockType = event.type)
            )
            CatalogEvent.OnClearSearchClicked -> updateState(
                state.value.copy(searchText = CatalogState.EMPTY_SEARCH_VALUE)
            )
            is CatalogEvent.OnSearchTextValueChanged -> updateState(
                state.value.copy(searchText = event.newValue)
            )
        }
    }
}