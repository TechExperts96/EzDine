package com.techexpert.app.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, vararg defValue: String): String? {
        val value = if (defValue.isNotEmpty()) defValue[0] else null
        return prefs.getString(key, value)
    }

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, vararg defValue: Int): Int {
        val value = if (defValue.isNotEmpty()) defValue[0] else 0
        return prefs.getInt(key, value)
    }

    fun putStringList(key: String, list: ArrayList<String>) {
        val json = gson.toJson(list)
        putString(key, json)
    }

    fun getStringList(key: String): ArrayList<String> {
        val json = getString(key)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, type)
    }
}
