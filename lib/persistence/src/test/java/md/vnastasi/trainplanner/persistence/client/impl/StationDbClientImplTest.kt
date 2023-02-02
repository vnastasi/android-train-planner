package md.vnastasi.trainplanner.persistence.client.impl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isSuccess
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import md.vnastasi.trainplanner.domain.createStation
import md.vnastasi.trainplanner.persistence.dao.StationDao
import md.vnastasi.trainplanner.test.core.doReturnFlowOf
import org.junit.jupiter.api.Test

internal class StationDbClientImplTest {

    private val mockStationDao = mock<StationDao>()

    private val stationDbClient = StationDbClientImpl(mockStationDao)

    @Test
    internal fun testFindBySearchQuery() = runTest {
        val query = "haag"
        val station = createStation()

        whenever(mockStationDao.findBySearchQuery(query)).doReturnFlowOf(listOf(station.toStationEntity()))

        stationDbClient.findBySearchQuery(query).test {
            assertThat(awaitItem()).containsExactly(station)
            awaitComplete()
        }
    }

    @Test
    internal fun testFindFavourites() = runTest {
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findFavourites(limit)).doReturnFlowOf(listOf(station.toStationEntity()))

        stationDbClient.findFavourites(limit).test {
            assertThat(awaitItem()).containsExactly(station)
            awaitComplete()
        }
    }

    @Test
    internal fun testFindRecent() = runTest {
        val limit = 10
        val station = createStation()

        whenever(mockStationDao.findRecent(limit)).doReturnFlowOf(listOf(station.toStationEntity()))

        stationDbClient.findRecent(limit).test {
            assertThat(awaitItem()).containsExactly(station)
            awaitComplete()
        }
    }

    @Test
    internal fun testInsert() = runTest {
        val station = createStation()

        assertThat { stationDbClient.insert(station) }.isSuccess()

        verifyBlocking(mockStationDao) { insert(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testUpdate() = runTest {
        val station = createStation()

        assertThat { stationDbClient.update(station) }.isSuccess()

        verifyBlocking(mockStationDao) { update(eq(station.toStationEntity())) }
    }

    @Test
    internal fun testDeleteAll() = runTest {
        assertThat { stationDbClient.deleteAll() }.isSuccess()

        verifyBlocking(mockStationDao) { deleteAll() }
    }
}
