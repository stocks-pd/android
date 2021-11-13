package ru.polytech.stonks.data.search_query.use_cases

import ru.polytech.stonks.data.search_query.data_sources.SharedPreferencesQueriesDataSource
import javax.inject.Inject

class GetSavedQueriesUseCase @Inject constructor(
    private val localDataSource: SharedPreferencesQueriesDataSource
) {

    operator fun invoke(): List<String> = localDataSource.getSavedQueries()

}