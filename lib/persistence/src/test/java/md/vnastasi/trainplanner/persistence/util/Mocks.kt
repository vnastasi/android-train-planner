package md.vnastasi.trainplanner.persistence.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.stubbing.OngoingStubbing

infix fun <T> OngoingStubbing<Flow<T>>.doReturnFlow(t: T): OngoingStubbing<Flow<T>> = thenReturn(flowOf(t))
