package ru.polytech.stonks.data.search_query.data_sources

import android.content.SharedPreferences
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.min

class SharedPreferencesQueriesDataSource @Inject constructor(private val pref: SharedPreferences) {

    private var queriesJson: String?
        get() {
            return pref.getString(QUERIES_KEY, null)
        }
        set(value) {
            pref.edit().putString(QUERIES_KEY, value).apply()
        }

    fun getSavedQueries(): List<String> {
        return queriesJson?.let {
            Json.decodeFromString(ListSerializer(String.serializer()), it)
        }
            ?: emptyList()
    }

    fun saveQuery(query: String) {
        val list = getSavedQueries().toMutableList().let {
            it.remove(query)
            it.subList(0, min(it.size, 14))
        }
        list.add(0, query)

        queriesJson = Json.encodeToString(ListSerializer(String.serializer()), list)
    }

    fun clear(): List<String> {
        val list = emptyList<String>()
        queriesJson = Json.encodeToString(ListSerializer(String.serializer()), list)
        return list
    }

    companion object {
        private const val QUERIES_KEY = "QUERIES_KEY"
    }
}