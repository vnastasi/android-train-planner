package md.vnastasi.trainplanner.api.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import md.vnastasi.trainplanner.api.client.RawStationsApiClient
import md.vnastasi.trainplanner.api.client.StationsApiClient
import md.vnastasi.trainplanner.api.exception.ApiFailureReason
import md.vnastasi.trainplanner.domain.error.ApiErrorReason
import md.vnastasi.trainplanner.domain.station.DistanceAwareStation
import md.vnastasi.trainplanner.domain.station.Station

internal class StationsApiClientImpl(
        private val rawApiClient: RawStationsApiClient,
        private val jsonSerializer: Json
) : StationsApiClient {

    override fun getStations(): Flow<List<Station>> = flow {
        val stations = safeExecute(
                serializer = jsonSerializer,
                call = { rawApiClient.getStations() },
                onSuccessMap = { body -> body.map { it.toStation() } },
                onErrorMap = { it.toApiFailureReason() }
        )

        emit(stations)
    }

    override fun getNearbyStations(latitude: Double, longitude: Double, limit: Int): Flow<List<DistanceAwareStation>> = flow {
        val stations = safeExecute(
                serializer = jsonSerializer,
                call = { rawApiClient.getNearbyStations(latitude, longitude, limit) },
                onSuccessMap = { body -> body.map { it.toDistanceAwareStation() } },
                onErrorMap = { it.toApiFailureReason() }
        )

        emit(stations)
    }

    private fun ApiErrorReason.toApiFailureReason(): ApiFailureReason = when (this) {
        ApiErrorReason.UNKNOWN_FAILURE -> ApiFailureReason.Unknown
        ApiErrorReason.UNPARSABLE_RESPONSE -> ApiFailureReason.UnparsableResponse
        ApiErrorReason.NS_SERVICE_FAILURE -> ApiFailureReason.NsFailure
        else -> ApiFailureReason.Unknown
    }
}
