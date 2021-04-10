package md.vnastasi.trainplanner.persistence.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.domain.station.Station

interface StationDbClient {

    fun findBySearchQuery(query: String): Flow<List<Station>>

    fun findFavourites(limit: Int): Flow<List<Station>>

    fun findRecent(limit: Int): Flow<List<Station>>

    suspend fun insert(station: Station)

    suspend fun update(station: Station)

    suspend fun deleteAll()
}
