package en.ubb.entityapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import en.ubb.entityapp.EntityApp
import en.ubb.entityapp.Manager
import en.ubb.entityapp.domain.*
import en.ubb.entityapp.service.EntityService
import en.ubb.entityapp.service.ServiceFactory
import en.ubb.entityapp.utils.loge
import en.ubb.entityapp.utils.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.lang.Exception

class MainModel : ViewModel() {

    private var manager: Manager = Manager()
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)

    private val mutableTypes = MutableLiveData<List<Type>>().apply { value = emptyList() }
    private val mutableRobots = MutableLiveData<List<Robot>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableOldRobots = MutableLiveData<List<Robot>>().apply { value = emptyList() }

    val types: LiveData<List<Type>> = mutableTypes
    val robots: LiveData<List<Robot>> = mutableRobots
    val loading: LiveData<Boolean> = mutableLoading
    val oldRobots: LiveData<List<Robot>> = mutableOldRobots
    var received_types: List<String> = emptyList()

    fun fetchTypesFromNetwork(app: EntityApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                logi("Received entities: ${mutableTypes.value}")
                received_types = service.getTypes()
                mutableTypes.value = received_types.map { type_name -> Type(type_name) }
                launch(Dispatchers.IO) {
                    app.db.typesDao.deleteTypes()
                    app.db.typesDao.addTypes(types.value!!)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchTypes(app: EntityApp) {
        mutableLoading.value = true
        try {
            if (manager.networkConnectivity(context = app)) {
                GlobalScope.launch(Dispatchers.IO) {
                    val numberOfBooks = app.db.typesDao.numberOfEntities
                    if (numberOfBooks <= 0) {
                        fetchTypesFromNetwork(app)
                    }
                }
            }

        } catch (e: Exception) {
            loge("Received an error while retrieving data: ${e.message}")
        } finally {
            mutableLoading.value = false
        }
    }

    fun fetchRobotsFromNetwork(app: EntityApp, type: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                logi("Before request: ${mutableRobots.value}")
                mutableRobots.value = service.getRobots(type)
                logi("Received entities: ${mutableRobots.value}")
                launch(Dispatchers.IO) {
                    app.db.robotsDao.deleteRobots(type)
                    app.db.robotsDao.addRobots(robots.value!!)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchRobots(app: EntityApp, type: String) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                if (manager.networkConnectivity(context = app)) {
                    fetchRobotsFromNetwork(app, type)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }

    }

    fun addRobot(app: EntityApp, robot: Robot) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                logi("Robot to POST: $robot")
                val response = service.addRobot(robot)
                logi("Response: $response")
                robot.id = response.id
                launch(Dispatchers.IO) {
                    app.db.robotsDao.addRobot(robot)
                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun updateRobot(app: EntityApp, id: Int, height: Int) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                logi("Robot to update: $id $height")
                val response = service.updateRobot(UpdateRobotRequestObject(id, height))
                logi("Response: $response")
            } catch (e: Exception) {
                loge("Received an error while updating data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchOldRobotsFromNetwork(app: EntityApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                mutableOldRobots.value = service.getOldRobots().sortedByDescending { robot -> robot.age }.slice(
                    IntRange(0, 9)
                )
                logi("Received entities: ${mutableOldRobots.value}")
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchOldRobots(app: EntityApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                fetchOldRobotsFromNetwork(app)
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }

    }

    fun updateRobotAge(app: EntityApp, id: Int, age: Int) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                logi("Robot to update: $id $age")
                val response = service.updateRobotAge(UpdateRobotAgeRO(id, age))
                logi("Response: $response")
            } catch (e: Exception) {
                loge("Received an error while updating data: ${e.message}")
            } finally {
                mutableLoading.value = false
            }
        }
    }


//    fun deleteEntity(app: EntityApp, entity: Entity) {
//        viewModelScope.launch {
//            mutableLoading.value = true
//            try {
//                val response = service.deleteEntity(entity.id)
//                logi("Response: $response")
////                if (response.status == 200) {
//                    launch(Dispatchers.IO) {
//                        app.db.entityDao.deleteEntity(entity)
//                    }
////                }
//            } catch (e: Exception) {
//                loge("Received an error while deleting data: ${e.message}")
//            } finally {
//                mutableLoading.value = false
//            }
//        }
//    }
}