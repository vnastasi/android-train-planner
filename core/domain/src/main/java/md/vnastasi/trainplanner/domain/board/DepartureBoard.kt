package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import md.vnastasi.trainplanner.domain.station.Station

@Parcelize
data class DepartureBoard(
        val station: Station,
        val departures: List<Departure>
) : Parcelable
