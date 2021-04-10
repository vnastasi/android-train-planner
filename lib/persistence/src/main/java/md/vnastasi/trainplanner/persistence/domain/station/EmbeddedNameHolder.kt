package md.vnastasi.trainplanner.persistence.domain.station

import androidx.room.ColumnInfo

internal data class EmbeddedNameHolder(

        @ColumnInfo(name = "short_name")
        val shortName: String,

        @ColumnInfo(name = "middle_name")
        val middleName: String,

        @ColumnInfo(name = "long_name")
        val longName: String
)
