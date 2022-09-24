package md.vnastasi.trainplanner.api.domain.disturbance

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class PeriodWrapper(

    @Contextual
    val start: OffsetDateTime,

    @Contextual
    val end: OffsetDateTime
)
