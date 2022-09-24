package md.vnastasi.trainplanner.domain.disruption

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Parcelize
data class Period(
    val start: OffsetDateTime,
    val end: OffsetDateTime
): Parcelable
