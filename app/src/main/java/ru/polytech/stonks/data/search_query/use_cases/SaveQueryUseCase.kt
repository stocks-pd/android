package ru.polytech.stonks.data.search_query.use_cases

import ru.polytech.stonks.data.search_query.data_sources.SharedPreferencesQueriesDataSource
import javax.inject.Inject

class SaveQueryUseCase @Inject constructor(
    private val localDataSource: SharedPreferencesQueriesDataSource
)  {

    operator fun invoke(query: String) {
        localDataSource.saveQuery(query)
    }
}