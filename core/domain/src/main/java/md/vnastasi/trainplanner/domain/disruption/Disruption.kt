package md.vnastasi.trainplanner.domain.disruption

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disruption(
    val id: String,
    val title: String,
    val type: DisturbanceType,
    val intervals: List<DisruptionInterval>,
    val isActive: Boolean
) : Parcelable
