package md.vnastasi.trainplanner.persistence.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import md.vnastasi.trainplanner.domain.station.Station
import md.vnastasi.trainplanner.persistence.client.StationDbClient
import md.vnastasi.trainplanner.persistence.dao.StationDao

internal class StationDbClientImpl(
        private val stationDao: StationDao
) : StationDbClient {

    override fun findBySearchQuery(query: String): Flow<List<Station>> =
            stationDao.findBySearchQuery(query).map { list -> list.map { it.toStation() } }

    override fun findFavourites(limit: Int): Flow<List<Station>> =
            stationDao.findFavourites(limit).map { list -> list.map { it.toStation() } }

    override fun findRecent(limit: Int): Flow<List<Station>> =
            stationDao.findRecent(limit).map { list -> list.map { it.toStation() } }

    override suspend fun insert(station: Station) {
       stationDao.insert(station.toStationEntity())
    }

    override suspend fun update(station: Station) {
        stationDao.update(station.toStationEntity())
    }

    override suspend fun deleteAll() {
        stationDao.deleteAll()
    }
}
