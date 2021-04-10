package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Serializable

@Serializable
internal data class IntermediateStationWrapper(

    val code: String,

    val name: String
)
