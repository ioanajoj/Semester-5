package en.ubb.entityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import en.ubb.entityapp.adapter.TypesAdapter
import en.ubb.entityapp.domain.Type
import en.ubb.entityapp.model.MainModel
import en.ubb.entityapp.utils.logd
import kotlinx.android.synthetic.main.entity_list_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.json
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit


private const val NORMAL_CLOSURE_STATUS = 1000

class EntityListActivity : AppCompatActivity() {

    private var adapter: TypesAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_list_activity)
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
                val intent = Intent(application, NewEntityActivity::class.java)
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
//            .url("ws://192.168.3.137:2202")
//            .build()
//        val wsListener = EchoWebSocketListener()
//        clientWebsoket.newWebSocket(request, wsListener) // this provide to make 'Open ws connection'
//
//        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
//        clientWebsoket.dispatcher().executorService().shutdown()
    }

    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.types.observe { displayEntities(it ?: emptyList()) }
//        model.message.observe { showError(it) }
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@EntityListActivity, Observer { observe(it) })

    private fun displayEntities(entities: List<Type>) {
        adapter?.setData(entities)
    }

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progressBar.visibility = if (loading!!) View.VISIBLE else View.GONE
    }

    private fun loadEntities() {
        model.fetchTypes(application as EntityApp)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = TypesAdapter()
        recyclerView.adapter = adapter
        (application as EntityApp).db.typesDao.entities
            .observe(this, Observer { entities ->
                if (entities != null) {
                    adapter!!.setData(entities)
                }
            })
    }

//    inner class EchoWebSocketListener : WebSocketListener() {
//        lateinit var webSocket: WebSocket
//        override fun onOpen(webSocket: WebSocket, response: Response) {
//            this.webSocket = webSocket
//            webSocket.send("Hello, there!")
//            webSocket.send("What's up?")
//        }
//
//        override fun onMessage(webSocket: WebSocket?, text: String?) {
//            logd("Receiving : ${text!!}")
//            val receivedObj = JSONObject(text)
//            val message: String = "A new object has been added to the server: " + receivedObj.get("name") + " " + receivedObj.get("specs") + " " + receivedObj.get("age")
//            GlobalScope.launch(Dispatchers.IO) {
//                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//            }
//        }
//
//        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
//            logd("Receiving bytes : ${bytes!!.hex()}")
//        }
//
//        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
//            webSocket!!.close(NORMAL_CLOSURE_STATUS, null)
//            logd("Closing : $code / $reason")
//        }
//
//        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//            logd("Error : ${t.message}", t)
//        }
//
//        fun send(message: String) {
//            webSocket.send(message)
//        }
//
//        fun close() {
//            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
//        }
//    }
}