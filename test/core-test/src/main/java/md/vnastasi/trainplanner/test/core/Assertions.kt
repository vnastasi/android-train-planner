package md.vnastasi.trainplanner.test.core

import assertk.Assert
import assertk.Result
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take

fun <T: Any> Assert<Result<T?>>.hasData(): Assert<T> = isSuccess().isNotNull()

suspend fun <T> assertThatFlow(call: suspend () -> Flow<T>) = assertThat { call.invoke().take(1).firstOrNull() }
