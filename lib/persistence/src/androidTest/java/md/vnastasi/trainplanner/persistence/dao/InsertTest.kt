package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isNotEqualTo
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class InsertTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Test
    fun testInsertConflict() = runBlocking {
        val oldStation = SampleStations.AMSTERDAM_CENTRAL.toStationEntity().also { databaseRule.stationDao.insert(it) }
        val newStation = SampleStations.AMSTERDAM_CENTRAL.copy(countryCode = "DE").toStationEntity().also { databaseRule.stationDao.insert(it) }

        assertThat(oldStation).isNotEqualTo(newStation)

        databaseRule.stationDao.findBySearchQuery("amsterdam").test {
            assertThat(awaitItem()).containsExactly(newStation)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testInsertWithoutConflict() = runBlocking {
        val oldStation = SampleStations.AMSTERDAM_CENTRAL.toStationEntity().also { databaseRule.stationDao.insert(it) }
        val newStation = SampleStations.AMSTERDAM_CENTRAL.copy(countryCode = "DE", code = "new").toStationEntity().also { databaseRule.stationDao.insert(it) }

        assertThat(oldStation).isNotEqualTo(newStation)

        databaseRule.stationDao.findBySearchQuery("amsterdam").test {
            assertThat(awaitItem()).containsExactlyInAnyOrder(oldStation, newStation)
            cancelAndConsumeRemainingEvents()
        }
    }
}
