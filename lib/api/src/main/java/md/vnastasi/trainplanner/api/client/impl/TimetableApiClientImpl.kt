package md.vnastasi.trainplanner.api.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.client.RawTimetableApiClient
import md.vnastasi.trainplanner.api.client.TimetableApiClient
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.domain.board.Arrival
import md.vnastasi.trainplanner.domain.board.Departure
import md.vnastasi.trainplanner.domain.error.ApiErrorReason

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

    private fun ApiErrorReason.toApiFailureReason(stationCode: String): ApiFailureReason = when (this) {
        ApiErrorReason.UNKNOWN_FAILURE -> ApiFailureReason.Unknown
        ApiErrorReason.UNPARSABLE_RESPONSE -> ApiFailureReason.UnparsableResponse
        ApiErrorReason.NS_SERVICE_FAILURE -> ApiFailureReason.NsFailure
        ApiErrorReason.NO_ARRIVALS_FOR_STATION -> ApiFailureReason.NoArrivals(stationCode)
        ApiErrorReason.NO_DEPARTURES_FOR_STATION -> ApiFailureReason.NoDepartures(stationCode)
        ApiErrorReason.UNKNOWN_STATION -> ApiFailureReason.UnknownStation(stationCode)
    }
}
