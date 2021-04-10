package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class ArrivalWrapper(

        val origin: String,

        @Contextual
        val plannedArrival: OffsetDateTime,

        @Contextual
        val actualArrival: OffsetDateTime,

        val plannedTrack: String,

        val actualTrack: String,

        val unit: TransportationUnitWrapper,

        val cancelled: Boolean,

        val messages: List<MessageWrapper>
)
