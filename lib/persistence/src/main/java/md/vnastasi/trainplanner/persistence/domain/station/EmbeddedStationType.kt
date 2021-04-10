package md.vnastasi.trainplanner.persistence.domain.station

internal enum class EmbeddedStationType {

    STOP_TRAIN_STATION,
    STOP_TRAIN_JUNCTION_STATION,
    INTERCITY_JUNCTION_STATION,
    INTERCITY_STATION,
    MAJOR_STATION,
    OPTIONAL_STATION,
    UNKNOWN
}
