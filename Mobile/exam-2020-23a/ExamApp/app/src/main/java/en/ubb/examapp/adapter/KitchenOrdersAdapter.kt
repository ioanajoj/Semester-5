package en.ubb.examapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import en.ubb.examapp.OrderDetailActivity
import en.ubb.examapp.R
import en.ubb.examapp.UpdateOrderActivity
import en.ubb.examapp.domain.Order

class KitchenOrdersAdapter : RecyclerView.Adapter<KitchenOrdersAdapter.ViewHolder>() {

    //        TODO("Replace Entity")
    private var mValues = mutableListOf<Order>()

    fun setData(entities: List<Order>) {
//        TODO("Replace Entity")
        mValues.clear()
        mValues.addAll(entities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Replace layout")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.order_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = mValues[position]
        holder.mItem = entity
        holder.mIdView.text = entity.id.toString()
        holder.table.text = entity.table
        holder.details.text = entity.details
        holder.status.text = entity.status
        holder.time.text = entity.time.toString()
        holder.type.text = entity.type


        holder.mView.setOnClickListener { v ->
            //            TODO("Decide what to do on item click")
            val context = v.context
            val intent = Intent(context, UpdateOrderActivity::class.java)
            intent.putExtra("id", entity.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        //        TODO("Match stuff with xml layout")
        internal val mIdView: TextView = mView.findViewById(R.id.orderId)
        internal val table: TextView = mView.findViewById(R.id.orderTable)
        internal val details: TextView = mView.findViewById(R.id.orderDetails)
        internal val status: TextView = mView.findViewById(R.id.orderStatus)
        internal val time: TextView = mView.findViewById(R.id.orderTime)
        internal val type: TextView = mView.findViewById(R.id.orderType)
        internal var mItem: Order? = null

        override fun toString(): String {
            return "${super.toString()} '${details.text}'"
        }
    }
}