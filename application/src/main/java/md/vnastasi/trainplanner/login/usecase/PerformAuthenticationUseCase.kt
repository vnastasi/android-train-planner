package md.vnastasi.trainplanner.login.usecase

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.async.AsyncResult

interface PerformAuthenticationUseCase {

    fun execute(userName: String, password: String): Flow<AsyncResult<Unit>>
}
