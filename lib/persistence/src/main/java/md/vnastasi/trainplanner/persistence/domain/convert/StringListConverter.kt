package md.vnastasi.trainplanner.persistence.domain.convert

import androidx.room.TypeConverter

internal object StringListConverter {

    @JvmStatic
    @TypeConverter
    fun convertListToString(value: List<String>?): String = value?.joinToString(";").orEmpty()

    @JvmStatic
    @TypeConverter
    fun convertStringToList(value: String?): List<String> = value?.takeUnless { it.isEmpty() }?.split(";")?.toList() ?: emptyList()
}
