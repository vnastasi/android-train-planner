package md.vnastasi.trainplanner.persistence.domain.convert

import androidx.room.TypeConverter
import java.time.Instant

internal object InstantConverter {

    @JvmStatic
    @TypeConverter
    fun convertInstantToLong(value: Instant?): Long? = value?.toEpochMilli()

    @JvmStatic
    @TypeConverter
    fun convertLongToInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }
}
