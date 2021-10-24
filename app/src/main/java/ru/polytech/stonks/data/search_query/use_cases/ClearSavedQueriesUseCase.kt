package ru.polytech.stonks.data.search_query.use_cases

import ru.polytech.stonks.data.search_query.data_sources.SharedPreferencesQueriesDataSource
import javax.inject.Inject

class ClearSavedQueriesUseCase @Inject constructor(
    private val localDataSource: SharedPreferencesQueriesDataSource
)  {

    operator fun invoke(): List<String> {
        return localDataSource.clear()
    }
}