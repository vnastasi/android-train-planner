package md.vnastasi.trainplanner.dashboard.disruption.repository

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.domain.disruption.Disruption

interface DisruptionsRepository {

    fun getDisruptions(): Flow<List<Disruption>>
}
