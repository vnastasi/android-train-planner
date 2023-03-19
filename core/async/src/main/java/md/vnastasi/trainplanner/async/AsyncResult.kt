package md.vnastasi.trainplanner.async

import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.exception.FailureReason

sealed class AsyncResult<out T : Any> : java.io.Serializable {

    object Loading : AsyncResult<Nothing>()

    data class Success<out T : Any>(val data: T) : AsyncResult<T>()

    data class Failure(val exception: ApplicationException) : AsyncResult<Nothing>()
}

fun <T : Any> Result<T>.toAsyncResult(): AsyncResult<T> =
    if (this.isSuccess) {
        AsyncResult.Success(this.getOrThrow())
    } else {
        val exception = when (val cause = this.exceptionOrNull()) {
            is ApplicationException -> cause
            else -> ApplicationException(FailureReason.unknown(), cause)
        }
        AsyncResult.Failure(exception)
    }
