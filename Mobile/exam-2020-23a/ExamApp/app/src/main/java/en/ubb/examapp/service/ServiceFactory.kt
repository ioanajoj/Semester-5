package en.ubb.examapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    fun <T> createRetrofitService(clazz: Class<T>, endpoint: String) : T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(endpoint)
            .build()
        return retrofit.create(clazz)
    }
}