package com.app.ipassplus.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ipassplus.R

class SettingBottomSheetAdapter(private val list: List<String>, private var selectedItem: String,
    private val callback: (String) -> Unit) : RecyclerView.Adapter<SettingBottomSheetAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(layoutInflater.inflate(R.layout.item_options, parent, false))
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = list[position]
            holder.tvcamera.text = currentItem
            holder.radio.isChecked = selectedItem == currentItem

            holder.radio.setOnClickListener {
                selectedItem = currentItem
                callback.invoke(currentItem)
                notifyDataSetChanged()
            }

        }
        override fun getItemCount(): Int {
            return list.size
        }
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
            val tvcamera: TextView = itemView.findViewById(R.id.tvCameraApi)
            val radio: RadioButton = itemView.findViewById(R.id.rbApi)
        }
    }