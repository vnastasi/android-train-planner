package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.SampleStations.AMSTERDAM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.ARNHEM_CENTRAL
import md.vnastasi.trainplanner.domain.SampleStations.DEN_BOSCH
import md.vnastasi.trainplanner.domain.SampleStations.DE_VINK
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
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
        databaseRule.stationDao.findBySearchQuery("").test {
            assertThat(awaitItem()).containsExactlyInAnyOrder(
                    AMSTERDAM_CENTRAL.toStationEntity(),
                    DEN_BOSCH.toStationEntity(),
                    DE_VINK.toStationEntity(),
                    ARNHEM_CENTRAL.toStationEntity()
            )

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testSearchByLongName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("AAL").test {
            assertThat(awaitItem()).containsExactlyInAnyOrder(
                    AMSTERDAM_CENTRAL.toStationEntity(),
                    ARNHEM_CENTRAL.toStationEntity()
            )

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testSearchByMiddleName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("c.").test {
            assertThat(awaitItem()).containsExactlyInAnyOrder(
                    AMSTERDAM_CENTRAL.toStationEntity(),
                    ARNHEM_CENTRAL.toStationEntity()
            )

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testSearchByShortName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("den").test {
            assertThat(awaitItem()).containsExactly(DEN_BOSCH.toStationEntity())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testSearchBySynonym() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("CS").test {
            assertThat(awaitItem()).containsExactlyInAnyOrder(
                    AMSTERDAM_CENTRAL.toStationEntity(),
                    ARNHEM_CENTRAL.toStationEntity()
            )

            cancelAndConsumeRemainingEvents()
        }
    }
}
