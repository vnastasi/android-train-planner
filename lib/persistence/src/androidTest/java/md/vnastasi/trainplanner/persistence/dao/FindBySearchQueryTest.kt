package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.hasSize
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import md.vnastasi.trainplanner.persistence.util.expectOneElement
import md.vnastasi.trainplanner.domain.SampleStations.AMSTERDAM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.ARNHEM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.DEN_BOSCH
import md.vnastasi.trainplanner.domain.SampleStations.DE_VINK
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
        databaseRule.stationDao.findBySearchQuery("").expectOneElement { list ->
            assertThat(list).hasSize(4)
            assertThat(list).containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), DEN_BOSCH.toStationEntity(), DE_VINK.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
        }
    }

    @Test
    fun testSearchByLongName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("AAL").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
        }
    }

    @Test
    fun testSearchByMiddleName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("c.").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
        }
    }

    @Test
    fun testSearchByShortName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("den").expectOneElement { list ->
            assertThat(list).hasSize(1)
            assertThat(list).containsExactly(DEN_BOSCH.toStationEntity())
        }
    }

    @Test
    fun testSearchBySynonym() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("CS").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).containsExactlyInAnyOrder(AMSTERDAM_CENTRAL.toStationEntity(), ARNHEM_CENTRAL.toStationEntity())
        }
    }
}
