package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.hasSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.station.StationType
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.data.createStation
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
        createRecords().forEach { runBlocking { databaseRule.stationDao.insert(it.toStationEntity()) } }
    }

    @Test
    fun testEmptySearchQuery() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("").expectOneElement { list ->
            assertThat(list).hasSize(4)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("'s-Hertogenbosch", "Arnhem Centraal", "De Vink", "Amsterdam Centraal")
        }
    }

    @Test
    fun testSearchByLongName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("AAL").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    @Test
    fun testSearchByMiddleName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("c.").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    @Test
    fun testSearchByShortName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("den").expectOneElement { list ->
            assertThat(list).hasSize(1)
            assertThat(list).extracting { it.names.longName }.contains("'s-Hertogenbosch")
        }
    }

    @Test
    fun testSearchBySynonym() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("CS").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    private suspend inline fun <T> Flow<T>.expectOneElement(crossinline action: suspend (T) -> Unit) = take(1).collect(action)

    private fun createRecords() = listOf(
            createStation {
                code = "0001"
                shortName = "Den Bosch"
                middleName = "'s-Hertogenbosch"
                longName = "'s-Hertogenbosch"
                synonyms = listOf("Hertogenbosch ('s)", "Den Bosch")
                countryCode = "NL"
                type = StationType.INTERCITY_JUNCTION_STATION
                tracks = listOf("1", "3", "3a", "3b", "4", "4a", "4b", "6", "6a", "6b", "7", "7a", "7b")
                latitude = 51.69048
                longitude = 5.29362
            },
            createStation {
                code = "0002"
                shortName = "Amsterdam C"
                middleName = "Amsterdam C."
                longName = "Amsterdam Centraal"
                synonyms = listOf("Amsterdam", "Amsterdam CS")
                countryCode = "NL"
                type = StationType.MAJOR_STATION
                tracks = listOf("1", "2a", "2b", "4", "4a", "4b", "5", "5a", "5b", "7", "7a", "7b", "8", "8a", "8b", "10", "10a", "10b", "11", "11a", "11b", "13", "13a", "13b", "14", "14a", "14b", "15", "15a", "15b")
                latitude = 52.3788871765137
                longitude = 4.90027761459351
            },
            createStation {
                code = "0003"
                shortName = "Arnhem C"
                middleName = "Arnhem C."
                longName = "Arnhem Centraal"
                synonyms = listOf("Arnhem", "Arnhem CS")
                countryCode = "NL"
                type = StationType.INTERCITY_JUNCTION_STATION
                tracks = listOf("3", "4", "6", "6a", "6b", "7", "8", "9", "10", "11")
                latitude = 51.9850006103516
                longitude = 5.89916658401489
            },
            createStation {
                code = "0004"
                shortName = "De Vink"
                middleName = "De Vink"
                longName = "De Vink"
                synonyms = emptyList()
                countryCode = "NL"
                type = StationType.STOP_TRAIN_STATION
                tracks = listOf("1", "2", "3", "4")
                latitude =  52.1472206115723
                longitude = 4.4563889503479
            }
    )
}
