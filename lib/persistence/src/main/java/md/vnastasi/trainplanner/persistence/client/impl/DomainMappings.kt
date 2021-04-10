package md.vnastasi.trainplanner.persistence.client.impl

import md.vnastasi.trainplanner.domain.station.Coordinates
import md.vnastasi.trainplanner.domain.station.NameHolder
import md.vnastasi.trainplanner.domain.station.Station
import md.vnastasi.trainplanner.domain.station.StationType
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedCoordinates
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedNameHolder
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedStationType
import md.vnastasi.trainplanner.persistence.domain.station.StationEntity

internal fun Coordinates.toEmbeddedCoordinates() = EmbeddedCoordinates(
        latitude = this.latitude,
        longitude = this.longitude
)

internal fun NameHolder.toEmbeddedNameHolder() = EmbeddedNameHolder(
        shortName = this.shortName,
        middleName = this.middleName,
        longName = this.longName
)

internal fun StationType.toEmbeddedStationType() = when (this) {
    StationType.MAJOR_STATION -> EmbeddedStationType.MAJOR_STATION
    StationType.STOP_TRAIN_STATION -> EmbeddedStationType.STOP_TRAIN_STATION
    StationType.STOP_TRAIN_JUNCTION_STATION -> EmbeddedStationType.STOP_TRAIN_JUNCTION_STATION
    StationType.INTERCITY_STATION -> EmbeddedStationType.INTERCITY_STATION
    StationType.INTERCITY_JUNCTION_STATION -> EmbeddedStationType.INTERCITY_JUNCTION_STATION
    StationType.OPTIONAL_STATION -> EmbeddedStationType.OPTIONAL_STATION
    StationType.UNKNOWN -> EmbeddedStationType.UNKNOWN
}

internal fun Station.toStationEntity() = StationEntity(
        code = this.code,
        countryCode = this.countryCode,
        type = this.type.toEmbeddedStationType(),
        names = this.names.toEmbeddedNameHolder(),
        synonyms = this.synonyms.toList(),
        coordinates = this.coordinates.toEmbeddedCoordinates(),
        tracks = this.tracks.toList(),
        isFavourite = this.isFavourite,
        lastUsed = this.lastUsed
)
