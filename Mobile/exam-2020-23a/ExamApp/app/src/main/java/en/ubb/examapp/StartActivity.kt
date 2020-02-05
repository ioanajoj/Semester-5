package en.ubb.examapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.start_activity.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        mainSectionButton.setOnClickListener { v ->
            val intent = Intent(application, WaiterOrdersListActivity::class.java)
            startActivity(intent)
        }

        ageSectionButton.setOnClickListener { v ->
            val intent = Intent(application, KitchenOrdersListActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener { v ->
            val tableName = clientTableName.text.toString()
            if (tableName != "") {
                val intent = Intent(application, ClientActivity::class.java)
                intent.putExtra("table", tableName)
                startActivity(intent)
            } else {
                Snackbar.make(v, "Please enter a table name first", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}