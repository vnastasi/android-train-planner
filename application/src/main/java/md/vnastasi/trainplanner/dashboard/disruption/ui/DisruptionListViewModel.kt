package md.vnastasi.trainplanner.dashboard.disruption.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onErrorResume
import kotlinx.coroutines.launch
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.dashboard.disruption.usecase.CreateDisruptionListItemsUseCase

class DisruptionListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val createDisruptionListItems: CreateDisruptionListItemsUseCase
) : ViewModel() {

    val viewState: Flow<AsyncResult<List<DisruptionListItem>>> = savedStateHandle.getStateFlow("view_state", AsyncResult.Loading)

    init {
        createListItems()
    }

    private fun createListItems() {
        viewModelScope.launch(DispatcherRegistry.Main) {
            createDisruptionListItems().flowOn(DispatcherRegistry.Default).collect { savedStateHandle["view_state"] = it }
        }
    }

    class Provider(
        private val createDisruptionListItemsUseCase: CreateDisruptionListItemsUseCase
    ) {

        fun provideFor(savedStateHandle: SavedStateHandle) : DisruptionListViewModel = DisruptionListViewModel(savedStateHandle, createDisruptionListItemsUseCase)
    }
}
