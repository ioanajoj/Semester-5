package en.ubb.clujeatskotlin

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import en.ubb.clujeatskotlin.db.Place

class PlaceListAdapter internal constructor(
    context: Context,
    private val deleteListener: (Place) -> Unit,
    private val updateListener: (Place) -> Unit)
    : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var places = emptyList<Place>()

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextField: TextView = itemView.findViewById(R.id.nameTextField)
        val addressTextField: TextView = itemView.findViewById(R.id.addressTextField)

        fun bind(pos: Int, listener: (Place) -> Unit) = with(itemView) {
            val deleteButton = findViewById<ImageButton>(R.id.delete_button)
            deleteButton.setOnClickListener {
                deleteListener(context, pos, listener)
            }

            val recyclerViewPlaceItem = findViewById<View>(R.id.recyclerview_place_item)
            recyclerViewPlaceItem.setOnClickListener {
                Toast.makeText(context, "Item pressed", Toast.LENGTH_SHORT).show()
                updateListener(places[pos])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val current = places[position]
        holder.nameTextField.text = current.name
        holder.addressTextField.text = current.address

        holder.bind(position, deleteListener)
    }

    internal fun setPlaces(places: List<Place>) {
        this.places = places
        notifyDataSetChanged()
    }

    override fun getItemCount() = places.size

    internal fun deleteListener(context: Context, pos: Int, listener: (Place) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Are you sure you want to delete this place?")
        builder.setPositiveButton("Yes"){ _, _ ->
            listener(places[pos])
        }
        builder.setNegativeButton("No"){ _, _ ->
            Toast.makeText(context,"Place will not be deleted",Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}