package md.vnastasi.trainplanner.login.repository

interface CredentialsStorageRepository {

    suspend fun isStored(): Boolean

    suspend fun store(credentials: String)

    suspend fun clear()

    companion object {

       const val DATA_STORE_NAME = "storage"
       const val KEY_CREDENTIALS = "credentials"
    }
}
