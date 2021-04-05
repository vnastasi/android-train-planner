package md.vnastasi.trainplanner.api.client.impl

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.api.exception.MissingNetworkConnectionException
import md.vnastasi.trainplanner.domain.error.ApiError
import md.vnastasi.trainplanner.domain.error.ApiErrorReason
import md.vnastasi.trainplanner.exception.ApplicationException
import retrofit2.Response
import java.io.IOException

internal suspend fun <T, R> safeExecute(
        serializer: Json,
        call: suspend () -> Response<T>,
        onSuccessMap: (T) -> R,
        onErrorMap: (ApiErrorReason) -> ApiFailureReason
): R {
    val response = safeExecuteRequest(call)
    if (!response.isSuccessful) {
        val apiError = serializer.parseApiError(response)
        throw ApplicationException(onErrorMap.invoke(apiError.reason))
    }

    val body = response.body() ?: throw ApplicationException(ApiFailureReason.EmptyResponse)

    return onSuccessMap.invoke(body)
}

private suspend fun <T> safeExecuteRequest(block: suspend () -> Response<T>) =
        try {
            block.invoke()
        } catch (e: MissingNetworkConnectionException) {
            throw ApplicationException(ApiFailureReason.MissingInternetConnection)
        } catch (e: SerializationException) {
            throw ApplicationException(ApiFailureReason.UnparsableResponse, e)
        }

private fun Json.parseApiError(response: Response<*>): ApiError {
    val errorBody = response.errorBody() ?: throw ApplicationException(ApiFailureReason.EmptyResponse)

    return try {
        this.decodeFromString(errorBody.string()) ?: throw ApplicationException(ApiFailureReason.EmptyResponse)
    } catch (e: SerializationException) {
        throw ApplicationException(ApiFailureReason.UnparsableResponse, e)
    } catch (e: IOException) {
        throw ApplicationException(ApiFailureReason.Unknown, e)
    }
}
