package md.vnastasi.trainplanner.api.client.impl

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.api.client.StationsApiClient
import md.vnastasi.trainplanner.api.util.WebServerExtension
import md.vnastasi.trainplanner.api.util.enqueueResponse
import md.vnastasi.trainplanner.domain.station.*
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.test.core.assertThatFlow
import md.vnastasi.trainplanner.test.core.hasData
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.HttpURLConnection

private const val LATITUDE = 8.9
private const val LONGITUDE = -12.34
private const val LIMIT = 4

@ExtendWith(WebServerExtension::class)
internal class GetNearbyStationsTest : KoinTest {

    @Test
    @DisplayName(
        """
        Given latitude, longitude and limit
        When calling '/api/v1/station/nearby'
        Then expect request query parameters for latitude, longitude and limit
    """
    )
    internal fun testQueryParameters(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }.isFailure()

        assertThat(webServer.takeRequest().requestUrl).isNotNull().all {
            prop("latitude") { it.queryParameter("latitude") }.isEqualTo(LATITUDE.toString())
            prop("longitude") { it.queryParameter("longitude") }.isEqualTo(LONGITUDE.toString())
            prop("limit") { it.queryParameter("limit") }.isEqualTo(LIMIT.toString())
        }
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 404 response and no response body
        When calling '/api/v1/station/nearby'
        Then expect an exception to be raised with code 'SERVER_ERROR'
    """
    )
    internal fun testServerError(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("SERVER_ERROR")
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 500 response and response body
        When calling '/api/v1/station/nearby'
        Then expect an exception to be raised with code 'NS_SERVER_ERROR'
    """
    )
    internal fun testNsServerError(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
            jsonBody = "ns_service_failure.json"
        }

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("NS_SERVER_ERROR")
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with invalid format
        When calling '/api/v1/station/nearby'
        Then expect an exception to be raised with code 'UNPARSABLE_API_RESPONSE'
    """
    )
    internal fun testUnparsableResponse(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_object.json"
        }

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("UNPARSABLE_API_RESPONSE")
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with empty list
        When calling '/api/v1/station/nearby'
        Then expect a flow with empty list
    """
    )
    internal fun testEmptyList(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_list.json"
        }

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }
            .hasData()
            .isEmpty()
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with non-empty list
        When calling '/api/v1/station/nearby'
        Then expect a flow with list of stations and distances
    """
    )
    internal fun testNonEmptyList(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "nearby_stations.json"
        }

        val expectedStation = Station(
            code = "8400530",
            names = NameHolder(shortName = "Rotterdm C", middleName = "Rotterdam C.", longName = "Rotterdam Centraal"),
            type = StationType.MAJOR_STATION,
            synonyms = listOf("Rotterdam"),
            countryCode = "NL",
            tracks = listOf("2", "3", "4", "6", "7", "8", "9", "11", "12", "13", "14", "15", "16"),
            coordinates = Coordinates(latitude = 51.9249992370605, longitude = 4.46888875961304),
            isFavourite = false,
            lastUsed = null
        )

        assertThatFlow { get<StationsApiClient>().getNearbyStations(LATITUDE, LONGITUDE, LIMIT) }
            .hasData()
            .containsExactly(DistanceAwareStation(expectedStation, 123.45))
    }
}
