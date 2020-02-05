package en.ubb.examapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import en.ubb.examapp.domain.Entity
import en.ubb.examapp.domain.Order

@Dao
interface EntityDao {

    @get:Query("select * from orders")
    val orders: LiveData<MutableList<Order>>

    @get:Query("select count(*) from orders")
    val numberOfEntities: Int

    @Insert
    fun addEntity(entity: Order)

    @Insert
    fun addEntities(entities: List<Entity>)

    @Delete
    fun deleteEntity(entity: Entity)

    @Query("delete from orders")
    fun deleteEntities()

    @Query("delete from orders where type = :type")
    fun deleteOrders(type: String)

//    @Query("delete from orders where type = 'ready'")
//    fun deleteReadyOrders()
//
//    @Query("delete from orders where type = 'recorded'")
//    fun deleteRecordedOrders()

    @Insert
    fun addOrders(entities: List<Order>)

    @Insert
    fun addOrder(order: Order)

    @Query("select * from orders where id = 0")
    fun getOfflineOrders() : List<Order>
}