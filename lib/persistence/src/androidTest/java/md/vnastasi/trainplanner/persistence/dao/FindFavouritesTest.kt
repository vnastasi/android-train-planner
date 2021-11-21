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

@RunWith(AndroidJUnit4::class)
internal class FindFavouritesTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Test
    fun testEmptyList() = runBlocking {
        SampleStations.DEN_BOSCH.copy(isFavourite = false).toStationEntity().also { databaseRule.stationDao.insert(it) }
        SampleStations.ARNHEM_CENTRAL.copy(isFavourite = false).toStationEntity().also { databaseRule.stationDao.insert(it) }

        databaseRule.stationDao.findFavourites(10).test {
            assertThat(awaitItem()).isEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testListLimit() = runBlocking {
        val station1 = SampleStations.DEN_BOSCH.copy(isFavourite = true).toStationEntity()
        val station2 = SampleStations.ARNHEM_CENTRAL.copy(isFavourite = true).toStationEntity()

        databaseRule.stationDao.insert(station1)
        databaseRule.stationDao.insert(station2)

        databaseRule.stationDao.findFavourites(1).test {
            assertThat(awaitItem()).containsExactly(station1)
            cancelAndConsumeRemainingEvents()
        }
    }
}
