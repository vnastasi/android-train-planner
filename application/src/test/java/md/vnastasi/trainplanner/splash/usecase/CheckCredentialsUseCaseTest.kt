package md.vnastasi.trainplanner.splash.usecase

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository
import org.junit.jupiter.api.Test

internal class CheckCredentialsUseCaseTest {

    private val mockCredentialsStorageRepository = mock<CredentialsStorageRepository>()

    private val useCase = CheckCredentialsUseCase(mockCredentialsStorageRepository)

    @Test
    internal fun testCredentialsStored() = runBlockingTest  {
        whenever(mockCredentialsStorageRepository.isStored()).doReturn(true)

        assertThat(useCase.execute()).isTrue()
    }

    @Test
    internal fun testCredentialsNotStored() = runBlockingTest  {
        whenever(mockCredentialsStorageRepository.isStored()).doReturn(false)

        assertThat(useCase.execute()).isFalse()
    }
}
