package en.ubb.entityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import en.ubb.entityapp.adapter.AgeRobotsAdapter
import en.ubb.entityapp.domain.Robot
import en.ubb.entityapp.model.MainModel
import en.ubb.entityapp.utils.logd
import kotlinx.android.synthetic.main.age_list_activity.fabAdd
import kotlinx.android.synthetic.main.age_list_activity.fabRefresh
import kotlinx.android.synthetic.main.age_list_activity.progressBar
import kotlinx.android.synthetic.main.age_list_activity.recyclerView

class AgeListActivity : AppCompatActivity() {


    private var adapter: AgeRobotsAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.age_list_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()

        assert(recyclerView != null)
        setupRecyclerView(recyclerView)
        observeModel()
        loadEntities()

        fabAdd.setOnClickListener { v ->
//            if (!manager.networkConnectivity(this)) {
//                Snackbar.make(v, "Connect to internet to do this", Snackbar.LENGTH_SHORT).show()
//            } else {
//                val intent = Intent(application, NewEntityActivity::class.java)
//                startActivity(intent)
//            }
        }

        fabRefresh.setOnClickListener { v ->
            if (!manager.networkConnectivity(this)) {
                Snackbar.make(v, "Connect to internet to do this", Snackbar.LENGTH_SHORT).show()
            } else {
                model.fetchOldRobotsFromNetwork(application as EntityApp)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        model.fetchOldRobotsFromNetwork(application as EntityApp)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = AgeRobotsAdapter()
        recyclerView.adapter = adapter
        (application as EntityApp).db.robotsDao.robots
            .observe(this, Observer { entities ->
                if (entities != null) {
                    adapter!!.setData(entities)
                }
            })
    }


    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.oldRobots.observe { displayEntities(it ?: emptyList()) }
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@AgeListActivity, Observer { observe(it) })

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progressBar.visibility = if (loading!!) View.VISIBLE else View.GONE
    }


    private fun displayEntities(entities: List<Robot>) {
        adapter?.setData(entities)
    }

    private fun loadEntities() {
        if (!manager.networkConnectivity(this)) {
            Toast.makeText(applicationContext, "You need to connect to internet to do this", Toast.LENGTH_LONG).show()
        } else {
            model.fetchOldRobots(application as EntityApp)
        }
    }
}