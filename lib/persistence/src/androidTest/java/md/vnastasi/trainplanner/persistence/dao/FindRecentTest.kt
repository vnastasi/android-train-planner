package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
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
        SampleStations.DEN_BOSCH.copy(lastUsed = null).toStationEntity().also { databaseRule.stationDao.insert(it) }
        SampleStations.AMSTERDAM_CENTRAL.copy(lastUsed = null).toStationEntity().also { databaseRule.stationDao.insert(it) }

        databaseRule.stationDao.findRecent(10).test {
            assertThat(awaitItem()).isEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testAllLastUsedStationsMarkedAsFavourites() = runBlocking {
        SampleStations.DEN_BOSCH.copy(lastUsed = Instant.now(), isFavourite = true).toStationEntity().also { databaseRule.stationDao.insert(it) }
        SampleStations.AMSTERDAM_CENTRAL.copy(lastUsed = Instant.now(), isFavourite = true).toStationEntity().also { databaseRule.stationDao.insert(it) }

        databaseRule.stationDao.findRecent(10).test {
            assertThat(awaitItem()).isEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testListOrderAndLimit() = runBlocking {
        val station1 = SampleStations.DEN_BOSCH.copy(lastUsed = Instant.now(), isFavourite = false).toStationEntity()
        val station2 = SampleStations.AMSTERDAM_CENTRAL.copy(lastUsed = Instant.now().plusMillis(1), isFavourite = false).toStationEntity()

        databaseRule.stationDao.insert(station1)
        databaseRule.stationDao.insert(station2)

        databaseRule.stationDao.findRecent(1).test {
            assertThat(awaitItem()).containsExactly(station2)
            cancelAndConsumeRemainingEvents()
        }
    }
}
