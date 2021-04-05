package md.vnastasi.trainplanner.domain.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransportationUnit(
        val number: String,
        val operator: String,
        val category: Category,
        val type: TransportationType
): Parcelable
