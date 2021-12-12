package md.vnastasi.trainplanner.splash.usecase

interface CheckCredentialsUseCase {

    suspend fun execute(): Boolean
}
