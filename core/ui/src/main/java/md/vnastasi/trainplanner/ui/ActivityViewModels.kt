package md.vnastasi.trainplanner.ui

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy

inline fun <reified VM : ViewModel> FragmentActivity.providingViewModels(
    crossinline provider: (SavedStateHandle) -> VM
): Lazy<VM> = ViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { this.viewModelStore },
    factoryProducer = {
        object : AbstractSavedStateViewModelFactory(this, intent?.extras) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = provider.invoke(handle) as T
        }
    }
)
