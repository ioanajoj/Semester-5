package en.ubb.examapp.service

import androidx.room.Update
import en.ubb.examapp.domain.Entity
import en.ubb.examapp.domain.Order
import retrofit2.http.*

interface EntityService {

    @GET("/books")
    suspend fun getEntities(): List<Entity>

    @POST("/book")
    suspend fun addEntity(@Body e: Entity): Entity

    @DELETE("/book/{id}/")
    suspend fun deleteEntity(@Path("id") id: Int)

    companion object {
        const val SERVICE_ENDPOINT = "http://192.168.43.230:2301"
    }

    @GET("/orders")
    suspend fun getOrders(): List<Order>

    @GET("/recorded")
    suspend fun getRecordedOrders(): List<Order>

    @POST("/order")
    suspend fun addOrder(@Body order: Order): Order

    @GET("/order/{id}")
    suspend fun getOrderDetails(@Path("id") id: Int): Order

    @POST("/status")
    suspend fun updateOrderStatus(@Body obj: UpdateOrderRequestObject): Order

    @GET("/my/{table}")
    suspend fun getTableOrderDetails(@Path("table") table: String): Order

}