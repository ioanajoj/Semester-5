package en.ubb.entityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.entityapp.domain.Entity
import en.ubb.entityapp.model.MainModel
import kotlinx.android.synthetic.main.new_entity_activity.*
import java.util.*

class NewEntityActivity : AppCompatActivity() {

    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entity_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)
        addButton.setOnClickListener { v ->
            val app: EntityApp = application as EntityApp
//            model.addEntity(app, Entity(0, bookTitleEditText.text.toString(), Date()))
            finish()
        }
    }
}