package en.ubb.entityapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import en.ubb.entityapp.R
import en.ubb.entityapp.RobotAgeDetailActivity
import en.ubb.entityapp.RobotDetailActivity
import en.ubb.entityapp.domain.Robot

class AgeRobotsAdapter : RecyclerView.Adapter<AgeRobotsAdapter.ViewHolder>() {

    private var mValues = mutableListOf<Robot>()

    fun setData(books: List<Robot>) {
        mValues.clear()
        mValues.addAll(books)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeRobotsAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.robot_full_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = mValues[position]
        holder.mItem = entity
        holder.mRobotId.text = entity.id.toString()
        holder.mRobotType.text = entity.type
        holder.mRobotName.text = entity.name
        holder.mRobotSpecs.text = entity.specs
        holder.mRobotHeight.text = entity.height.toString()
        holder.mRobotAge.text = entity.age.toString()

        holder.mView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, RobotAgeDetailActivity::class.java)
            intent.putExtra(RobotAgeDetailActivity.ARG_ITEM_ID, entity.id)
            intent.putExtra(RobotAgeDetailActivity.ARG_ITEM_NAME, entity.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mRobotId: TextView = mView.findViewById(R.id.robotId)
        internal val mRobotType: TextView = mView.findViewById(R.id.robotType)
        internal val mRobotName: TextView = mView.findViewById(R.id.robotName)
        internal val mRobotSpecs: TextView = mView.findViewById(R.id.robotSpecs)
        internal val mRobotHeight: TextView = mView.findViewById(R.id.robotHeight)
        internal val mRobotAge: TextView = mView.findViewById(R.id.robotAge)
        internal var mItem: Robot? = null

        override fun toString(): String {
            return "${super.toString()} '${mRobotName.text}'"
        }
    }

}