package en.ubb.clujeatskotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceDAO {

    @Query("SELECT * FROM place_table ORDER BY name ASC")
    fun getAlphabeticallyOrderedPlaces(): LiveData<List<Place>>

    @Query("SELECT * FROM place_table WHERE name LIKE :name")
    fun getPlace(name: String): Place

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: Place): Long

    @Query("DELETE FROM place_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(place: Place)

    @Update
    suspend fun update(place: Place)
}