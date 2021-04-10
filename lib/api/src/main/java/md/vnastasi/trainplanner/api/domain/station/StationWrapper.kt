package md.vnastasi.trainplanner.api.domain.station

import kotlinx.serialization.Serializable

@Serializable
internal data class StationWrapper(

        val code: String,

        val names: NameHolderWrapper,

        val type: StationTypeWrapper,

        val synonyms: List<String>,

        val countryCode: String,

        val tracks: List<String>,

        val coordinates: CoordinatesWrapper
)
