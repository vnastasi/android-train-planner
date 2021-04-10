package md.vnastasi.trainplanner.api.client.impl

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.domain.error.ApiErrorReasonWrapper
import md.vnastasi.trainplanner.api.domain.error.ApiErrorWrapper
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.api.exception.MissingNetworkConnectionException
import md.vnastasi.trainplanner.exception.ApplicationException
import retrofit2.Response
import java.io.IOException

internal suspend fun <T, R> safeExecute(
        serializer: Json,
        call: suspend () -> Response<T>,
        onSuccessMap: (T) -> R,
        onErrorMap: (ApiErrorReasonWrapper) -> ApiFailureReason
): R {
    val response = safeExecuteCall(call)
    if (!response.isSuccessful) {
        val apiError = serializer.parseApiError(response)
        throw ApplicationException(onErrorMap.invoke(apiError.reason))
    }

    val body = response.body() ?: throw ApplicationException(ApiFailureReason.EmptyResponse)

    return onSuccessMap.invoke(body)
}

private suspend fun <T> safeExecuteCall(block: suspend () -> Response<T>) =
        try {
            block.invoke()
        } catch (e: MissingNetworkConnectionException) {
            throw ApplicationException(ApiFailureReason.MissingInternetConnection)
        } catch (e: SerializationException) {
            throw ApplicationException(ApiFailureReason.UnparsableResponse, e)
        }

private fun Json.parseApiError(response: Response<*>): ApiErrorWrapper {
    val errorBody = response.errorBody() ?: response.raiseServerError()

    return try {
        val bodyString = errorBody.string().takeUnless { it.isBlank() } ?: response.raiseServerError()
        this.decodeFromString(bodyString) ?: response.raiseServerError()
    } catch (e: SerializationException) {
        throw ApplicationException(ApiFailureReason.UnparsableResponse, e)
    } catch (e: IOException) {
        throw ApplicationException(ApiFailureReason.Unknown, e)
    }
}

private fun Response<*>.raiseServerError(): Nothing = throw ApplicationException(ApiFailureReason.ServerError(code()))
