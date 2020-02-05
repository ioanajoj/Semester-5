package en.ubb.clujeatskotlin

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import en.ubb.clujeatskotlin.db.Place
import en.ubb.clujeatskotlin.db.PlacesRepository
import en.ubb.clujeatskotlin.db.PlacesRoomDatabase
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlacesRepository
    val allPlaces: LiveData<List<Place>>

    init {
        val placesDao = PlacesRoomDatabase.getDatabase(application, viewModelScope).placeDao()
        repository = PlacesRepository(placesDao, context = application.applicationContext)
        allPlaces = repository.getAllPlaces()
    }

    fun refresh() = viewModelScope.launch {
        repository.getAllPlaces()
    }

    fun insert(place: Place) = viewModelScope.launch {
        repository.insert(place)
    }

    fun delete(place: Place) = viewModelScope.launch {
        repository.delete(place)
    }

    fun update(place: Place) = viewModelScope.launch {
        repository.update(place)
    }

}