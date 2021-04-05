package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Parcelize
data class Departure(
        val destination: String,
        val plannedDeparture: OffsetDateTime,
        val actualDeparture: OffsetDateTime,
        val plannedTrack: String,
        val unit: TransportationUnit,
        val intermediateStations: List<IntermediateStation>,
        val status: DepartureStatus,
        val isCancelled: Boolean,
        val messages: List<Message>
) : Parcelable {

    val delay: Long
        get() = plannedDeparture.until(actualDeparture, ChronoUnit.MINUTES)

    fun isModified() = plannedDeparture != actualDeparture
}
