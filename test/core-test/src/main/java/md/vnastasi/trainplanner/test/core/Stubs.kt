package md.vnastasi.trainplanner.test.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.stubbing.OngoingStubbing

infix fun <T> OngoingStubbing<Flow<T>>.doReturnFlowOf(t: T): OngoingStubbing<Flow<T>> = thenReturn(flowOf(t))
