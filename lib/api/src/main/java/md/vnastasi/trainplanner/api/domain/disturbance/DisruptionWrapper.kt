package md.vnastasi.trainplanner.api.domain.disturbance

import kotlinx.serialization.Serializable

@Serializable
internal data class DisruptionWrapper(

    val id: String,

    val title: String,

    val type: DisturbanceTypeWrapper,

    val intervals: List<IntervalWrapper>,

    val isActive: Boolean
)
