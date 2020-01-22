package en.ubb.entityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.entityapp.domain.Robot
import en.ubb.entityapp.model.MainModel
import kotlinx.android.synthetic.main.new_entity_activity.addButton
import kotlinx.android.synthetic.main.new_robot_activity.robotName
import kotlinx.android.synthetic.main.new_robot_activity.robotSpecs
import kotlinx.android.synthetic.main.new_robot_activity.*

class NewRobotActivity : AppCompatActivity() {
    private lateinit var model: MainModel
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_robot_activity)

        if (savedInstanceState == null) {
            type = intent.getStringExtra("type")
        }

        model = ViewModelProviders.of(this).get(MainModel::class.java)
        addButton.setOnClickListener { v ->
            val app: EntityApp = application as EntityApp
            model.addRobot(app, Robot(0, robotName.text.toString(), robotSpecs.text.toString(),
                robotHeight.text.toString().toInt(), type, robotAge.text.toString().toInt()))
            finish()
        }
    }
}