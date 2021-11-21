package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isNotEmpty
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
internal class DeleteAllTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Before
    fun setUp() {
        listOf(AMSTERDAM_CENTRAL, DE_VINK, DEN_BOSCH, ARNHEM_CENTRAL).forEach { runBlocking { databaseRule.stationDao.insert(it.toStationEntity()) } }
    }

    @Test
    fun testDeleteAll() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("").test {
            assertThat(awaitItem()).isNotEmpty()

            databaseRule.stationDao.deleteAll()
            assertThat(awaitItem()).isEmpty()

            cancelAndConsumeRemainingEvents()
        }
    }
}
