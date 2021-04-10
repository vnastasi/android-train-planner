package md.vnastasi.trainplanner.persistence.domain.station

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "stations")
internal data class StationEntity(

        @PrimaryKey
        @ColumnInfo(name = "code", collate = ColumnInfo.NOCASE)
        val code: String,

        @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
        val type: EmbeddedStationType,

        @Embedded
        val names: EmbeddedNameHolder,

        @ColumnInfo(name = "synonyms")
        val synonyms: List<String>,

        @ColumnInfo(name = "country_code")
        val countryCode: String,

        @Embedded
        val coordinates: EmbeddedCoordinates,

        @ColumnInfo(name = "tracks")
        val tracks: List<String>,

        @ColumnInfo(name = "is_favourite")
        val isFavourite: Boolean,

        @ColumnInfo(name = "last_used")
        val lastUsed: Instant?
)