package md.vnastasi.trainplanner.api.client.impl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import md.vnastasi.trainplanner.api.client.StationsApiClient
import md.vnastasi.trainplanner.api.util.WebServerExtension
import md.vnastasi.trainplanner.api.util.enqueueResponse
import md.vnastasi.trainplanner.exception.ApplicationException
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.HttpURLConnection

internal class ConnectivityCheckTest : KoinTest {

    @Test
    @DisplayName(
            """
        Given no connection
        When calling any endpoint
        Then expect exception to be raised with code 'MISSING_INTERNET_CONNECTION'
    """
    )
    internal fun testNoConnection(webServer: MockWebServer) = runTest {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        get<StationsApiClient>().getStations().test {
            assertThat(awaitError())
                    .isInstanceOf(ApplicationException::class)
                    .prop("code") { it.failureReason.code }.isEqualTo("MISSING_INTERNET_CONNECTION")
        }
    }

    companion object {

        @Suppress("unused")
        @RegisterExtension
        @JvmField
        val extension = WebServerExtension(connectivityChecker = { false })
    }
}
