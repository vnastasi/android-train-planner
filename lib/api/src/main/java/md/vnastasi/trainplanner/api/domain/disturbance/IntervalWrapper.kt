package md.vnastasi.trainplanner.api.domain.disturbance

import kotlinx.serialization.Serializable

@Serializable
internal data class IntervalWrapper(

    val period: PeriodWrapper,

    val description: String,

    val cause: String,

    val extraTravelTimeAdvice: String?,

    val alternativeTransportAdvice: String?,

    val otherAdvices: List<String>
)
