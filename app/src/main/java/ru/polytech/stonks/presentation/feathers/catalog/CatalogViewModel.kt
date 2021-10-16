package ru.polytech.stonks.presentation.feathers.catalog

import ru.polytech.stonks.presentation.core.base.BaseViewModel
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogAction
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogEvent
import ru.polytech.stonks.presentation.feathers.catalog.model.CatalogState
import javax.inject.Inject

class CatalogViewModel @Inject constructor(

) : BaseViewModel<CatalogState, CatalogAction, CatalogEvent>(CatalogState.stub) {


    override fun obtainEvent(event: CatalogEvent) {

    }
}