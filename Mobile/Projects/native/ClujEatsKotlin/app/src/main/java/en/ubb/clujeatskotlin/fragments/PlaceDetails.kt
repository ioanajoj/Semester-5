package en.ubb.clujeatskotlin.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import en.ubb.clujeatskotlin.PlaceViewModel
import en.ubb.clujeatskotlin.R
import kotlinx.android.synthetic.main.place_details.*

class PlaceDetails : AppCompatActivity() {

    private lateinit var placeViewModel: PlaceViewModel
    private var placeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_details)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeId = intent.getLongExtra("dbId", -1)

        setListeners()
    }

    private fun setListeners() {
        placeViewModel.allPlaces.observe(this, Observer {
                places -> for (place in places) {
            if (place.dbId == placeId) {
                nameTextField.text = place.name
                addressTextField.text = place.address
            }
        }
        })

        nameTextField.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit the name of this place")

            val nameEditText = EditText(this)
            nameEditText.hint = "Enter new name"
            builder.setView(nameEditText)

            builder.setPositiveButton("Done"){ _, _ ->
                (placeViewModel.allPlaces.value)!!.forEach {
                    place ->
                    run {
                        if (place.dbId == placeId) {
                            place.name = nameEditText.text.toString()
                            placeViewModel.update(place)
                        }
                    }
                }
            }
            builder.setNegativeButton("Cancel"){_,_ -> run {} }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        addressTextField.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit the address of this place")

            val addressEditText = EditText(this)
            addressEditText.text.append(addressTextField.text)
            builder.setView(addressEditText)

            builder.setPositiveButton("Done"){ _, _ ->
                (placeViewModel.allPlaces.value)!!.forEach {
                        place ->
                    run {
                        if (place.dbId == placeId) {
                            place.address = addressEditText.text.toString()
                            placeViewModel.update(place)
                        }
                    }
                }
            }
            builder.setNegativeButton("Cancel"){_,_ -> run {} }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}