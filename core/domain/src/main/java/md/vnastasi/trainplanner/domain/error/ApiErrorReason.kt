package md.vnastasi.trainplanner.domain.error

enum class ApiErrorReason {

    UNKNOWN_FAILURE,
    UNPARSABLE_RESPONSE,
    NS_SERVICE_FAILURE,
    UNKNOWN_STATION,
    NO_DEPARTURES_FOR_STATION,
    NO_ARRIVALS_FOR_STATION
}
