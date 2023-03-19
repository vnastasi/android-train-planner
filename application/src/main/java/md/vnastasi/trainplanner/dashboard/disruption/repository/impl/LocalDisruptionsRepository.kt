package md.vnastasi.trainplanner.dashboard.disruption.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import md.vnastasi.trainplanner.dashboard.disruption.repository.DisruptionsRepository
import md.vnastasi.trainplanner.domain.disruption.Disruption
import md.vnastasi.trainplanner.domain.disruption.DisturbanceType

class LocalDisruptionsRepository : DisruptionsRepository {

    override fun getDisruptions(): Flow<List<Disruption>> = flowOf(
        listOf(
            Disruption(id = "1", title = "Notification 1", type = DisturbanceType.CALAMITY, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Disturbance 1", type = DisturbanceType.DISRUPTION, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Notification 1", type = DisturbanceType.CALAMITY, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Notification 1", type = DisturbanceType.CALAMITY, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Disturbance 1", type = DisturbanceType.DISRUPTION, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Notification 1", type = DisturbanceType.CALAMITY, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Disturbance 1", type = DisturbanceType.DISRUPTION, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Disturbance 1", type = DisturbanceType.DISRUPTION, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Maintenance 1", type = DisturbanceType.MAINTENANCE, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Disturbance 1", type = DisturbanceType.DISRUPTION, intervals = emptyList(), isActive = true),
            Disruption(id = "1", title = "Notification 1", type = DisturbanceType.CALAMITY, intervals = emptyList(), isActive = true),
        )
    )
}
