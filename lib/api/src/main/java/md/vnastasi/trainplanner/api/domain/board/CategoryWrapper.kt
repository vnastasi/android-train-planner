package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryWrapper(

    val code: String,

    val name: String
)
