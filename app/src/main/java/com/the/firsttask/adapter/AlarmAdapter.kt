package com.the.firsttask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.the.firsttask.R
import com.the.firsttask.database.AlarmEntity

class AlarmAdapter(
    private val mList: List<AlarmEntity>,
    var mCtx: Context,
) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_alarm_card, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.description.text = item.description
        holder.date.text = item.date
        holder.time.text = item.time
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView = itemView.findViewById(R.id.tv_des_info)
        var date: TextView = itemView.findViewById(R.id.tv_date_info)
        var time: TextView = itemView.findViewById(R.id.tv_time_info)
    }
}