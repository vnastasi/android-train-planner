package md.vnastasi.trainplanner.async

import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.exception.FailureReason

sealed class AsyncResult<out T : Any> {

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
            else -> ApplicationException(UnknownFailureReason(), cause)
        }
        AsyncResult.Failure(exception)
    }

private class UnknownFailureReason : FailureReason {

    override val code: String = "UNKNOWN"

    override val message: String = "Unknown failure"
}
