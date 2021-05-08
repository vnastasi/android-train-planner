package md.vnastasi.trainplanner.persistence.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import md.vnastasi.trainplanner.persistence.domain.station.StationEntity

@Dao
internal interface StationDao {

    @Query("""
        SELECT * 
        FROM stations 
        WHERE short_name LIKE '%'||:query||'%' 
            OR long_name LIKE '%'||:query||'%' 
            OR middle_name LIKE '%'||:query||'%' 
            OR synonyms LIKE '%'||:query||'%'
    """)
    fun findBySearchQuery(query: String): Flow<List<StationEntity>>

    @Query("""
        SELECT * 
        FROM stations 
        WHERE is_favourite = 1 
        LIMIT :limit
    """)
    fun findFavourites(limit: Int): Flow<List<StationEntity>>

    @Query("""
        SELECT * 
        FROM stations 
        WHERE last_used IS NOT NULL 
            AND code NOT IN (
                SELECT code 
                FROM stations 
                WHERE is_favourite = 1 
                ORDER BY last_used DESC 
                LIMIT :limit
            ) 
        ORDER BY last_used DESC
        LIMIT :limit
    """)
    fun findRecent(limit: Int): Flow<List<StationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: StationEntity)

    @Update
    suspend fun update(station: StationEntity)

    @Query("""
        DELETE FROM stations
    """)
    suspend fun deleteAll()
}
