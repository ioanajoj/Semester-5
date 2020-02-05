package en.ubb.clujeatskotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import en.ubb.clujeatskotlin.db.Place
import en.ubb.clujeatskotlin.fragments.AddPlaceFragment
import en.ubb.clujeatskotlin.fragments.PlaceDetails
import en.ubb.clujeatskotlin.network.ConnectionLiveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var placeViewModel: PlaceViewModel
    private var newPlaceActivityRequestCode = 1
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PlaceListAdapter(this,
            deleteListener = {
                placeViewModel.delete(it)
            },
            updateListener = {
                val intent = Intent(this, PlaceDetails::class.java)
                intent.putExtra("placeInstance", it)
                intent.putExtra("dbId", it.dbId)
                startActivity(intent)
            }
        )

        swipeToRefresh.setOnRefreshListener {
            placeViewModel.refresh()
            swipeToRefresh.isRefreshing = false
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeViewModel.allPlaces.observe(this, Observer {
            places -> places?.let { adapter.setPlaces(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            Toast.makeText(applicationContext, "Add a new place", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddPlaceFragment::class.java)
            startActivityForResult(intent, newPlaceActivityRequestCode)
        }

        ConnectionLiveData(applicationContext).observe(this, Observer {
            isConnected =
            if (it.isConnected) {
                if (!isConnected)
                    Toast.makeText(applicationContext, "Internet connection now available",
                        Toast.LENGTH_SHORT).show()
                true
            } else {
                if (isConnected)
                    Toast.makeText(applicationContext, "Internet connection not available",
                        Toast.LENGTH_SHORT).show()
                false
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newPlaceActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Place(it.getStringExtra(AddPlaceFragment.NAME),
                    it.getStringExtra(AddPlaceFragment.ADDRESS),
                    it.getStringExtra(AddPlaceFragment.PHOTO))
                placeViewModel.insert(place)
            }
        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }
    }
}
