package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Serializable

@Serializable
internal data class TransportationUnitWrapper(

        val number: String,

        val operator: String,

        val category: CategoryWrapper,

        val type: TransportationTypeWrapper
)
