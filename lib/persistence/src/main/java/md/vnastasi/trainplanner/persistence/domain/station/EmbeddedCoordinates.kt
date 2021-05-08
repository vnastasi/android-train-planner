package md.vnastasi.trainplanner.persistence.domain.station

import androidx.room.ColumnInfo

internal data class EmbeddedCoordinates(

        @ColumnInfo(name = "latitude")
        val latitude: Double,

        @ColumnInfo(name = "longitude")
        val longitude: Double
)
