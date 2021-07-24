package md.vnastasi.trainplanner.ui

import android.os.Bundle
import androidx.core.os.bundleOf

operator fun Bundle?.plus(other: Bundle?): Bundle {
    val thisBundle = this ?: bundleOf()
    val otherBundle = other ?: bundleOf()
    return thisBundle.apply { putAll(otherBundle) }
}
