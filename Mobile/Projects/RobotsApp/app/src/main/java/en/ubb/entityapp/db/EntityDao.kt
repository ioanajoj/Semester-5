package en.ubb.entityapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import en.ubb.entityapp.domain.Entity

@Dao
interface EntityDao {

    @get:Query("select * from entities")
    val entities: LiveData<MutableList<Entity>>

    @get:Query("select count(*) from entities")
    val numberOfEntities: Int

    @Insert
    fun addEntity(entity: Entity)

    @Insert
    fun addEntities(entities: List<Entity>)

    @Delete
    fun deleteEntity(entity: Entity)

    @Query("delete from entities")
    fun deleteEntities()
}