package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import md.vnastasi.trainplanner.domain.station.Station

@Parcelize
data class ArrivalBoard(
        val station: Station,
        val arrivals: List<Arrival>
) : Parcelable
