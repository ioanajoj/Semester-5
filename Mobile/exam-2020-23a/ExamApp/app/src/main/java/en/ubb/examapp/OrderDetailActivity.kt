package en.ubb.examapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import en.ubb.examapp.model.MainModel
import en.ubb.examapp.service.EntityService
import en.ubb.examapp.service.ServiceFactory
import kotlinx.android.synthetic.main.order_list_content.*
import kotlinx.coroutines.launch

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var model: MainModel
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_list_content)

        model = ViewModelProviders.of(this).get(MainModel::class.java)


        if (savedInstanceState == null) {
            val id = intent.getIntExtra("id", -1)
            model.viewModelScope.launch {
                val order = service.getOrderDetails(id)
                orderId.text = id.toString()
                orderTable.text = order.table
                orderDetails.text = order.details
                orderStatus.text = order.status
                orderTime.text = order.time.toString()
                orderType.text = order.type
            }

//            orderTable.text = intent.getStringExtra("table")
//            orderDetails.text = intent.getStringExtra("details")
//            orderStatus.text = intent.getStringExtra("status")
//            orderTime.text = intent.getStringExtra("time")
//            orderType.text = intent.getStringExtra("type")
//
        }
    }
}