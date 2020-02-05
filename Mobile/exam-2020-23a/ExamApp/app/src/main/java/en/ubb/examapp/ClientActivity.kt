package en.ubb.examapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import en.ubb.examapp.model.MainModel
import en.ubb.examapp.service.EntityService
import en.ubb.examapp.service.ServiceFactory
import en.ubb.examapp.utils.loge
import kotlinx.android.synthetic.main.order_list_content.*
import kotlinx.coroutines.launch

class ClientActivity : AppCompatActivity() {

    private lateinit var model: MainModel
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        val tableName = intent.getStringExtra("table")
        model.viewModelScope.launch {
            try {
                val order = service.getTableOrderDetails(tableName)
                setContentView(R.layout.order_list_content)
                orderId.text = order.id.toString()
                orderTable.text = order.table
                orderDetails.text = order.details
                orderStatus.text = order.status
                orderTime.text = order.time.toString()
                orderType.text = order.type
            } catch (e: Exception) {
                loge(e)
                Toast.makeText(applicationContext, "Table does not exist", Toast.LENGTH_LONG).show()
            }
        }

    }
}