package md.vnastasi.trainplanner.api.client.impl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.api.client.DisruptionsApiClient
import md.vnastasi.trainplanner.api.util.WebServerExtension
import md.vnastasi.trainplanner.api.util.enqueueResponse
import md.vnastasi.trainplanner.domain.disruption.Disruption
import md.vnastasi.trainplanner.domain.disruption.DisruptionInterval
import md.vnastasi.trainplanner.domain.disruption.DisturbanceType
import md.vnastasi.trainplanner.domain.disruption.Period
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.ext.toOffsetDateTime
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

@ExtendWith(WebServerExtension::class)
internal class GetDisruptionsTest : KoinTest {

    @Test
    @DisplayName("""
        When calling '/api/v1/disruption'
        Then expect query parameter 'types' with values 'DISRUPTION' and 'MAINTENANCE'
    """)
    internal fun testQueryParameter(webServer: MockWebServer): Unit = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpsURLConnection.HTTP_INTERNAL_ERROR
        }

        get<DisruptionsApiClient>().getDisruptions().test {
            awaitError()
        }

        assertThat(webServer.takeRequest().requestUrl?.queryParameterValues("types"))
            .isNotNull()
            .containsAll("MAINTENANCE", "DISRUPTION")
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 500 response and response body
        When calling '/api/v1/disruption'
        Then expect an exception to be raised with code 'NS_SERVER_ERROR'
    """
    )
    internal fun testNsServerError(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR
            jsonBody = "ns_service_failure.json"
        }

        get<DisruptionsApiClient>().getDisruptions().test {
            assertThat(awaitError())
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("NS_SERVER_ERROR")
        }
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 404 response and no response body
        When calling '/api/v1/disruption'
        Then expect an exception to be raised with code 'SERVER_ERROR'
    """
    )
    internal fun testServerError(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        get<DisruptionsApiClient>().getDisruptions().test {
            assertThat(awaitError())
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("SERVER_ERROR")

        }
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with invalid format
        When calling '/api/v1/disruption'
        Then expect an exception to be raised with code 'UNPARSABLE_API_RESPONSE'
    """
    )
    internal fun testUnparsableResponse(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_object.json"
        }

        get<DisruptionsApiClient>().getDisruptions().test {
            assertThat(awaitError())
                .isInstanceOf(ApplicationException::class)
                .prop("code") { it.failureReason.code }.isEqualTo("UNPARSABLE_API_RESPONSE")

        }
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with empty list
        When calling '/api/v1/disruption'
        Then expect a flow with empty list
    """
    )
    internal fun testEmptyList(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "empty_list.json"
        }

        get<DisruptionsApiClient>().getDisruptions().test {
            assertThat(awaitItem()).isNotNull().isEmpty()
            awaitComplete()
        }
    }

    @Test
    @DisplayName(
        """
        Given a HTTP 200 response and response body with empty list
        When calling '/api/v1/disruption'
        Then expect a flow with disruption list
    """
    )
    internal fun testNonEmptyList(webServer: MockWebServer) = runBlocking {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_OK
            jsonBody = "disruptions.json"
        }

        val expectedDisruption = Disruption(
            id = "6018812",
            title = "Almere-Zwolle",
            type = DisturbanceType.DISRUPTION,
            intervals = listOf(
                DisruptionInterval(
                    period = Period(
                        start = "2022-09-19T08:14Z".toOffsetDateTime(),
                        end = "2022-12-15T03:00Z".toOffsetDateTime()
                    ),
                    cause = "Defecte bovenleiding",
                    description = "Tussen Lelystad Centrum en Dronten rijden er geen treinen door een defecte bovenleiding.",
                    alternativeTransportAdvice = "Er rijden bussen.",
                    otherAdvices = listOf("Maak gebruik van de omreisroutes. Plan uw reis kort voor vertrek."),
                    extraTravelTimeAdvice = null
                )
            ),
            isActive = true
        )

        get<DisruptionsApiClient>().getDisruptions().test {
            assertThat(awaitItem()).isNotNull().contains(expectedDisruption)
            awaitComplete()
        }
    }
}
