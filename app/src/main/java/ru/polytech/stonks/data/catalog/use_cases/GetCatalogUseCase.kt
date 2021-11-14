package ru.polytech.stonks.data.catalog.use_cases

import ru.polytech.stonks.data.catalog.data_sources.KtorCatalogDataSource
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(
    private val catalogDataSource: KtorCatalogDataSource
) {
    suspend operator fun invoke(): List<Stock> {
        return catalogDataSource.getCatalog()
    }
}