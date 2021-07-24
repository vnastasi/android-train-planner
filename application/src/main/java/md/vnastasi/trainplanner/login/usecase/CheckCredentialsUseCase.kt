package md.vnastasi.trainplanner.login.usecase

import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository

class CheckCredentialsUseCase(
    private val credentialsStorageRepository: CredentialsStorageRepository
) {

    suspend fun execute(): Boolean = credentialsStorageRepository.isStored()
}
