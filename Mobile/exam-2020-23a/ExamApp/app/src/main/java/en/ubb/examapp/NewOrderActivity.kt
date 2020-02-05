package en.ubb.examapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.examapp.domain.Order
import en.ubb.examapp.model.MainModel
import kotlinx.android.synthetic.main.new_order_activity.*

class NewOrderActivity : AppCompatActivity() {

    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_order_activity)
        model = ViewModelProviders.of(this).get(MainModel::class.java)

        addButton.setOnClickListener { v ->
            val app: ExamApp = application as ExamApp
            model.addOrder(app, Order(0, orderTable.text.toString(),
                orderDetails.text.toString(), orderStatus.text.toString(),
                orderTime.text.toString().toInt(), orderType.text.toString()))
            finish()
        }
    }
}