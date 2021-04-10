package md.vnastasi.trainplanner.persistence.domain.convert

import androidx.room.TypeConverter
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedStationType

internal object StationTypeConverter {

    @JvmStatic
    @TypeConverter
    fun convertStationTypeToString(value: EmbeddedStationType?): String? = value?.name

    @JvmStatic
    @TypeConverter
    fun convertStringToStationType(value: String?): EmbeddedStationType? = value?.let { EmbeddedStationType.valueOf(it) }
}
