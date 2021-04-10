package md.vnastasi.trainplanner

import md.vnastasi.trainplanner.domain.station.Coordinates
import md.vnastasi.trainplanner.domain.station.NameHolder
import md.vnastasi.trainplanner.domain.station.Station
import md.vnastasi.trainplanner.domain.station.StationType
import java.time.OffsetDateTime

internal const val STATION_CODE = "8400380"
internal const val STATION_LONG_NAME = "Den Haag Laan v NOI"
internal const val STATION_MIDDLE_NAME = "DH Laan v NOI"
internal const val STATION_SHORT_NAME = "Laan v NOI"
internal const val STATION_SYNONYM_1 = "Laan van NOI"
internal const val STATION_SYNONYM_2 = "Laan van Nieuw Oost Indië"
internal const val COUNTRY_CODE = "NL"
internal const val LATITUDE = 52.0786094665527
internal const val LONGITUDE = 4.34277772903442

internal inline fun createStation(block: StationDsl.() -> Unit = {}) = StationDsl().apply(block).build()

internal class StationDsl(
        var code: String = STATION_CODE,
        var shortName: String = STATION_SHORT_NAME,
        var middleName: String = STATION_MIDDLE_NAME,
        var longName: String = STATION_LONG_NAME,
        var synonyms: List<String> = listOf(STATION_SYNONYM_1, STATION_SYNONYM_2),
        var type: StationType = StationType.STOP_TRAIN_JUNCTION_STATION,
        var countryCode: String = COUNTRY_CODE,
        var tracks: List<String> = listOf("3", "4", "5", "6"),
        var latitude: Double = LATITUDE,
        var longitude: Double = LONGITUDE,
        var isFavourite: Boolean = false,
        var lastUsed: OffsetDateTime? = null
) {

    fun build() = Station(
            code = this.code,
            names = NameHolder(shortName, middleName, longName),
            type = this.type,
            synonyms = this.synonyms.toList(),
            countryCode = this.countryCode,
            tracks = this.tracks.toList(),
            coordinates = Coordinates(latitude, longitude),
            isFavourite = this.isFavourite,
            lastUsed = this.lastUsed?.toInstant()
    )
}
