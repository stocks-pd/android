package ru.polytech.stonks.data.util

import android.content.Context
import android.content.SharedPreferences

fun defaultPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
}

private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"