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
import com.ipassplus.adapter.PortraitImagesAdapter
import com.ipassplus.databinding.FragmentComparisonPortraitBinding
import com.ipassplus.models.PortraitImageData
import com.ipassplus.utils.Attribute
import com.ipassplus.utils.GroupedAttributes
import com.ipassplus.utils.Helpers.Companion.getResultTypeTranslation
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults

class ComparisonPortraitFragment(var results: DocumentReaderResults) : Fragment() {

    lateinit var binding: FragmentComparisonPortraitBinding
    private var adapter: PortraitImagesAdapter? = null
    private var titleList: List<String>? = null
    var portraitImages: ArrayList<Bitmap>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initResults(): List<GroupedAttributes> {
        val pickerData = mutableListOf<GroupedAttributes>()
        val attributes = mutableListOf<Attribute>()

//        results.textResult?.fields?.forEach {
//            val name = it.getFieldName(this.requireContext())
//            for (value in it.values) {
//                val valid = getValidity(value.field.validityList, value.sourceType)
//                val item = Attribute(
//                    name!!,
//                    value.value,
//                    it.lcid,
//                    value.pageIndex,
//                    valid,
//                    value.sourceType
//                )
//                attributes.add(item)
//            }
//        }

        results.graphicResult?.fields?.forEach {
            val name = it.getFieldName(this.requireContext()) + " [${it.pageIndex}]"
            val image = it.bitmap
            val item = Attribute(name, "", source = it.sourceType, image = image)
            attributes.add(item)
        }

        val types = attributes.map { it.source }.toSet()
        for (type in types) {
            val typed = attributes.filter { it.source == type }.toMutableList()
            val group = GroupedAttributes(getResultTypeTranslation(type!!), typed)
            pickerData.add(group)
        }
        pickerData.sortBy { it.type }

        return pickerData
    }

    fun init() {

        setDetails()

        if (results.graphicResult != null) {
            val listData = PortraitImageData.data
            titleList = ArrayList(listData.keys)
            adapter = PortraitImagesAdapter(requireContext(), initResults())
            binding.expandableImageList!!.setAdapter(adapter)
            binding.expandableImageList!!.setOnGroupExpandListener { groupPosition ->

            }
            binding.expandableImageList!!.setOnGroupCollapseListener { groupPosition ->

            }
            binding.expandableImageList!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                false
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun setDetails() {
        portraitImages= arrayListOf()
        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null)
        portraitImages!!.add(results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)!!)

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME).isNullOrEmpty())
            binding.nameTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES))

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
                binding.cvProfile.setImageDrawable(bitmapDrawable)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comparison_portrait, container, false)
        binding = FragmentComparisonPortraitBinding.inflate(inflater)
        return binding.root
    }


}