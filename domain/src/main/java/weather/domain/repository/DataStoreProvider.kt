package weather.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class DataStoreProvider private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DataStoreProvider? = null

        fun getInstance(context: Context): DataStoreProvider {
            return instance ?: synchronized(this) {
                instance ?: DataStoreProvider(context).also { instance = it }
            }
        }
    }

    val weatherDataStore: DataStore<Preferences> by lazy {
        context.weatherDataDataStore
    }

    private val Context.weatherDataDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "Weather"
    )
}