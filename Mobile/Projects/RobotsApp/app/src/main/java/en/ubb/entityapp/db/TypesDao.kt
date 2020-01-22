package en.ubb.entityapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import en.ubb.entityapp.domain.Type

@Dao
interface TypesDao {

    @get:Query("select * from types")
    val entities: LiveData<MutableList<Type>>

    @get:Query("select count(*) from types")
    val numberOfEntities: Int

    @Insert
    fun addType(type: Type)

    @Insert
    fun addTypes(entities: List<Type>)

    @Delete
    fun deleteType(entity: Type)

    @Query("delete from types")
    fun deleteTypes()
}