package md.vnastasi.trainplanner.api.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.domain.disruption.Disruption

interface DisruptionsApiClient {

    fun getDisruptions(): Flow<List<Disruption>>
}