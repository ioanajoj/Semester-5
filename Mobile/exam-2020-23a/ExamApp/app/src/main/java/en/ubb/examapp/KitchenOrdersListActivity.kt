package en.ubb.examapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import en.ubb.examapp.adapter.KitchenOrdersAdapter
import en.ubb.examapp.domain.Order
import en.ubb.examapp.model.MainModel
import en.ubb.examapp.utils.logd
import kotlinx.android.synthetic.main.entity_list_activity.*

class KitchenOrdersListActivity : AppCompatActivity() {

    private var adapter: KitchenOrdersAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kitchen_orders_list_activity)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()

        assert(recyclerView != null)
        setupRecyclerView(recyclerView)
        observeModel()
        loadEntities()

        fabAdd.setOnClickListener { v ->
//            val intent = Intent(application, NewOrderActivity::class.java)
//            startActivity(intent)
        }

        fabRefresh.setOnClickListener { v ->
            if (!manager.networkConnectivity(this)) {
                Snackbar.make(v, "Connect to internet to do this", Snackbar.LENGTH_SHORT).show()
            } else {
                model.fetchKitchenOrdersFromNetwork(application as ExamApp)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        model.fetchKitchenOrdersFromNetwork(application as ExamApp)
    }

    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.message.observe { displayMessage(it) }
        model.kitchenOrders.observe { displayEntities(it ?: emptyList()) }
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@KitchenOrdersListActivity, Observer { observe(it) })

    private fun displayEntities(entities: List<Order>) {
        adapter?.setData(entities)
    }

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progressBar.visibility = if (loading!!) View.VISIBLE else View.GONE
    }

    private fun loadEntities() {
        model.fetchKitchenOrdersFromNetwork(application as ExamApp)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = KitchenOrdersAdapter()
        recyclerView.adapter = adapter
        (application as ExamApp).db.entityDao.orders
            .observe(this, Observer { entities ->
                if (entities != null) {
                    adapter!!.setData(entities)
                }
            })
    }

    fun displayMessage(message: String?) {
        progressBar.visibility = View.GONE
        var errorMessage = "Unknown error"
        if (message != null) {
            errorMessage = message
        }
        Snackbar.make(recyclerView!!, errorMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") { loadEntities() }.show()
    }
}