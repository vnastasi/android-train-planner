package md.vnastasi.trainplanner.domain.station

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NameHolder(
    val shortName: String,
    val middleName: String,
    val longName: String
): Parcelable
