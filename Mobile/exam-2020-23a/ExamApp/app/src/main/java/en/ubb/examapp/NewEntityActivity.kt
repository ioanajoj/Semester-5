package en.ubb.examapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.examapp.domain.Entity
import en.ubb.examapp.model.MainModel
import kotlinx.android.synthetic.main.new_entity_activity.*
import java.util.*

class NewEntityActivity : AppCompatActivity() {

    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entity_activity)
        model = ViewModelProviders.of(this).get(MainModel::class.java)

        addButton.setOnClickListener { v ->
            val app: ExamApp = application as ExamApp
            model.addEntity(app, Entity(0, entityTitleEditText.text.toString(), Date()))
            finish()
        }
    }
}