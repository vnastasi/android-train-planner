package md.vnastasi.trainplanner.login.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.login.repository.AuthenticationRepository
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository

class PerformAuthenticationUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val credentialsStorageRepository: CredentialsStorageRepository,
    private val encodeCredentialsUseCase: EncodeCredentialsUseCase
) {

    fun execute(userName: String, password: String): Flow<AsyncResult<Unit>> = flow {
        emit(AsyncResult.Loading)
        try {
            authenticationRepository.authenticate(userName, password)
            val credentials = encodeCredentialsUseCase.execute(userName, password)
            credentialsStorageRepository.store(credentials)
            emit(AsyncResult.Success(Unit))
        } catch (e: ApplicationException) {
            emit(AsyncResult.Failure(e))
        }
    }
}
