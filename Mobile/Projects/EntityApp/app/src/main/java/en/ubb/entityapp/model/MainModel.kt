package en.ubb.entityapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import en.ubb.entityapp.EntityApp
import en.ubb.entityapp.Manager
import en.ubb.entityapp.domain.Entity
import en.ubb.entityapp.service.EntityService
import en.ubb.entityapp.service.ServiceFactory
import en.ubb.entityapp.utils.loge
import en.ubb.entityapp.utils.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainModel : ViewModel() {

    private var manager: Manager = Manager()
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)

    private val mutableEntities = MutableLiveData<List<Entity>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }

    val entities: LiveData<List<Entity>> = mutableEntities
    val loading: LiveData<Boolean> = mutableLoading

    fun fetchEntitiesFromNetwork(app: EntityApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                mutableEntities.value = service.getEntities()
                logi("Received entities: ${mutableEntities.value}")
                launch(Dispatchers.IO) {
                    app.db.entityDao.deleteEntities()
                    app.db.entityDao.addEntities(entities.value!!)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchEntities(app: EntityApp) {
        mutableLoading.value = true
        try {
            if (manager.networkConnectivity(context = app)) {
                GlobalScope.launch(Dispatchers.IO) {
                    val numberOfBooks = app.db.entityDao.numberOfEntities
                    if (numberOfBooks <= 0) {
                        fetchEntitiesFromNetwork(app)
                    }
                }
            }

        } catch (e: Exception) {
            loge("Received an error while retrieving data: ${e.message}")
        } finally {
            mutableLoading.value = false
        }
    }

    fun addEntity(app: EntityApp, entity: Entity) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                val response = service.addEntity(entity)
                logi("Response: $response")
                launch(Dispatchers.IO) {
                    app.db.entityDao.addEntity(entity)
                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun deleteEntity(app: EntityApp, entity: Entity) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                val response = service.deleteEntity(entity.id)
                logi("Response: $response")
//                if (response.status == 200) {
                    launch(Dispatchers.IO) {
                        app.db.entityDao.deleteEntity(entity)
                    }
//                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }
}