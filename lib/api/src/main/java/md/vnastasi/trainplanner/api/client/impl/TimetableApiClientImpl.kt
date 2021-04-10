package md.vnastasi.trainplanner.api.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.client.RawTimetableApiClient
import md.vnastasi.trainplanner.api.client.TimetableApiClient
import md.vnastasi.trainplanner.api.domain.error.ApiErrorReasonWrapper
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.domain.board.Arrival
import md.vnastasi.trainplanner.domain.board.Departure

internal class TimetableApiClientImpl(
        private val rawApiClient: RawTimetableApiClient,
        private val jsonSerializer: Json
) : TimetableApiClient {

    override suspend fun getDepartures(code: String): Flow<List<Departure>> = flow {
        val departures = safeExecute(
                serializer = jsonSerializer,
                call = { rawApiClient.getDepartures(code) },
                onSuccessMap = { body -> body.map { it.toDeparture() } },
                onErrorMap = { it.toApiFailureReason(code) }
        )

        emit(departures)
    }

    override suspend fun getArrivals(code: String): Flow<List<Arrival>> = flow {
        val arrivals = safeExecute(
                serializer = jsonSerializer,
                call = { rawApiClient.getArrivals(code) },
                onSuccessMap = { body -> body.map { it.toArrival() } },
                onErrorMap = { it.toApiFailureReason(code) }
        )

        emit(arrivals)
    }

    private fun ApiErrorReasonWrapper.toApiFailureReason(stationCode: String): ApiFailureReason = when (this) {
        ApiErrorReasonWrapper.UNKNOWN_FAILURE -> ApiFailureReason.Unknown
        ApiErrorReasonWrapper.UNPARSABLE_RESPONSE -> ApiFailureReason.UnparsableResponse
        ApiErrorReasonWrapper.NS_SERVICE_FAILURE -> ApiFailureReason.NsServerError
        ApiErrorReasonWrapper.NO_ARRIVALS_FOR_STATION -> ApiFailureReason.NoArrivals(stationCode)
        ApiErrorReasonWrapper.NO_DEPARTURES_FOR_STATION -> ApiFailureReason.NoDepartures(stationCode)
        ApiErrorReasonWrapper.UNKNOWN_STATION -> ApiFailureReason.UnknownStation(stationCode)
    }
}
