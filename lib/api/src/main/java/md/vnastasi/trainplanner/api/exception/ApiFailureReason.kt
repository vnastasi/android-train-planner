package md.vnastasi.trainplanner.api.exception

import md.vnastasi.trainplanner.exception.FailureReason

sealed class ApiFailureReason(
        override val code: String,
        override val message: String
) : FailureReason {

    object MissingInternetConnection : ApiFailureReason("MISSING_INTERNET_CONNECTION", "No internet connection")

    object EmptyResponse : ApiFailureReason("EMPTY_API_RESPONSE", "API response was empty")

    object UnparsableResponse : ApiFailureReason("UNPARSABLE_API_RESPONSE", "Failed to parse API response")

    object NsFailure : ApiFailureReason("NS_SERVER_ERROR", "Failed to connect to NS services")

    class UnknownStation(stationCode: String): ApiFailureReason("UNKNOWN_STATION", "Unknown station with code <$stationCode>")

    class NoArrivals(stationCode: String): ApiFailureReason("NO_ARRIVALS_AVAILABLE", "Arrival timetable unavailable for station with code <$stationCode>")

    class NoDepartures(stationCode: String): ApiFailureReason("NO_DEPARTURES_AVAILABLE", "Departure timetable unavailable for station with code <$stationCode>")

    object Unknown : ApiFailureReason("UNKNOWN_SERVER_ERROR", "Something went wrong while connecting to the server")
}
