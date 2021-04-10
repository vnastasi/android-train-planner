package md.vnastasi.trainplanner.api.client.impl

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.flow.toCollection
import md.vnastasi.trainplanner.api.client.TimetableApiClient
import md.vnastasi.trainplanner.api.util.WebServerExtension
import md.vnastasi.trainplanner.api.util.assertThatBlocking
import md.vnastasi.trainplanner.api.util.enqueueResponse
import md.vnastasi.trainplanner.domain.board.*
import md.vnastasi.trainplanner.domain.station.Station
import md.vnastasi.trainplanner.exception.ApplicationException
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.HttpURLConnection
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val STATION_CODE = "123456"

@ExtendWith(WebServerExtension::class)
internal class GetDeparturesTest : KoinTest {

    @Test
    @DisplayName("""
        Given a station code
        When calling '/api/v1/station/{code}/departures'
        Then expect 4th path segment to contain station code
    """)
    internal fun testPathSegment(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }.isFailure()

        assertThat(webServer.takeRequest().requestUrl?.pathSegments)
                .isNotNull()
                .all {
                    hasSize(5)
                    prop("4th segment") { it[3] }.isEqualTo(STATION_CODE)
                }
    }

    @Test
    @DisplayName("""
        Given a HTTP 500 response and response body
        When calling '/api/v1/station/{code}/departures'
        Then expect an exception to be raised with code 'NS_SERVER_ERROR'
    """)
    internal fun testNsServerError(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR
            jsonBody = "ns_service_failure.json"
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("NS_SERVER_ERROR")
    }

    @Test
    @DisplayName("""
        Given a HTTP 500 response and response body with reason 'UNKNOWN_STATION' 
        When calling '/api/v1/station/{code}/departures'
        Then expect an exception to be raised with code 'UNKNOWN_STATION'
    """)
    internal fun testUnknownStation(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR
            jsonBody = "station_not_found.json"
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("UNKNOWN_STATION")
    }

    @Test
    @DisplayName("""
        Given a HTTP 500 response and response body with reason 'NO_DEPARTURES_FOR_STATION' 
        When calling '/api/v1/station/{code}/departures'
        Then expect an exception to be raised with code 'NO_DEPARTURES_AVAILABLE'
    """)
    internal fun testNoDepartures(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR
            jsonBody = "no_departures.json"
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("NO_DEPARTURES_AVAILABLE")
    }

    @Test
    @DisplayName("""
        Given a HTTP 404 response and no response body
        When calling '/api/v1/station/{code}/departures'
        Then expect an exception to be raised with code 'SERVER_ERROR'
    """)
    internal fun testServerError(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("SERVER_ERROR")
    }

    @Test
    @DisplayName("""
        Given a HTTP 200 response and response body with invalid format
        When calling '/api/v1/station/{code}/departures'
        Then expect an exception to be raised with code 'UNPARSABLE_API_RESPONSE'
    """)
    internal fun testUnparsableResponse(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_object.json"
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("UNPARSABLE_API_RESPONSE")
    }

    @Test
    @DisplayName("""
        Given a HTTP 200 response and response body with empty list
        When calling '/api/v1/station/{code}/departures'
        Then expect a flow with empty list
    """)
    internal fun testEmptyList(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_list.json"
        }

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isSuccess()
                .all {
                    hasSize(1)
                    contains(emptyList<Station>())
                }
    }

    @Test
    @DisplayName("""
        Given a HTTP 200 response and response body with non-empty list
        When calling '/api/v1/station/{code}/departures'
        Then expect a flow with departure list
    """)
    internal fun testNonEmptyList(webServer: MockWebServer) {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "departures.json"
        }

        val expectedDeparture = Departure(
                destination = "Utrecht Centraal",
                plannedDeparture = OffsetDateTime.parse("2020-01-10T20:57Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")),
                actualDeparture = OffsetDateTime.parse("2020-01-10T20:59Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")),
                plannedTrack = "9",
                unit = TransportationUnit(number = "NS 3774", operator = "NS", category = Category(code = "IC", name = "Intercity"), type = TransportationType.TRAIN),
                intermediateStations = listOf(
                        IntermediateStation(code = "8400170", name = "Delft"),
                        IntermediateStation(code = "8400280", name = "Den Haag HS"),
                        IntermediateStation(code = "8400390", name = "Leiden C."),
                        IntermediateStation(code = "8400561", name = "Schiphol Airport")
                ),
                status = DepartureStatus.EXPECTED,
                isCancelled = false,
                messages = listOf(
                        Message(text = "Also stopping in Schiedam C., Laan v NOI, Bijlmer ArenA", type = MessageType.INFO)
                )
        )

        assertThatBlocking { get<TimetableApiClient>().getDepartures(STATION_CODE).toCollection(mutableListOf()) }
                .isSuccess()
                .all {
                    hasSize(1)
                    prop("element") { it[0] }.contains(expectedDeparture)
                }
    }
}
