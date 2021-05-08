package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import md.vnastasi.trainplanner.persistence.util.TestStations
import md.vnastasi.trainplanner.persistence.util.expectOneElement
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(AndroidJUnit4::class)
internal class FindRecentTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Test
    fun testNoLastUsedStations() = runBlocking {
        TestStations.DEN_BOSCH.copy(lastUsed = null).toStationEntity().also { databaseRule.stationDao.insert(it) }
        TestStations.AMSTERDAM_CENTRAL.copy(lastUsed = null).toStationEntity().also { databaseRule.stationDao.insert(it) }

        databaseRule.stationDao.findRecent(10).expectOneElement { list ->
            assertThat(list).isEmpty()
        }
    }

    @Test
    fun testAllLastUsedStationsMarkedAsFavourites() = runBlocking {
        TestStations.DEN_BOSCH.copy(lastUsed = Instant.now(), isFavourite = true).toStationEntity().also { databaseRule.stationDao.insert(it) }
        TestStations.AMSTERDAM_CENTRAL.copy(lastUsed = Instant.now(), isFavourite = true).toStationEntity().also { databaseRule.stationDao.insert(it) }

        databaseRule.stationDao.findRecent(10).expectOneElement { list ->
            assertThat(list).isEmpty()
        }
    }

    @Test
    fun testListOrderAndLimit() = runBlocking {
        val station1 = TestStations.DEN_BOSCH.copy(lastUsed = Instant.now(), isFavourite = false).toStationEntity()
        val station2 = TestStations.AMSTERDAM_CENTRAL.copy(lastUsed = Instant.now(), isFavourite = false).toStationEntity()

        databaseRule.stationDao.insert(station1)
        databaseRule.stationDao.insert(station2)

        databaseRule.stationDao.findRecent(1).expectOneElement { list ->
            assertThat(list).all {
                hasSize(1)
                contains(station2)
            }
        }
    }
}
