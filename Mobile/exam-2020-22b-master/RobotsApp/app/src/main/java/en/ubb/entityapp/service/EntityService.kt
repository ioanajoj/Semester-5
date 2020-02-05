package en.ubb.entityapp.service

import androidx.room.Update
import en.ubb.entityapp.domain.*
import retrofit2.http.*
import java.util.*

interface EntityService {

    @GET("/types")
    suspend fun getTypes(): List<String>

    @GET("/robots/{type}")
    suspend fun getRobots(@Path("type") path: String): List<Robot>

    @POST("/robot")
    suspend fun addRobot(@Body e: Robot): Robot

    @DELETE("/book/{id}/")
    suspend fun deleteEntity(@Path("id") id: Int)

    @POST("/height")
    suspend fun updateRobot(@Body requestBody: UpdateRobotRequestObject)

    @GET("/old")
    suspend fun getOldRobots(): List<Robot>

    @POST("/age")
    suspend fun updateRobotAge(@Body requestBody: UpdateRobotAgeRO)

    companion object {
        const val SERVICE_ENDPOINT = "http://192.168.3.137:2202"
    }

}