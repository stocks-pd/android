package ru.polytech.stonks.data.catalog.data_sources

import io.ktor.client.request.*
import ru.polytech.stonks.data.util.ApplicationHttpClient
import ru.polytech.stonks.domain.feathurs.catalog.model.Stock
import javax.inject.Inject

class KtorCatalogDataSource @Inject constructor(
    private val http: ApplicationHttpClient
) {

    suspend fun getCatalog(): List<Stock> {
        return http.client.post {
            url {
                path("stocks")
                header("Content-Type", "application/json")
                // todo add filters
            }
        }
    }
}