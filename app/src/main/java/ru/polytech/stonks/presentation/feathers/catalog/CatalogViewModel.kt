package ru.polytech.stonks.presentation.feathers.catalog

import androidx.compose.ui.text.input.TextFieldValue
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
) : BaseViewModel<CatalogState, CatalogAction, CatalogEvent>(CatalogState.stub) {

    override fun obtainEvent(event: CatalogEvent) {
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
        }
    }

    private fun search() {
        // todo search
    }
}