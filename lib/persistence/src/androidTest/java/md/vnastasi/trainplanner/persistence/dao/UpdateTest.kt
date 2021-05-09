package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedStationType
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import md.vnastasi.trainplanner.persistence.util.expectOneElement
import md.vnastasi.trainplanner.domain.SampleStations
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
        databaseRule.stationDao.findBySearchQuery("de vink").expectOneElement { list ->
            assertThat(list).all {
                hasSize(1)
                contains(newStation)
            }
        }
    }
}
