package com.evans.senditapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore("my_data_store")
class UserPreferencies(
    context: Context
) {
    private val dataStore = context.dataStore
    private val applicationContext = context.applicationContext
//    private val dataStore: DataStore<Preferences>
//
//    init {
//        dataStore = applicationContext.createDataStore(
//            name = "my_data_store"
//        )
//    }

    suspend fun saveAuthToken(authToken: String){
        dataStore.edit {
            
        }
    }
}