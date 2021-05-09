package md.vnastasi.trainplanner.persistence.client.impl

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isSuccess
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations
import md.vnastasi.trainplanner.domain.createStation
import md.vnastasi.trainplanner.persistence.dao.StationDao
import md.vnastasi.trainplanner.test.core.assertThatFlow
import md.vnastasi.trainplanner.test.core.doReturnFlowOf
import md.vnastasi.trainplanner.test.core.hasData
import org.junit.jupiter.api.Test

internal class StationDbClientImplTest {

    private val mockStationDao = mock<StationDao>()

    private val stationDbClient = StationDbClientImpl(mockStationDao)

    @Test
    internal fun testFindBySearchQuery() = runBlocking {
        val query = "haag"
        val station = createStation()

        whenever(mockStationDao.findBySearchQuery(query)).doReturnFlowOf(listOf(station.toStationEntity()))

        assertThatFlow { stationDbClient.findBySearchQuery(query) }
            .hasData()
            .containsExactly(station)
    }

    @Test
    internal fun testFindFavourites() = runBlocking {
        SampleStations
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findFavourites(limit)).doReturnFlowOf(listOf(station.toStationEntity()))

        assertThatFlow { stationDbClient.findFavourites(limit) }
            .hasData()
            .containsExactly(station)
    }

    @Test
    internal fun testFindRecent() = runBlocking {
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findRecent(limit)).doReturnFlowOf(listOf(station.toStationEntity()))

        assertThatFlow { stationDbClient.findRecent(limit) }
            .hasData()
            .containsExactly(station)
    }

    @Test
    internal fun testInsert() = runBlocking {
        val station = createStation()

        assertThat { stationDbClient.insert(station) }.isSuccess()

        verifyBlocking(mockStationDao) { insert(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testUpdate() = runBlocking {
        val station = createStation()

        assertThat { stationDbClient.update(station) }.isSuccess()

        verifyBlocking(mockStationDao) { update(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testDeleteAll() = runBlocking {
        assertThat { stationDbClient.deleteAll() }.isSuccess()

        verifyBlocking(mockStationDao) { deleteAll() }
    }
}
