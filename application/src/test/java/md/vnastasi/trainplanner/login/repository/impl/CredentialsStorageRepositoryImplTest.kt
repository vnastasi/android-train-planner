package md.vnastasi.trainplanner.login.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isSuccess
import assertk.assertions.isTrue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository.Companion.KEY_CREDENTIALS
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CredentialsStorageRepositoryImplTest {

    private val mockDataStore = mock<DataStore<Preferences>>()

    private val repository = CredentialsStorageRepositoryImpl(mockDataStore)

    @Test
    @DisplayName(
        """
        Given preference pair with key 'credentials' exists
        When checking if credentials are stored
        Then expect true
    """
    )
    internal fun testCredentialsStored() = runBlockingTest {
        val preferencePair = stringPreferencesKey(KEY_CREDENTIALS) to "qwerty"
        whenever(mockDataStore.data).doReturn(flowOf(preferencesOf(preferencePair)))

        assertThat { repository.isStored() }
            .isSuccess()
            .isTrue()
    }

    @Test
    @DisplayName(
        """
        Given preference pair with key 'credentials' does not exists
        When checking if credentials are stored
        Then expect false
    """
    )
    internal fun testCredentialsNotStored() = runBlockingTest {
        whenever(mockDataStore.data).doReturn(flowOf(emptyPreferences()))

        assertThat { repository.isStored() }
            .isSuccess()
            .isFalse()
    }
}