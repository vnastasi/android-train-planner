package md.vnastasi.trainplanner.login.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.login.repository.AuthenticationRepository
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository

class PerformAuthenticationUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val credentialsStorageRepository: CredentialsStorageRepository,
    private val encodeCredentialsUseCase: EncodeCredentialsUseCase
) {

    fun execute(userName: String, password: String): Flow<Result<Unit>> = flow {
        try {
            authenticationRepository.authenticate(userName, password)
            val credentials = encodeCredentialsUseCase.execute(userName, password)
            credentialsStorageRepository.store(credentials)
            emit(Result.success(Unit))
        } catch (e: ApplicationException) {
            emit(Result.failure<Unit>(e))
        }
    }
}
