package en.ubb.entityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import en.ubb.entityapp.domain.Entity
import en.ubb.entityapp.model.MainModel
import en.ubb.entityapp.utils.logd
import kotlinx.android.synthetic.main.entity_detail_activity.*
import java.util.*

class EntityDetailActivity : AppCompatActivity() {

    private lateinit var entity: Entity
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_detail_activity)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        if (savedInstanceState == null) {
            val id = intent.getIntExtra(ARG_ITEM_ID, -1)
            titleTextView.text = intent.getStringExtra(ARG_ITEM_TITLE)
            dateTextView.text = intent.getStringExtra(ARG_ITEM_DATE)
            entity = Entity(id, intent.getStringExtra(ARG_ITEM_TITLE), Date())
        }

        deleteButton.setOnClickListener { v ->
            val app: EntityApp = application as EntityApp
            model.deleteEntity(app, entity)
            finish()
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "id"
        const val ARG_ITEM_TITLE = "title"
        const val ARG_ITEM_DATE = "date"
    }
}