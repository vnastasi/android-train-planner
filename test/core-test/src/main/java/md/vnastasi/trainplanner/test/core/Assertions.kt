package md.vnastasi.trainplanner.test.core

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isSuccess
import assertk.assertions.prop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

fun <T: Any> Assert<Result<T?>>.hasData(): Assert<T> = isSuccess().isNotNull()

fun <T: Any?> Assert<List<T>>.hasItemAtPosition(position: Int): Assert<T> = prop("item[$position]") { it[position] }

suspend fun <T> assertThatFlow(call: suspend () -> Flow<T>) = assertThat { call.invoke().take(1).firstOrNull() }

suspend fun <T> consumingFow(limit: Int = 1, call: suspend () -> Flow<T>) = assertThat { call.invoke().take(limit).toList() }

