package com.app.ipassplus.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ipassplus.R
import com.app.ipassplus.ui.dashboard.model.ScenariosItemModel

class ScenariosListAdapter(private val mList: ArrayList<ScenariosItemModel>) : RecyclerView.Adapter<ScenariosListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_scenarios, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.ivImage.setImageResource(ItemsViewModel.image)
        holder.tvFullProcess.text = ItemsViewModel.process
        holder.tvdescreption.text = ItemsViewModel.description.toString()

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvFullProcess: TextView = itemView.findViewById(R.id.tvFullProcess)
        val tvdescreption: TextView = itemView.findViewById(R.id.tvdescreption)
    }
}