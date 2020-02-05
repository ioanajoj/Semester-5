package en.ubb.examapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import en.ubb.examapp.db.EntityDatabase
import en.ubb.examapp.model.MainModel
import en.ubb.examapp.utils.logd
import en.ubb.examapp.utils.logi
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ExamApp : Application() {

    lateinit var db: EntityDatabase
    lateinit var model: MainModel

    override fun onCreate() {
        super.onCreate()
        logi("EntityApp created")
        db = Room.databaseBuilder<EntityDatabase>(applicationContext,
            EntityDatabase::class.java, "entity-database").build()
        model = MainModel()

        // WebSocket
        val clientWebSocket = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("ws://192.168.43.230:2301/")
            .build()
        val wsListener = EchoWebSocketListener()
        clientWebSocket.newWebSocket(request, wsListener)

        clientWebSocket.dispatcher().executorService().shutdown()
    }

    inner class EchoWebSocketListener : WebSocketListener() {
        lateinit var webSocket: WebSocket
        override fun onOpen(webSocket: WebSocket, response: Response) {
            this.webSocket = webSocket
            webSocket.send("Hello, there!")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            logd("Receiving : ${text!!}")
            val receivedObj = JSONObject(text)
//            TODO("Edit receiveObj.get()")
            val message: String = "A new object has been added to the server: id:" + receivedObj.get("id") +
                    " table:" + receivedObj.get("table") +
                    " details:" + receivedObj.get("details")
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

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}