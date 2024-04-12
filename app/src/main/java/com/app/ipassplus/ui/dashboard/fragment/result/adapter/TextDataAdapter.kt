package com.app.ipassplus.ui.dashboard.fragment.result.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.ipassplus.R

class TextDataAdapter internal  constructor(
    private val context: Context,
    private val dataList: MutableList<String>?,
//    private val dataList: HashMap<String, List<String>>
): BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return this.dataList?.size!!
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return 1
      //  return this.dataList?.get(listPosition)?.values?.size!!
    }

    override fun getGroup(listPosition: Int): String? {
        return this.dataList?.get(listPosition)!!
      //  return this.dataList?.get(listPosition)!!
    }


    override fun getChild(listPosition: Int, expandedListPosition: Int): Int? {
        return 25
     //   return this.dataList?.get(listPosition)?.values?.get(expandedListPosition)
    }


    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.imagelist_item, null)
        }
        val resultsTV = convertView!!.findViewById<TextView>(R.id.resultsTV)
        val infoTitleTV = convertView!!.findViewById<TextView>(R.id.infoTitleTV)
        val ivStatus = convertView!!.findViewById<ImageView>(R.id.ivStatus)
        infoTitleTV.setTypeface(null, Typeface.BOLD)
        resultsTV.setTypeface(null, Typeface.NORMAL)
        /*resultsTV.text = listTitle?.value
        if (listTitle?.status!! == 0) {
            resultsTV.setTextColor(context.getColor(R.color.confirm_red))
            ivStatus.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.red_circleee
                )
            )
        } else if (listTitle?.status!! == 1) {
            resultsTV.setTextColor(context.getColor(com.regula.common.R.color.reg_green_ok))
            ivStatus.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.check_tick))
        }*/
//        infoTitleTV.text = listTitle?.getFieldName(context)
        infoTitleTV.visibility = View.VISIBLE
        return convertView
    }

    @SuppressLint("ServiceCast")
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val data = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.imagelist_item, null)
        }
        val resultsTV = convertView!!.findViewById<TextView>(R.id.resultsTV)
        val infoTitleTV = convertView!!.findViewById<TextView>(R.id.infoTitleTV)
       /* val ivStatus = convertView!!.findViewById<ImageView>(R.id.ivStatus)
        resultsTV.text = data?.field?.value
        infoTitleTV.text = data?.value
        if (data?.field?.validityStatus!! == 0) {
            resultsTV.setTextColor(context.getColor(R.color.confirm_red))
            ivStatus.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.red_circleee
                )
            )
        } else if (data?.field?.validityStatus!! == 1) {
            resultsTV.setTextColor(context.getColor(com.regula.common.R.color.reg_green_ok))
            ivStatus.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.check_tick))
        }*/
        return convertView
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}