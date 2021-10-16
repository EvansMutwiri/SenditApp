package com.evans.senditapp

import android.content.Context

class PreferencesProvider(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("userPreferences", 0)

    fun putBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    //retrieve boolean with default value false
    fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    //String values
    fun putString(key: String, value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    //Integer values
    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInteger(key: String): Int = sharedPreferences.getInt(key, 0)

}