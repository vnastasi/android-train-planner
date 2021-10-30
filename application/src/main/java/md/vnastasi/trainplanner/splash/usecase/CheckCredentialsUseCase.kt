package md.vnastasi.trainplanner.splash.usecase

import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository

class CheckCredentialsUseCase(
    private val credentialsStorageRepository: CredentialsStorageRepository
) {

    suspend fun execute(): Boolean = credentialsStorageRepository.isStored()
}
