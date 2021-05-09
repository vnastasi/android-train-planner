package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertions.isEmpty
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations
import md.vnastasi.trainplanner.domain.SampleStations.AMSTERDAM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.ARNHEM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.DEN_BOSCH
import md.vnastasi.trainplanner.domain.SampleStations.DE_VINK
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import md.vnastasi.trainplanner.test.core.assertThatFlow
import md.vnastasi.trainplanner.test.core.hasData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class DeleteAllTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Before
    fun setUp() {
        listOf(AMSTERDAM_CENTRAL, DE_VINK, DEN_BOSCH, ARNHEM_CENTRAL).forEach { runBlocking { databaseRule.stationDao.insert(it.toStationEntity()) } }
    }

    @Test
    fun testDeleteAll() = runBlocking {
        SampleStations
        databaseRule.stationDao.deleteAll()

        assertThatFlow { databaseRule.stationDao.findBySearchQuery("") }
            .hasData()
            .isEmpty()
    }
}
