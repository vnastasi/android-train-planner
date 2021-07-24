package md.vnastasi.trainplanner.ui

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

@MainThread
inline fun <reified VM: ViewModel> Fragment.providingActivityViewModels(
    crossinline provider: (SavedStateHandle) -> VM
): Lazy<VM> {
    val factory = object : AbstractSavedStateViewModelFactory(requireActivity(), requireActivity().intent?.extras + arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = provider.invoke(handle) as T
    }

    return activityViewModels { factory }
}

inline fun <reified VM : ViewModel> Fragment.providingViewModels(
    crossinline provider: (SavedStateHandle) -> VM
): Lazy<VM> {
    val factory = object : AbstractSavedStateViewModelFactory(this, arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = provider.invoke(handle) as T
    }

    return viewModels(ownerProducer = { this }, factoryProducer = { factory })
}
