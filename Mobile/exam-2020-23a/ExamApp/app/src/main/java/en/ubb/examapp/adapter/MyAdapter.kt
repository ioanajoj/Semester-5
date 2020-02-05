package en.ubb.examapp.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import en.ubb.examapp.EntityDetailActivity
import en.ubb.examapp.R
import en.ubb.examapp.domain.Entity

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

//        TODO("Replace  ")
    private var mValues = mutableListOf<Entity>()

    fun setData(entities: List<Entity>) {
//        TODO("Replace Entity")
        mValues.clear()
        mValues.addAll(entities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Replace layout")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.entity_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = mValues[position]
        holder.mItem = entity
        holder.mIdView.text = entity.id.toString()
        holder.mContentView.text = entity.title

        holder.mView.setOnClickListener { v ->
//            TODO("Decide what to do on item click")
            val context = v.context
            val intent = Intent(context, EntityDetailActivity::class.java)
            val args = Bundle()
            args.putInt(EntityDetailActivity.ARG_ITEM_ID, entity.id)
            args.putString(EntityDetailActivity.ARG_ITEM_TITLE, entity.title)
            args.putString(EntityDetailActivity.ARG_ITEM_DATE, entity.date.toString())
            intent.putExtras(args)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
//        TODO("Match stuff with xml layout")
        internal val mIdView: TextView = mView.findViewById(R.id.id)
        internal val mContentView: TextView = mView.findViewById(R.id.content)
        internal var mItem: Entity? = null

        override fun toString(): String {
            return "${super.toString()} '${mContentView.text}'"
        }
    }
}