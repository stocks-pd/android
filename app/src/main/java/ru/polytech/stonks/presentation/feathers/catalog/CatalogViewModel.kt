package ru.polytech.stonks.presentation.feathers.catalog

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.polytech.stonks.data.catalog.use_cases.GetCatalogUseCase
import ru.polytech.stonks.data.search_query.use_cases.ClearSavedQueriesUseCase
import ru.polytech.stonks.data.search_query.use_cases.GetSavedQueriesUseCase
import ru.polytech.stonks.data.search_query.use_cases.SaveQueryUseCase
import ru.polytech.stonks.presentation.core.base.BaseViewModel
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogAction
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogEvent
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogState
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getSavedQueriesUseCase: GetSavedQueriesUseCase,
    private val saveQueryUseCase: SaveQueryUseCase,
    private val clearSavedQueriesUseCase: ClearSavedQueriesUseCase,
    private val getCatalogUseCase: GetCatalogUseCase,
) : BaseViewModel<CatalogState, CatalogAction, CatalogEvent>(CatalogState()) {

    override fun obtainEvent(event: CatalogEvent) = launchUnit {
        when (event) {
            CatalogEvent.OnFavorsClicked -> updateState(
                state.value.copy(isFavorsEnabled = !state.value.isFavorsEnabled)
            )

            CatalogEvent.OnFiltersClicked -> { }

            is CatalogEvent.OnItemClicked -> { }

            CatalogEvent.OnSearchClicked -> {
                updateState(state.value.copy(searchHints = getSavedQueriesUseCase()))
                if (state.value.isSearchEnabled) {
                    if (state.value.searchText != CatalogState.EMPTY_SEARCH_VALUE) {
                        search()
                        saveQueryUseCase(state.value.searchText.text)
                    }
                }
                updateState(state.value.copy(isSearchEnabled = !state.value.isSearchEnabled))
            }

            is CatalogEvent.OnHintClicked -> {
                updateState(
                    state.value.copy(
                        searchText = state.value.searchText.copy(text = event.hint),
                        isSearchEnabled = false
                    )
                )
                search()
            }

            CatalogEvent.OnClearSearchHistoryClicked -> updateState(
                state.value.copy(searchHints = clearSavedQueriesUseCase())
            )

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
            CatalogEvent.OnCreate -> {
                state.value = state.value.copy(
                    isLoading = true
                )
                state.value = state.value.copy(
                    stocks = getCatalogUseCase(),
                    isLoading = false
                )
            }
        }
    }

    private fun search() {
        // todo search
    }
}