package en.ubb.entityapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_activity.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        mainSectionButton.setOnClickListener { v ->
            val intent = Intent(application, EntityListActivity::class.java)
            startActivity(intent)
        }

        ageSectionButton.setOnClickListener { v ->
            val intent = Intent(application, AgeListActivity::class.java)
            startActivity(intent)
        }
    }
}