package en.ubb.entityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.entityapp.model.MainModel
import kotlinx.android.synthetic.main.robot_age_detail_activity.*

class RobotAgeDetailActivity : AppCompatActivity() {

    private var id: Int = -1
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.robot_age_detail_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        if (savedInstanceState == null) {
            id = intent.getIntExtra(ARG_ITEM_ID, -1)
            robotName.text = intent.getStringExtra(ARG_ITEM_NAME)
        }

        updateButton.setOnClickListener { v ->
            val app: EntityApp = application as EntityApp
            val newAge = robotAge.text.toString().toInt()
            model.updateRobotAge(app, id, newAge)
            finish()
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "id"
        const val ARG_ITEM_NAME = "name"
    }
}