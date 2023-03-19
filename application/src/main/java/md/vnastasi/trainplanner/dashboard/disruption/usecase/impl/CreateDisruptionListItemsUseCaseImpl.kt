package md.vnastasi.trainplanner.dashboard.disruption.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.dashboard.disruption.repository.DisruptionsRepository
import md.vnastasi.trainplanner.dashboard.disruption.ui.DisruptionListFragment
import md.vnastasi.trainplanner.dashboard.disruption.ui.DisruptionListItem
import md.vnastasi.trainplanner.dashboard.disruption.usecase.CreateDisruptionListItemsUseCase
import md.vnastasi.trainplanner.domain.disruption.Disruption
import md.vnastasi.trainplanner.domain.disruption.DisturbanceType
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.exception.FailureReason

class CreateDisruptionListItemsUseCaseImpl(
    private val disruptionsRepository: DisruptionsRepository
) : CreateDisruptionListItemsUseCase {

    override fun invoke(): Flow<AsyncResult<List<DisruptionListItem>>> = disruptionsRepository.getDisruptions()
        .map(::mapItems)
        .catch { throwable ->
            when (throwable) {
                is ApplicationException -> emit(AsyncResult.Failure(throwable))
                else -> emit(AsyncResult.Failure(ApplicationException(FailureReason.unknown(), throwable)))
            }
        }

    private fun mapItems(disruptions : List<Disruption>): AsyncResult<List<DisruptionListItem>> {
        val map = disruptions.groupBy { it.type }
        val list = buildList {
            if (!map[DisturbanceType.CALAMITY].isNullOrEmpty()) {
                add(DisruptionListItem.Heading(R.string.disruptions_ongoing_notifications))
                map[DisturbanceType.CALAMITY]!!.map { DisruptionListItem.Notification(it) }.also(::addAll)
            }
            if (!map[DisturbanceType.DISRUPTION].isNullOrEmpty()) {
                add(DisruptionListItem.Heading(R.string.disruptions_disturbances))
                map[DisturbanceType.DISRUPTION]!!.map { DisruptionListItem.Disturbance(it) }.also(::addAll)
            }
            if (!map[DisturbanceType.MAINTENANCE].isNullOrEmpty()) {
                add(DisruptionListItem.Heading(R.string.disruptions_engineering_works))
                map[DisturbanceType.MAINTENANCE]!!.map { DisruptionListItem.Maintenance(it) }.also(::addAll)
            }
        }
        return AsyncResult.Success(list)
    }
}
