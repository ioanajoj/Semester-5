package en.ubb.entityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.entityapp.model.MainModel
import kotlinx.android.synthetic.main.robot_detail_activity.*

class RobotDetailActivity : AppCompatActivity() {

    private var id: Int = -1
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.robot_detail_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        if (savedInstanceState == null) {
            id = intent.getIntExtra(ARG_ITEM_ID, -1)
            robotName.text = intent.getStringExtra(ARG_ITEM_NAME)
        }

        updateButton.setOnClickListener { v ->
            val app: EntityApp = application as EntityApp
            val newHeight = robotHeight.text.toString().toInt()
            model.updateRobot(app, id, newHeight)
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