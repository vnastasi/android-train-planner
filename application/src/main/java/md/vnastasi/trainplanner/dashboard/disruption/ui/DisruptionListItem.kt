package md.vnastasi.trainplanner.dashboard.disruption.ui

import androidx.annotation.StringRes
import md.vnastasi.trainplanner.domain.disruption.Disruption

sealed class DisruptionListItem {

    data class Heading(@StringRes val textResId: Int) : DisruptionListItem()

    data class Notification(val value: Disruption) : DisruptionListItem()

    data class Maintenance(val value: Disruption) : DisruptionListItem()

    data class Disturbance(val value: Disruption) : DisruptionListItem()
}
