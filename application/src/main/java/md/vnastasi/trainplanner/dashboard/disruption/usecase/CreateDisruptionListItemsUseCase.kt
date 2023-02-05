package md.vnastasi.trainplanner.dashboard.disruption.usecase

import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.dashboard.disruption.ui.DisruptionListItem

interface CreateDisruptionListItemsUseCase {

    operator fun invoke(): Flow<AsyncResult<List<DisruptionListItem>>>
}