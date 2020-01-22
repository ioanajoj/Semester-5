package en.ubb.entityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import en.ubb.entityapp.adapter.RobotsAdapter
import en.ubb.entityapp.domain.Robot
import en.ubb.entityapp.model.MainModel
import en.ubb.entityapp.utils.logd
import kotlinx.android.synthetic.main.robots_list_activity.*

class RobotsListActivity : AppCompatActivity() {
    private var adapter: RobotsAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    private lateinit var type: String

    private var receivedRobots: List<Robot> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_list_activity)

        if (savedInstanceState == null) {
            type = intent.getStringExtra("type")
        }

        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()

        assert(recyclerView != null)

        setupRecyclerView(recyclerView)
        observeModel()
        loadEntities()

        fabAdd.setOnClickListener { v ->
            if (!manager.networkConnectivity(this)) {
                Snackbar.make(v, "Connect to internet to do this", Snackbar.LENGTH_SHORT).show()
            } else {
                val intent = Intent(application, NewRobotActivity::class.java)
                intent.putExtra("type", type)
                startActivity(intent)
            }
        }

        fabRefresh.setOnClickListener { v ->
            if (!manager.networkConnectivity(this)) {
                Snackbar.make(v, "Connect to internet to do this", Snackbar.LENGTH_SHORT).show()
            } else {
                model.fetchTypesFromNetwork(application as EntityApp)
            }
        }

//        // ws test
//        val clientWebsoket = OkHttpClient.Builder()
//            .readTimeout(3, TimeUnit.SECONDS)
//            .build()
//        val request = Request.Builder()
//            .url("ws://192.168.3.137:3000")
//            .build()
//        val wsListener = EchoWebSocketListener()
//        clientWebsoket.newWebSocket(request, wsListener) // this provide to make 'Open ws connection'
//
//        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
//        clientWebsoket.dispatcher().executorService().shutdown()
    }

    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.robots.observe { displayRobots(it ?: emptyList()) }
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@RobotsListActivity, Observer { observe(it) })

    private fun displayRobots(entities: List<Robot>) {
        adapter?.setData(entities)
    }

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progressBar.visibility = if (loading!!) View.VISIBLE else View.GONE
    }

    private fun loadEntities() {
        model.fetchRobots(application as EntityApp, type)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = RobotsAdapter()
        recyclerView.adapter = adapter
        (application as EntityApp).db.robotsDao.getRobotsByType(type)
            .observe(this, Observer { entities ->
                if (entities != null) {
                    adapter!!.setData(entities)
                }
            })

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}