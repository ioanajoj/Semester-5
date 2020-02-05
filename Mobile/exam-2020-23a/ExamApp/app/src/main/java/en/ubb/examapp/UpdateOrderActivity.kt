package en.ubb.examapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import en.ubb.examapp.model.MainModel
import en.ubb.examapp.service.EntityService
import en.ubb.examapp.service.ServiceFactory
import en.ubb.examapp.service.UpdateOrderRequestObject
import kotlinx.android.synthetic.main.update_order_activity.*
import kotlinx.coroutines.launch

class UpdateOrderActivity : AppCompatActivity() {

    private lateinit var model: MainModel
    private val service: EntityService = ServiceFactory
        .createRetrofitService(EntityService::class.java, EntityService.SERVICE_ENDPOINT)
    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_order_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        if (savedInstanceState == null) {
            id = intent.getIntExtra("id", -1)
        }

        updateButton.setOnClickListener {
            model.updateOrderStatus(UpdateOrderRequestObject(id, orderStatusEdit.text.toString()))
            finish()
        }

    }
}