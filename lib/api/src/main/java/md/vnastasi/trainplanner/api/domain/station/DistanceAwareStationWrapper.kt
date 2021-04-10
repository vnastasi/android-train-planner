package md.vnastasi.trainplanner.api.domain.station

import kotlinx.serialization.Serializable

@Serializable
internal data class DistanceAwareStationWrapper(

        val station: StationWrapper,

        val distance: Double
)
