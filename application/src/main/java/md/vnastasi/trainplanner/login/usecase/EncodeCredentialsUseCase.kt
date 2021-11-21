package md.vnastasi.trainplanner.login.usecase

interface EncodeCredentialsUseCase {

    fun execute(userName: String, password: String): String
}
