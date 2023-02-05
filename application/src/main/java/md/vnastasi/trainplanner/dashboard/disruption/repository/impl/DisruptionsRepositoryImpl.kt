package md.vnastasi.trainplanner.dashboard.disruption.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import md.vnastasi.trainplanner.api.client.DisruptionsApiClient
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.dashboard.disruption.repository.DisruptionsRepository
import md.vnastasi.trainplanner.domain.disruption.Disruption

class DisruptionsRepositoryImpl(
    private val client: DisruptionsApiClient
): DisruptionsRepository {

    override fun getDisruptions(): Flow<List<Disruption>> = client.getDisruptions().flowOn(DispatcherRegistry.IO)
}
