package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IntermediateStation(
    val code: String,
    val name: String
): Parcelable
