package md.vnastasi.trainplanner.ui

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

inline fun <reified VM : ViewModel> FragmentActivity.providingViewModels(
    crossinline provider: (SavedStateHandle) -> VM
): Lazy<VM> {
    val factory = object : AbstractSavedStateViewModelFactory(this, intent?.extras) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = provider.invoke(handle) as T
    }

    return viewModels { factory }
}
