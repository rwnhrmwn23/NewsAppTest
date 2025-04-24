package com.onedev.newsapptest.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object RecentSearchManager {
    private val Context.dataStore by preferencesDataStore(name = "recent_search")

    private val RECENT_KEY = stringSetPreferencesKey("recent_keywords")

    suspend fun saveSearch(context: Context, query: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[RECENT_KEY] ?: emptySet()
            preferences[RECENT_KEY] = (current + query).toList().takeLast(5).toSet()
        }
    }

    fun getSearches(context: Context): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[RECENT_KEY]?.toList()?.reversed() ?: emptyList()
        }
    }
}
