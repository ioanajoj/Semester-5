package en.ubb.entityapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import en.ubb.entityapp.db.EntityDatabase
import en.ubb.entityapp.model.MainModel
import en.ubb.entityapp.utils.logd
import en.ubb.entityapp.utils.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit


private const val NORMAL_CLOSURE_STATUS = 1000

class EntityApp : Application() {

    lateinit var db: EntityDatabase
    lateinit var model: MainModel

    override fun onCreate() {
        super.onCreate()
        logi("EntityApp created")
        db = Room.databaseBuilder<EntityDatabase>(applicationContext,
            EntityDatabase::class.java, "entity-database").build()
        model = MainModel()

        // ws test
        val clientWebsoket = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("ws://192.168.3.137:2202/")
            .build()
        val wsListener = EchoWebSocketListener()
        clientWebsoket.newWebSocket(request, wsListener) // this provide to make 'Open ws connection'

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        clientWebsoket.dispatcher().executorService().shutdown()
    }

    inner class EchoWebSocketListener : WebSocketListener() {
        lateinit var webSocket: WebSocket
        override fun onOpen(webSocket: WebSocket, response: Response) {
            this.webSocket = webSocket
            webSocket.send("Hello, there!")
            webSocket.send("What's up?")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            logd("Receiving : ${text!!}")
            val receivedObj = JSONObject(text)
            val message: String = "A new object has been added to the server: " + receivedObj.get("name") + " " + receivedObj.get("specs") + " " + receivedObj.get("age")
            model.viewModelScope.launch {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
            logd("Receiving bytes : ${bytes!!.hex()}")
        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            webSocket!!.close(NORMAL_CLOSURE_STATUS, null)
            logd("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            logd("Error : ${t.message}", t)
        }

        fun send(message: String) {
            webSocket.send(message)
        }

        fun close() {
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
        }
    }
}