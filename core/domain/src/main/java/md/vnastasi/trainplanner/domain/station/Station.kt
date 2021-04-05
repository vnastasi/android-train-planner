package md.vnastasi.trainplanner.domain.station

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class Station(
        val code: String,
        val names: NameHolder,
        val type: StationType,
        val synonyms: List<String>,
        val countryCode: String,
        val tracks: List<String>,
        val coordinates: Coordinates,
        val isFavourite: Boolean,
        val lastUsed: Instant?
): Parcelable
