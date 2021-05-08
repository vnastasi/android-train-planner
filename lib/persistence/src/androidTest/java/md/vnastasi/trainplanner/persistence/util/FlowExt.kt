package md.vnastasi.trainplanner.persistence.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take

internal suspend inline fun <T> Flow<T>.expectOneElement(crossinline action: suspend (T) -> Unit) = take(1).collect(action)
