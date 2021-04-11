package md.vnastasi.trainplanner.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import md.vnastasi.trainplanner.persistence.dao.StationDao
import md.vnastasi.trainplanner.persistence.domain.convert.InstantConverter
import md.vnastasi.trainplanner.persistence.domain.convert.StationTypeConverter
import md.vnastasi.trainplanner.persistence.domain.convert.StringListConverter
import md.vnastasi.trainplanner.persistence.domain.station.StationEntity

@Database(entities = [StationEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [StationTypeConverter::class, StringListConverter::class, InstantConverter::class])
internal abstract class ApplicationDatabase : RoomDatabase() {

    abstract val stationDao: StationDao
}
