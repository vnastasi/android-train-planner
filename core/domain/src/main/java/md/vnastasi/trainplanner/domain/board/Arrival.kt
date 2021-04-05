package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Parcelize
data class Arrival(
        val origin: String,
        val plannedArrival: OffsetDateTime,
        val actualArrival: OffsetDateTime,
        val plannedTrack: String,
        val actualTrack: String,
        val unit: TransportationUnit,
        val isCancelled: Boolean,
        val messages: List<Message>
) : Parcelable {

    val delay: Long
        get() = plannedArrival.until(actualArrival, ChronoUnit.MINUTES)

    fun isModifiedTrack() = plannedTrack != actualTrack

    fun isModifiedArrival() = plannedArrival != actualArrival

    fun isModified() = isModifiedArrival() || isModifiedTrack()
}
