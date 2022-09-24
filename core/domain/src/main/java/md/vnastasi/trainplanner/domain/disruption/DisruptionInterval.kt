package md.vnastasi.trainplanner.domain.disruption

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisruptionInterval(
    val period: Period,
    val description: String,
    val cause: String,
    val extraTravelTimeAdvice: String?,
    val alternativeTransportAdvice: String?,
    val otherAdvices: List<String>
) : Parcelable
