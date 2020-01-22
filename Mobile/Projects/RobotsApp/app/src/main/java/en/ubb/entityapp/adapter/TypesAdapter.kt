package en.ubb.entityapp.adapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import en.ubb.entityapp.R
import en.ubb.entityapp.RobotsListActivity
import en.ubb.entityapp.domain.Type

class TypesAdapter : RecyclerView.Adapter<TypesAdapter.ViewHolder>() {

    private var mValues = mutableListOf<Type>()

    fun setData(books: List<Type>) {
        mValues.clear()
        mValues.addAll(books)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.type_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = mValues[position]
        holder.mItem = entity
        holder.mTypeName.text = entity.name

        holder.mView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, RobotsListActivity::class.java)
            val args = Bundle()
            args.putString("type", entity.name)
            intent.putExtras(args)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mTypeName: TextView = mView.findViewById(R.id.typeName)
        internal var mItem: Type? = null

        override fun toString(): String {
            return "${super.toString()} '${mTypeName.text}'"
        }
    }
}