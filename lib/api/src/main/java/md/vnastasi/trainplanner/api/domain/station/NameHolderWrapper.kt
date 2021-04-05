package md.vnastasi.trainplanner.api.domain.station

import kotlinx.serialization.Serializable

@Serializable
internal data class NameHolderWrapper(

    val shortName: String,

    val middleName: String,

    val longName: String
)
