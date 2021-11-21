package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedStationType
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class UpdateTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Test
    fun testUpdate() = runBlocking {
        val oldStation = SampleStations.DE_VINK.toStationEntity().also { databaseRule.stationDao.insert(it) }
        val newStation = oldStation.copy(type = EmbeddedStationType.INTERCITY_STATION)

        databaseRule.stationDao.update(newStation)

        databaseRule.stationDao.findBySearchQuery("de vink").test {
            assertThat(awaitItem()).containsExactly(newStation)
            cancelAndConsumeRemainingEvents()
        }
    }
}
