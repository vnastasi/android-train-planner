package md.vnastasi.trainplanner.persistence.util

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.domain.station.StationType
import md.vnastasi.trainplanner.persistence.ApplicationDatabase
import md.vnastasi.trainplanner.persistence.client.impl.toStationEntity
import md.vnastasi.trainplanner.persistence.dao.StationDao
import md.vnastasi.trainplanner.persistence.data.createStation
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class DatabaseRule : TestWatcher() {

    private var database: ApplicationDatabase? = null

    val stationDao: StationDao
        get() = requireNotNull(database).stationDao

    override fun starting(description: Description?) {
        super.starting(description)
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Application>(), ApplicationDatabase::class.java).build()
        populateDatabase()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        database?.clearAllTables()
        database?.close()
    }

    private fun populateDatabase() {
        createRecords().forEach { runBlocking { stationDao.insert(it.toStationEntity()) } }
    }

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
