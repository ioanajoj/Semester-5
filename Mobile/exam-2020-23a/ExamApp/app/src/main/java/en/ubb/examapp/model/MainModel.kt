package en.ubb.examapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import en.ubb.examapp.ExamApp
import en.ubb.examapp.Manager
import en.ubb.examapp.domain.Entity
import en.ubb.examapp.domain.Order
import en.ubb.examapp.service.EntityService
import en.ubb.examapp.service.ServiceFactory
import en.ubb.examapp.service.UpdateOrderRequestObject
import en.ubb.examapp.utils.loge
import en.ubb.examapp.utils.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainModel : ViewModel() {

    private var manager: Manager = Manager()
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)

//    TODO("Add MutableLiveData")
    private val mutableWaiterOrders = MutableLiveData<List<Order>>().apply { value = emptyList() }
    private val mutableKitchenOrders = MutableLiveData<List<Order>>().apply { value = emptyList() }
    private val mutableEntities = MutableLiveData<List<Entity>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableMessage = MutableLiveData<String>()

//    TODO("Add LiveData")
    val waiterOrders: LiveData<List<Order>> = mutableWaiterOrders
    val kitchenOrders: LiveData<List<Order>> = mutableKitchenOrders
    val entities: LiveData<List<Entity>> = mutableEntities
    val loading: LiveData<Boolean> = mutableLoading
    val message: LiveData<String> = mutableMessage

    var offlineOrders: List<Order> = emptyList()

    fun fetchEntitiesFromNetwork(app: ExamApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
//                TODO("Get from Server")
                mutableEntities.value = service.getEntities()
                logi("Received entities: ${mutableEntities.value}")
//                TODO("Sync local db")
                launch(Dispatchers.IO) {
                    app.db.entityDao.deleteEntities()
                    app.db.entityDao.addEntities(entities.value!!)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchEntities(app: ExamApp) {
        mutableLoading.value = true
        try {
            if (manager.networkConnectivity(context = app)) {
                GlobalScope.launch(Dispatchers.IO) {
                    val numberOfEntities = app.db.entityDao.numberOfEntities
                    if (numberOfEntities <= 0) {
                        fetchEntitiesFromNetwork(app)
                    }
                }
            }
        } catch (e: Exception) {
            loge("Received an error while retrieving data: ${e.message}")
            mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
        } finally {
            mutableLoading.value = false
        }
    }

    fun addEntity(app: ExamApp, entity: Entity) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                val response = service.addEntity(entity)
                logi("Response: $response")
//                launch(Dispatchers.IO) {
//                    app.db.entityDao.addEntity(entity)
//                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun deleteEntity(app: ExamApp, entity: Entity) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                val response = service.deleteEntity(entity.id)
                logi("Response: $response")
                launch(Dispatchers.IO) {
                    app.db.entityDao.deleteEntity(entity)
                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchOrdersFromNetwork(app: ExamApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
//                TODO("Get from Server")
                mutableWaiterOrders.value = service.getOrders()
                logi("Received entities: ${mutableWaiterOrders.value}")
//                TODO("Sync local db")
                launch(Dispatchers.IO) {
                    app.db.entityDao.deleteEntities()
//                    app.db.entityDao.deleteOrders("ready")
                    app.db.entityDao.addOrders(waiterOrders.value!!)
                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchWaiterOrders(app: ExamApp) {
        mutableLoading.value = true
        try {
            if (manager.networkConnectivity(context = app)) {
                GlobalScope.launch(Dispatchers.IO) {
//                    offlineOrders = app.db.entityDao.getOfflineOrders()
//                    offlineOrders.forEach { order ->
//                        service.addOrder(order)
//                    }
                    val numberOfEntities = app.db.entityDao.numberOfEntities
                    if (numberOfEntities <= 0) {
                        fetchOrdersFromNetwork(app)
                    }
                }
            }
        } catch (e: Exception) {
            loge("Received an error while retrieving data: ${e.message}")
            mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
        } finally {
            mutableLoading.value = false
        }
    }

    fun addOrder(app: ExamApp, order: Order) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                if (manager.networkConnectivity(context = app)) {
                    val response = service.addOrder(order)
                    logi("Response: $response")
                    fetchOrdersFromNetwork(app)
                } else {
                    launch(Dispatchers.IO) {
                        app.db.entityDao.addOrder(order)
                    }
                }
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchKitchenOrdersFromNetwork(app: ExamApp) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                if (manager.networkConnectivity(context = app)) {
//                TODO("Get from Server")
                    mutableKitchenOrders.value = service.getRecordedOrders()
                    logi("Received entities: ${mutableKitchenOrders.value}")
                } else {
                    mutableMessage.value = "You are not able to do this while offline"
                }
//                TODO("Sync local db")
//                launch(Dispatchers.IO) {
//                    app.db.entityDao.deleteOrders("recorded")
//                    app.db.entityDao.addOrders(mutableKitchenOrders.value!!)
//                }
            } catch (e: Exception) {
                loge("Received an error while retrieving data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun updateOrderStatus(updateObj: UpdateOrderRequestObject) {
        viewModelScope.launch {
            mutableLoading.value = true
            try {
                service.updateOrderStatus(updateObj)
            } catch (e: Exception) {
                loge("Received an error while deleting data: ${e.message}")
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }
}