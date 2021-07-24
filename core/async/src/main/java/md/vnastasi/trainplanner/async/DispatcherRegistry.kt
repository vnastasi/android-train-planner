package md.vnastasi.trainplanner.async

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DispatcherRegistry {

    private val defaultHolder = Holder(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)

    private var holder: Holder = defaultHolder

    val Main: CoroutineDispatcher
        get() = holder.main

    val Default: CoroutineDispatcher
        get() = holder.computation

    val IO: CoroutineDispatcher
        get() = holder.io

    fun overrideMain(dispatcher: CoroutineDispatcher) {
        holder = holder.copy(main = dispatcher)
    }

    fun overrideDefault(dispatcher: CoroutineDispatcher) {
        holder = holder.copy(computation = dispatcher)
    }

    fun overrideIO(dispatcher: CoroutineDispatcher) {
        holder = holder.copy(io = dispatcher)
    }

    fun reset() {
        holder = defaultHolder
    }

    private data class Holder(
        val main: CoroutineDispatcher,
        val computation: CoroutineDispatcher,
        val io: CoroutineDispatcher
    )
}
