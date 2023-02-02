package md.vnastasi.trainplanner.api.client.impl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import md.vnastasi.trainplanner.api.auth.Authorization
import md.vnastasi.trainplanner.api.client.StationsApiClient
import md.vnastasi.trainplanner.api.util.WebServerExtension
import md.vnastasi.trainplanner.api.util.enqueueResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.HttpURLConnection

internal class AuthorizationHeaderTest : KoinTest {

    @Test
    @DisplayName(
            """
        Given username 'username' and password 'password'
        When calling any endpoint
        Then expect authorization header to contain correct value
    """
    )
    internal fun testAuthHeader(webServer: MockWebServer) = runTest {
        webServer.enqueueResponse {
            httpStatus = HttpURLConnection.HTTP_NOT_FOUND
        }

        get<StationsApiClient>().getStations().test {
            awaitError()
        }

        assertThat(webServer.takeRequest().getHeader("Authorization")).isEqualTo("Basic dXNlcjpwYXNzd29yZA==")
    }

    companion object {

        @Suppress("unused")
        @RegisterExtension
        @JvmField
        val extension = WebServerExtension(authorizationProvider = { Authorization("user", "password") })
    }
}
