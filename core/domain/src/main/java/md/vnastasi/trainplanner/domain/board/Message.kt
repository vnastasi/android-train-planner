package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val text: String,
    val type: MessageType
): Parcelable
