package md.vnastasi.trainplanner.api.util

import assertk.assertThat
import kotlinx.coroutines.runBlocking

internal inline fun <T> assertThatBlocking(crossinline block: suspend () -> T) = assertThat { runBlocking { block.invoke() } }
