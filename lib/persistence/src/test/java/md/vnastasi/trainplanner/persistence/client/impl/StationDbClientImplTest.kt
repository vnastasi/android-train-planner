package md.vnastasi.trainplanner.persistence.client.impl

import assertk.all
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.isSuccess
import assertk.assertions.prop
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.toList
import md.vnastasi.trainplanner.createStation
import md.vnastasi.trainplanner.persistence.dao.StationDao
import md.vnastasi.trainplanner.persistence.util.assertThatBlocking
import md.vnastasi.trainplanner.persistence.util.doReturnFlow
import org.junit.jupiter.api.Test

internal class StationDbClientImplTest {

    private val mockStationDao = mock<StationDao>()

    private val stationDbClient = StationDbClientImpl(mockStationDao)

    @Test
    internal fun testFindBySearchQuery() {
        val query = "haag"
        val station = createStation()

        whenever(mockStationDao.findBySearchQuery(query)).doReturnFlow(listOf(station.toStationEntity()))

        assertThatBlocking { stationDbClient.findBySearchQuery(query).toList(mutableListOf()) }
                .isSuccess()
                .all {
                    hasSize(1)
                    prop("element") { it[0] }.contains(station)
                }
    }

    @Test
    internal fun testFindFavourites() {
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findFavourites(limit)).doReturnFlow(listOf(station.toStationEntity()))

        assertThatBlocking { stationDbClient.findFavourites(limit).toList(mutableListOf()) }
                .isSuccess()
                .all {
                    hasSize(1)
                    prop("element") { it[0] }.contains(station)
                }
    }

    @Test
    internal fun testFindRecent() {
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findRecent(limit)).doReturnFlow(listOf(station.toStationEntity()))

        assertThatBlocking { stationDbClient.findRecent(limit).toList(mutableListOf()) }
                .isSuccess()
                .all {
                    hasSize(1)
                    prop("element") { it[0] }.contains(station)
                }
    }

    @Test
    internal fun testInsert() {
        val station = createStation()

        assertThatBlocking { stationDbClient.insert(station) }.isSuccess()

        verifyBlocking(mockStationDao) { insert(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testUpdate() {
        val station = createStation()

        assertThatBlocking { stationDbClient.update(station) }.isSuccess()

        verifyBlocking(mockStationDao) { update(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testDeleteAll() {
        assertThatBlocking { stationDbClient.deleteAll() }.isSuccess()

        verifyBlocking(mockStationDao) { deleteAll() }
    }
}
