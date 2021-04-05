package md.vnastasi.trainplanner.api.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.domain.board.Arrival
import md.vnastasi.trainplanner.domain.board.Departure

interface TimetableApiClient {

    suspend fun getDepartures(code: String): Flow<List<Departure>>

    suspend fun getArrivals(code: String): Flow<List<Arrival>>
}
