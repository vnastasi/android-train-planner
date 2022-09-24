package md.vnastasi.trainplanner.api.client.impl

import md.vnastasi.trainplanner.api.domain.board.*
import md.vnastasi.trainplanner.api.domain.board.CategoryWrapper
import md.vnastasi.trainplanner.api.domain.board.DepartureStatusWrapper
import md.vnastasi.trainplanner.api.domain.board.IntermediateStationWrapper
import md.vnastasi.trainplanner.api.domain.board.MessageTypeWrapper
import md.vnastasi.trainplanner.api.domain.board.MessageWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.DisruptionWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.DisturbanceTypeWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.IntervalWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.PeriodWrapper
import md.vnastasi.trainplanner.api.domain.station.CoordinatesWrapper
import md.vnastasi.trainplanner.api.domain.station.DistanceAwareStationWrapper
import md.vnastasi.trainplanner.api.domain.station.NameHolderWrapper
import md.vnastasi.trainplanner.api.domain.station.StationTypeWrapper
import md.vnastasi.trainplanner.api.domain.station.StationWrapper
import md.vnastasi.trainplanner.domain.board.*
import md.vnastasi.trainplanner.domain.disruption.Disruption
import md.vnastasi.trainplanner.domain.disruption.DisruptionInterval
import md.vnastasi.trainplanner.domain.disruption.DisturbanceType
import md.vnastasi.trainplanner.domain.disruption.Period
import md.vnastasi.trainplanner.domain.station.*

internal fun CoordinatesWrapper.toCoordinates() = Coordinates(
        latitude = latitude,
        longitude = longitude
)

internal fun NameHolderWrapper.toNameHolder() = NameHolder(
        shortName = shortName,
        middleName = middleName,
        longName = longName
)

internal fun StationTypeWrapper.toStationType() = when (this) {
    StationTypeWrapper.INTERCITY_JUNCTION_STATION -> StationType.INTERCITY_JUNCTION_STATION
    StationTypeWrapper.INTERCITY_STATION -> StationType.INTERCITY_STATION
    StationTypeWrapper.MAJOR_STATION -> StationType.MAJOR_STATION
    StationTypeWrapper.OPTIONAL_STATION -> StationType.OPTIONAL_STATION
    StationTypeWrapper.STOP_TRAIN_JUNCTION_STATION -> StationType.STOP_TRAIN_JUNCTION_STATION
    StationTypeWrapper.STOP_TRAIN_STATION -> StationType.STOP_TRAIN_STATION
    StationTypeWrapper.UNKNOWN -> StationType.UNKNOWN
}

internal fun StationWrapper.toStation() = Station(
        code = code,
        countryCode = countryCode,
        coordinates = coordinates.toCoordinates(),
        names = names.toNameHolder(),
        synonyms = synonyms.toList(),
        tracks = tracks.toList(),
        type = type.toStationType(),
        lastUsed = null,
        isFavourite = false
)

internal fun DistanceAwareStationWrapper.toDistanceAwareStation() = DistanceAwareStation(
        station = station.toStation(),
        distance = distance
)

internal fun CategoryWrapper.toCategory() = Category(
        code = code,
        name = name
)

internal fun DepartureStatusWrapper.toDepartureStatus() = when (this) {
    DepartureStatusWrapper.BOARDING -> DepartureStatus.BOARDING
    DepartureStatusWrapper.DEPARTED -> DepartureStatus.DEPARTED
    DepartureStatusWrapper.EXPECTED -> DepartureStatus.EXPECTED
}

internal fun IntermediateStationWrapper.toIntermediateStation() = IntermediateStation(
        code = code,
        name = name
)

internal fun MessageTypeWrapper.toMessageType() = when (this) {
    MessageTypeWrapper.INFO -> MessageType.INFO
    MessageTypeWrapper.WARNING -> MessageType.WARNING
}

internal fun MessageWrapper.toMessage() = Message(
        text = text,
        type = type.toMessageType()
)

internal fun TransportationTypeWrapper.toTransportationType() = when (this) {
    TransportationTypeWrapper.BIKE -> TransportationType.BIKE
    TransportationTypeWrapper.BUS -> TransportationType.BUS
    TransportationTypeWrapper.CAR -> TransportationType.CAR
    TransportationTypeWrapper.FERRY -> TransportationType.FERRY
    TransportationTypeWrapper.METRO -> TransportationType.METRO
    TransportationTypeWrapper.SUBWAY -> TransportationType.SUBWAY
    TransportationTypeWrapper.TAXI -> TransportationType.TAXI
    TransportationTypeWrapper.TRAIN -> TransportationType.TRAIN
    TransportationTypeWrapper.TRAM -> TransportationType.TRAM
    TransportationTypeWrapper.UNKNOWN -> TransportationType.UNKNOWN
    TransportationTypeWrapper.WALK -> TransportationType.WALK
}

internal fun TransportationUnitWrapper.toTransportationUnit() = TransportationUnit(
        number = number,
        operator = operator,
        type = type.toTransportationType(),
        category = category.toCategory()
)

internal fun DepartureWrapper.toDeparture() = Departure(
        destination = destination,
        plannedDeparture = plannedDeparture,
        actualDeparture = actualDeparture,
        plannedTrack = plannedTrack,
        intermediateStations = intermediateStations.map { it.toIntermediateStation() },
        isCancelled = cancelled,
        messages = messages.map { it.toMessage() },
        status = status.toDepartureStatus(),
        unit = unit.toTransportationUnit()
)

internal fun ArrivalWrapper.toArrival() = Arrival(
        origin =  origin,
        plannedArrival = plannedArrival,
        actualArrival = actualArrival,
        plannedTrack = plannedTrack,
        actualTrack = actualTrack,
        unit = unit.toTransportationUnit(),
        isCancelled = cancelled,
        messages = messages.map { it.toMessage() }
)

internal fun DisruptionWrapper.toDisruption() = Disruption(
    id = this.id,
    type = this.type.toDisturbanceType(),
    title = this.title,
    intervals = this.intervals.map(IntervalWrapper::toDisruptionInterval),
    isActive = this.isActive
)

internal fun DisturbanceTypeWrapper.toDisturbanceType() = when (this) {
    DisturbanceTypeWrapper.MAINTENANCE -> DisturbanceType.MAINTENANCE
    DisturbanceTypeWrapper.DISRUPTION -> DisturbanceType.DISRUPTION
    DisturbanceTypeWrapper.CALAMITY -> DisturbanceType.CALAMITY
}

internal fun IntervalWrapper.toDisruptionInterval() = DisruptionInterval(
    period = this.period.toPeriod(),
    description = this.description,
    cause = this.cause,
    extraTravelTimeAdvice = this.extraTravelTimeAdvice,
    alternativeTransportAdvice = this.alternativeTransportAdvice,
    otherAdvices = this.otherAdvices.toList()
)

internal fun PeriodWrapper.toPeriod() = Period(
    start = this.start,
    end = this.end
)
