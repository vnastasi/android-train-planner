package md.vnastasi.trainplanner.async

import java.util.concurrent.atomic.AtomicBoolean

data class Event<out T : Any>(
    private val content: T
) {

    private val lock = AtomicBoolean(false)

    fun consume(): T? =
        if (lock.compareAndSet(false, true)) {
            content
        } else {
            null
        }

    fun peek(): T = content
}
