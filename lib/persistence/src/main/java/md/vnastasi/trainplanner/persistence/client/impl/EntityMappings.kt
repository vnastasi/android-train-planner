package md.vnastasi.trainplanner.persistence.client.impl

import md.vnastasi.trainplanner.domain.station.Coordinates
import md.vnastasi.trainplanner.domain.station.NameHolder
import md.vnastasi.trainplanner.domain.station.Station
import md.vnastasi.trainplanner.domain.station.StationType
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedCoordinates
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedNameHolder
import md.vnastasi.trainplanner.persistence.domain.station.EmbeddedStationType
import md.vnastasi.trainplanner.persistence.domain.station.StationEntity

internal fun EmbeddedCoordinates.toCoordinates() = Coordinates(
        latitude = this.latitude,
        longitude = this.longitude
)

internal fun EmbeddedNameHolder.toNameHolder() = NameHolder(
        shortName = this.shortName,
        middleName = this.middleName,
        longName = this.longName
)

internal fun EmbeddedStationType.toStationType() = when (this) {
    EmbeddedStationType.MAJOR_STATION -> StationType.MAJOR_STATION
    EmbeddedStationType.INTERCITY_JUNCTION_STATION -> StationType.INTERCITY_JUNCTION_STATION
    EmbeddedStationType.INTERCITY_STATION -> StationType.INTERCITY_STATION
    EmbeddedStationType.STOP_TRAIN_STATION -> StationType.STOP_TRAIN_STATION
    EmbeddedStationType.STOP_TRAIN_JUNCTION_STATION -> StationType.STOP_TRAIN_JUNCTION_STATION
    EmbeddedStationType.OPTIONAL_STATION -> StationType.OPTIONAL_STATION
    EmbeddedStationType.UNKNOWN -> StationType.UNKNOWN
}

internal fun StationEntity.toStation() = Station(
        code = this.code,
        countryCode = this.countryCode,
        type = this.type.toStationType(),
        names = this.names.toNameHolder(),
        synonyms = this.synonyms.toList(),
        coordinates = this.coordinates.toCoordinates(),
        tracks = this.tracks.toList(),
        isFavourite = this.isFavourite,
        lastUsed = this.lastUsed
)
