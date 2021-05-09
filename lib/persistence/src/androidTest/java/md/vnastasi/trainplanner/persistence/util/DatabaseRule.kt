package md.vnastasi.trainplanner.persistence.util

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import md.vnastasi.trainplanner.persistence.ApplicationDatabase
import md.vnastasi.trainplanner.persistence.dao.StationDao
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class DatabaseRule : TestWatcher() {

    private var database: ApplicationDatabase? = null

    val stationDao: StationDao
        get() = requireNotNull(database).stationDao

    override fun starting(description: Description?) {
        super.starting(description)
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Application>(), ApplicationDatabase::class.java).build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        database?.clearAllTables()
        database?.close()
    }
}
