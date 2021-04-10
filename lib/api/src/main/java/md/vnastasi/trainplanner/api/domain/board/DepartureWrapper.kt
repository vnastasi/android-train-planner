package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class DepartureWrapper(

        val destination: String,

        @Contextual
        val plannedDeparture: OffsetDateTime,

        @Contextual
        val actualDeparture: OffsetDateTime,

        val plannedTrack: String,

        val unit: TransportationUnitWrapper,

        val intermediateStations: List<IntermediateStationWrapper>,

        val status: DepartureStatusWrapper,

        val cancelled: Boolean,

        val messages: List<MessageWrapper>
)
