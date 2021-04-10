package md.vnastasi.trainplanner.api.domain.station

import kotlinx.serialization.Serializable

@Serializable
internal data class CoordinatesWrapper(

    val latitude: Double,

    val longitude: Double
)
