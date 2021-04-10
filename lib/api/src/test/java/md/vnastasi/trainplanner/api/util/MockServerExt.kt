package md.vnastasi.trainplanner.api.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal fun MockWebServer.enqueueResponse(block: MockResponseDsl.() -> Unit) {
    this.enqueue(MockResponseDsl().apply(block).toMockResponse())
}

internal class MockResponseDsl(
    var httpStatus: Int = 200,
    var jsonBody: String? = null
) {

    fun toMockResponse(): MockResponse = MockResponse().apply {
        setResponseCode(httpStatus)
        jsonBody?.run { setBody(jsonStringFrom(this)) }
    }
}

private fun jsonStringFrom(fileName: String): String = JsonTestFile(fileName).bufferedReader().use { it.readText() }

private object JsonTestFile {

    operator fun invoke(fileName: String) = this.javaClass.classLoader?.getResourceAsStream(fileName) ?: throw IllegalStateException("Could not load file <$fileName>")
}
