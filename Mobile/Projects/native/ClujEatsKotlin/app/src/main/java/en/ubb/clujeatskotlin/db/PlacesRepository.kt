package en.ubb.clujeatskotlin.db

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import en.ubb.clujeatskotlin.network.APIService
import en.ubb.clujeatskotlin.network.ConnectionLiveData
import en.ubb.clujeatskotlin.network.ResponseData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlacesRepository(private val placeDAO: PlaceDAO, private val context: Context) {

    private val allPlacesDb: LiveData<List<Place>> = placeDAO.getAlphabeticallyOrderedPlaces()
    private var isConnected: Boolean = false
//    private val offlinePlaces: MutableList<Place> = mutableListOf()

    init {
        ConnectionLiveData(context).observeForever {
            isConnected = it.isConnected
            if (isConnected) {
                GlobalScope.launch {
                    updateDb()
                }
            }}
    }

    private val apiService by lazy {
        APIService.create()
    }

    private var disposable: Disposable? = null

    fun getAllPlaces(): LiveData<List<Place>> {
        if (isConnected) {
            GlobalScope.launch {
                updateDb()
            }
        }
        return allPlacesDb
    }

    private suspend fun updateDb() {
        val defaultId: Long = -1
        val offlinePlaces = allPlacesDb.value?.filter { place -> place.remoteId == defaultId }
        if (offlinePlaces != null) {
            for (place in offlinePlaces ) {
                apiService.addPlace(place).enqueue(object: Callback<ResponseData> {
                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        Toast.makeText(context, "Could not add the place to the server",
                            Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>
                    ) {
                        if (response.isSuccessful) {
                            GlobalScope.launch {
                                place.remoteId = response.body()?.remoteId!!
                                placeDAO.update(place)
                            }
                            Toast.makeText(context, "Place successfully added",
                                Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(context, "Place could not be added ",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        disposable = apiService.getAllPlaces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEach { place ->
                    println("Place: " + place.remoteId + " " + place)
                    if (!(allPlacesDb.value)!!.contains(place)) {
                        GlobalScope.launch {
                            placeDAO.insert(place)
                        }
                    } else {
                        GlobalScope.launch {
                            placeDAO.update(place)
                        }
                    }
                }
                if (allPlacesDb.value!!.size != it.size) {
                    allPlacesDb.value!!.forEach { place ->
                        if (!it.contains(place))
                            GlobalScope.launch {
                                placeDAO.delete(place)
                            }
                    }
                }
            }
    }

    suspend fun insert(place: Place) {
        if (isConnected) {
            apiService.addPlace(place).enqueue(object: Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(context, "Could not add the place to the server",
                        Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful) {
                        place.remoteId = response.body()?.remoteId!!
                        GlobalScope.launch {
                            placeDAO.insert(place)
                        }
                        Toast.makeText(context, "Place successfully added",
                            Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(context, "Place could not be added ",
                            Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        else {
            val placeId = placeDAO.insert(place)
            place.dbId = placeId
        }
    }

    suspend fun delete(place: Place) {
        if (isConnected) {
            apiService.deletePlace(place.remoteId).enqueue(object: Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(context, "Could not delete place from server",
                        Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            placeDAO.delete(place)
                        }
                        Toast.makeText(context, "Place successfully deleted",
                            Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(context, "Place could not be deleted ",
                            Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        else {
            Toast.makeText(context, "Unfortunately this action is not possible unless you have an internet connection",
                Toast.LENGTH_LONG).show()
        }
    }

    suspend fun update(place: Place) {
        if (isConnected) {
            apiService.updatePlace(place.remoteId, place).enqueue(object: Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(context, "Could not remotely update place.",
                        Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful) {
                        GlobalScope.launch {
                            placeDAO.update(place)
                        }
                        Toast.makeText(context, "Place successfully updated",
                            Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(context, "Place could not be updated ",
                            Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        else {
            Toast.makeText(context, "Unfortunately this action is not possible unless you have an internet connection",
                Toast.LENGTH_LONG).show()
        }
    }
}