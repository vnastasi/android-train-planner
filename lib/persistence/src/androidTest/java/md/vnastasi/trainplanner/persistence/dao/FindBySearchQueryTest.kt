package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import kotlinx.coroutines.runBlocking
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
internal class FindBySearchQueryTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Before
    fun setUp() {
        listOf(AMSTERDAM_CENTRAL, DEN_BOSCH, DE_VINK, ARNHEM_CENTRAL).forEach { runBlocking { databaseRule.stationDao.insert(it.toStationEntity()) } }
    }

    @Test
    fun testEmptySearchQuery() = runBlocking {
        assertThatFlow { databaseRule.stationDao.findBySearchQuery("") }
            .hasData()
            .containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), DEN_BOSCH.toStationEntity(), DE_VINK.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
    }

    @Test
    fun testSearchByLongName() = runBlocking {
        assertThatFlow { databaseRule.stationDao.findBySearchQuery("AAL") }
            .hasData()
            .containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
    }

    @Test
    fun testSearchByMiddleName() = runBlocking {
        assertThatFlow { databaseRule.stationDao.findBySearchQuery("c.") }
            .hasData()
            .containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
    }

    @Test
    fun testSearchByShortName() = runBlocking {
        assertThatFlow { databaseRule.stationDao.findBySearchQuery("den") }
            .hasData()
            .containsExactly(DEN_BOSCH.toStationEntity())
    }

    @Test
    fun testSearchBySynonym() = runBlocking {
        assertThatFlow { databaseRule.stationDao.findBySearchQuery("CS") }
            .hasData()
            .containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
    }
}
