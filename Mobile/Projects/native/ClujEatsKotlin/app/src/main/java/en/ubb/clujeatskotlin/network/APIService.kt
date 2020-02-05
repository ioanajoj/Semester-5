package en.ubb.clujeatskotlin.network

import en.ubb.clujeatskotlin.db.Place
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIService {

    companion object {
        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://192.168.43.230:8000/")
                .build()
            return retrofit.create(APIService::class.java)
        }
    }

    @GET("places/")
    fun getAllPlaces() : Observable<List<Place>>

    @POST("places/new/")
    @Headers(
        "Content-Type: application/json"
    )
    fun addPlace(@Body place: Place) : Call<ResponseData>

    @DELETE("places/{id}/")
    fun deletePlace(@Path("id") id: Long) : Call<ResponseData>

    @PUT("places/{id}/")
    fun updatePlace(@Path("id") id: Long, @Body place: Place) : Call<ResponseData>
}