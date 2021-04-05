package md.vnastasi.trainplanner.api.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.domain.station.DistanceAwareStation
import md.vnastasi.trainplanner.domain.station.Station

interface StationsApiClient {

    fun getStations(): Flow<List<Station>>

    fun getNearbyStations(latitude: Double, longitude: Double, limit: Int): Flow<List<DistanceAwareStation>>
}
