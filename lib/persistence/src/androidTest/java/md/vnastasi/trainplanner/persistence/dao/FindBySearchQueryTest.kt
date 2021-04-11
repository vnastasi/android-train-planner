package md.vnastasi.trainplanner.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.hasSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import md.vnastasi.trainplanner.persistence.util.DatabaseRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class FindBySearchQueryTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @Test
    fun testEmptySearchQuery() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("").expectOneElement { list ->
            assertThat(list).hasSize(4)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("'s-Hertogenbosch", "Arnhem Centraal", "De Vink", "Amsterdam Centraal")
        }
    }

    @Test
    fun testSearchByLongName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("AAL").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    @Test
    fun testSearchByMiddleName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("c.").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    @Test
    fun testSearchByShortName() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("den").expectOneElement { list ->
            assertThat(list).hasSize(1)
            assertThat(list).extracting { it.names.longName }.contains("'s-Hertogenbosch")
        }
    }

    @Test
    fun testSearchBySynonym() = runBlocking {
        databaseRule.stationDao.findBySearchQuery("CS").expectOneElement { list ->
            assertThat(list).hasSize(2)
            assertThat(list).extracting { it.names.longName }.containsExactlyInAnyOrder("Amsterdam Centraal", "Arnhem Centraal")
        }
    }

    private suspend inline fun <T> Flow<T>.expectOneElement(crossinline action: suspend (T) -> Unit) = take(1).collect(action)
}
