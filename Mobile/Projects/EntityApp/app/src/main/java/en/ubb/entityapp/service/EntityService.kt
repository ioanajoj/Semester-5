package en.ubb.entityapp.service

import en.ubb.entityapp.domain.Entity
import en.ubb.entityapp.domain.ServerResponse
import retrofit2.http.*

interface EntityService {

    @GET("/books")
    suspend fun getEntities(): List<Entity>

    @POST("/book")
    suspend fun addEntity(@Body e: Entity): Entity

    @DELETE("/book/{id}/")
    suspend fun deleteEntity(@Path("id") id: Int)

    companion object {
        const val SERVICE_ENDPOINT = "http://192.168.3.137:3000"
    }

}