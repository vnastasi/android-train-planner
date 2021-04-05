package md.vnastasi.trainplanner.domain.error

import java.time.OffsetDateTime

data class ApiError(
        val requestId: String,
        val timestamp: OffsetDateTime,
        val path: String,
        val httpsStatusCode: Int,
        val reason: ApiErrorReason,
        val message: String?
)
