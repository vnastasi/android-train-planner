package md.vnastasi.trainplanner.splash.usecase.impl

import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase

internal class CheckCredentialsUseCaseImpl(
    private val credentialsStorageRepository: CredentialsStorageRepository
) : CheckCredentialsUseCase {

    override suspend fun execute(): Boolean = credentialsStorageRepository.isStored()
}
