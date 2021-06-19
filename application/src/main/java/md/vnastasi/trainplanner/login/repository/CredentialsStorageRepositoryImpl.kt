package md.vnastasi.trainplanner.login.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CredentialsStorageRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : CredentialsStorageRepository {

    private val key = stringPreferencesKey(CredentialsStorageRepository.KEY_CREDENTIALS)

    override suspend fun isStored(): Boolean = dataStore.data.map { it.contains(key) }.first()

    override suspend fun store(credentials: String) {
        dataStore.edit { it[key] = credentials }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
