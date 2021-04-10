package md.vnastasi.trainplanner.api.domain.board

import kotlinx.serialization.Serializable

@Serializable
internal data class MessageWrapper(

    val text: String,

    val type: MessageTypeWrapper
)
