package md.vnastasi.trainplanner.api.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.client.DisruptionsApiClient
import md.vnastasi.trainplanner.api.client.RawDisruptionsApiClient
import md.vnastasi.trainplanner.api.domain.disturbance.DisruptionWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.DisturbanceTypeWrapper
import md.vnastasi.trainplanner.api.domain.error.ApiErrorReasonWrapper
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.domain.disruption.Disruption

internal class DisruptionsApiClientImpl(
    private val rawApiClient: RawDisruptionsApiClient,
    private val jsonSerializer: Json
) : DisruptionsApiClient {

    override fun getDisruptions(): Flow<List<Disruption>> = flow {
        val list = safeExecute(
            serializer = jsonSerializer,
            call = { rawApiClient.getDisruptions(listOf(DisturbanceTypeWrapper.DISRUPTION, DisturbanceTypeWrapper.MAINTENANCE)) },
            onSuccessMap = { list -> list.map(DisruptionWrapper::toDisruption) },
            onErrorMap = { it.toApiFailureReason() }
        )

        emit(list)
    }

    private fun ApiErrorReasonWrapper.toApiFailureReason(): ApiFailureReason = when (this) {
        ApiErrorReasonWrapper.UNKNOWN_FAILURE -> ApiFailureReason.Unknown
        ApiErrorReasonWrapper.UNPARSABLE_RESPONSE -> ApiFailureReason.UnparsableResponse
        ApiErrorReasonWrapper.NS_SERVICE_FAILURE -> ApiFailureReason.NsServerError
        else -> ApiFailureReason.Unknown
    }
}
