package md.vnastasi.trainplanner.login.repository

interface AuthenticationRepository {

    suspend fun authenticate(userName: String, password: String)
}
