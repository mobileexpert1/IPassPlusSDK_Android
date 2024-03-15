package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipassplus.R
import com.ipassplus.adapter.BankProcessExpandableListAdapter
import com.ipassplus.databinding.FragmentBandInfoBinding
import com.ipassplus.utils.ExpandableData
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults

class BandInfoFragment(var results: DocumentReaderResults) : Fragment() {
    lateinit var binding: FragmentBandInfoBinding
    private var adapter: BankProcessExpandableListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_band_info, container, false)
        binding = FragmentBandInfoBinding.inflate(inflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (binding.expandableListBank != null) {
            adapter = BankProcessExpandableListAdapter(requireContext(), getTitleList())
            binding.expandableListBank.setAdapter(adapter)
            binding.expandableListBank.setOnGroupExpandListener { groupPosition ->
            }
            binding.expandableListBank.setOnGroupCollapseListener { groupPosition ->
            }
            binding.expandableListBank.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                false
            }
        }
        setDetails()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setDetails() {
        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME).isNullOrEmpty())
            binding.nameUserTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME))

        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)!=null){
            val graphicFieldImage = results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)
            if (graphicFieldImage != null) {
                // If it's a Bitmap, convert it to Drawable first
                val bitmapDrawable = if (graphicFieldImage is Bitmap) {
                    BitmapDrawable(resources, graphicFieldImage)
                } else {
                    graphicFieldImage as Drawable?
                }

                // Set the Drawable on the ImageView
                binding.bioProfile.setImageDrawable(bitmapDrawable)
            }
        }
    }

    private fun getTitleList(): MutableList<ExpandableData> {
        val list = mutableListOf<ExpandableData>()

        if (results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES) != null) {
            val i1 = ExpandableData()
            i1.title = "Surname and gives names"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES)!!)
            i1.childView=myData
            i1.answer = results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES)!!
            list.add(i1)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER) != null) {
            val i2 = ExpandableData()
            i2.title = "Bank card number"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER)!!)
            i2.childView=myData
            i2.answer = results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER)!!
            list.add(i2)
        }

        if (results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU) != null) {
            val i3 = ExpandableData()
            i3.title = "Bank card validity"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU)!!)
            i3.childView=myData
            i3.answer = results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU)!!
            list.add(i3)
        }

        if (results.getTextFieldValueByType(eVisualFieldType.FT_CATEGORY) != null) {
            val i3 = ExpandableData()
            i3.title = "Category"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_CATEGORY)!!)
            i3.childView=myData
            i3.answer = results.getTextFieldValueByType(eVisualFieldType.FT_CATEGORY)!!
            list.add(i3)
        }

        return list
    }
}