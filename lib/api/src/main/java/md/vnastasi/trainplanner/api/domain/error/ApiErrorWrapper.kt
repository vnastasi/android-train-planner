package md.vnastasi.trainplanner.api.domain.error

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class ApiErrorWrapper(

        val requestId: String,

        @Contextual
        val timestamp: OffsetDateTime,

        val path: String,

        @SerialName("status")
        val httpsStatusCode: Int,

        val reason: ApiErrorReasonWrapper,

        val message: String?
)
