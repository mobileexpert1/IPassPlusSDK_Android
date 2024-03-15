package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipassplus.R
import com.ipassplus.adapter.FullProcessExpandableListAdapter
import com.ipassplus.databinding.FragmentFullInformationBinding
import com.ipassplus.utils.ExpandableData
import com.regula.documentreader.api.enums.LCID
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults

class FullInformationFragment(var results: DocumentReaderResults) : Fragment() {

    lateinit var binding: FragmentFullInformationBinding
    private var adapter: FullProcessExpandableListAdapter? = null
    private var titleList: List<ExpandableData>? = null
    private var givenName: String = "----"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_full_information, container, false)
        binding = FragmentFullInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (results.textResult != null) {
//            val listData = ExpandableListData.fullInfo
//            val listData = getInfoList()
        val dataList = results.textResult?.fields
            for (data in dataList!!) {
                if (data.getFieldName(requireContext()).equals("Surname and given names")) givenName = data.value!!
            }
            adapter = FullProcessExpandableListAdapter(requireContext(), dataList)
            binding.expandableListFull.setAdapter(adapter)
            binding.expandableListFull.setOnGroupExpandListener { groupPosition ->
            }
            binding.expandableListFull.setOnGroupCollapseListener { groupPosition ->
            }
            binding.expandableListFull.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                false
            }
        }
        setDetails()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setDetails() {
//        Log.e("!!!!!!!!!!", results.getTextFieldValueByType(eVisualFieldType.FT_E_ID_PLACE_OF_BIRTH_COUNTRY)!!)

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES).isNullOrEmpty()) {
            binding.nameUserTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES))
        } else binding.nameUserTV.setText(givenName)

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SEX).isNullOrEmpty())
            binding.tvGender.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SEX))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_AGE).isNullOrEmpty())
            binding.ageTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_AGE))

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

        if (results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_CLASS_CODE) != null) {
            val i1 = ExpandableData()
            i1.title = "Document class code"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_CLASS_CODE)!!)
            i1.childView=myData
            i1.answer = results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_CLASS_CODE)!!
            list.add(i1)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_CODE) != null) {
            val i2 = ExpandableData()
            i2.title = "Issuing state code"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_CODE)!!)
            i2.childView=myData
            i2.answer = results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_CODE)!!
            list.add(i2)
        }

        if (results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_NUMBER) != null) {
            val i3 = ExpandableData()
            i3.title = "Document number"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_NUMBER)!!)
            i3.childView=myData
            i3.answer = results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_NUMBER)!!
            list.add(i3)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY) != null) {
            val i4 = ExpandableData()
            i4.title = "Date of expiry"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY)!!)
            i4.childView=myData
            i4.answer = results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY)!!
            list.add(i4)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_BIRTH) != null) {
            val i5 = ExpandableData()
            i5.title = "Date of birth"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_BIRTH)!!)
            i5.childView=myData
            i5.answer = results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_BIRTH)!!
            list.add(i5)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_PERSONAL_NUMBER) != null) {
            val i6 = ExpandableData()
            i6.title = "Personal number"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_PERSONAL_NUMBER)!!)
            i6.childView=myData
            i6.answer = results.getTextFieldValueByType(eVisualFieldType.FT_PERSONAL_NUMBER)!!
            list.add(i6)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME) != null) {
            val i7 = ExpandableData()
            i7.title = "Surname"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME)!!)
            i7.childView=myData
            i7.answer = results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME)!!
            list.add(i7)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_GIVEN_NAMES) != null) {
            val i8 = ExpandableData()
            i8.title = "Given name"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_GIVEN_NAMES)!!)
            i8.childView=myData
            i8.answer = results.getTextFieldValueByType(eVisualFieldType.FT_GIVEN_NAMES)!!
            list.add(i8)

        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY) != null) {
            val i9 = ExpandableData()
            i9.title = "Nationality"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY)!!)
            i9.childView=myData
            i9.answer = results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY)!!
            list.add(i9)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_SEX) != null) {
            val i10 = ExpandableData()
            i10.title = "Sex"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_SEX)!!)
            i10.childView=myData
            i10.answer = results.getTextFieldValueByType(eVisualFieldType.FT_SEX)!!
            list.add(i10)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES) != null) {
            val i11 = ExpandableData()
            i11.title = "Surname and given names"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES)!!)
            i11.childView=myData
            i11.answer = results.getTextFieldValueByType(eVisualFieldType.FT_AKA_SURNAME_AND_GIVEN_NAMES)!!
            list.add(i11)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY_CODE) != null) {
            val i12 = ExpandableData()
            i12.title = "Nationality code"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY_CODE)!!)
            i12.childView=myData
            i12.answer = results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY_CODE)!!
            list.add(i12)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_TYPE) != null) {
            val i13 = ExpandableData()
            i13.title = "MRZ Type"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_TYPE)!!)
            i13.childView=myData
            i13.answer = results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_TYPE)!!
            list.add(i13)
        }

        if (results.getTextFieldValueByType(eVisualFieldType.FT_OPTIONAL_DATA) != null) {
            val i14 = ExpandableData()
            i14.title = "Optional data"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_OPTIONAL_DATA)!!)
            i14.childView=myData
            i14.answer = results.getTextFieldValueByType(eVisualFieldType.FT_OPTIONAL_DATA)!!
            list.add(i14)
        }

        if (results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_NAME)!=null){
            val i15 = ExpandableData()
            i15.title = "issuing state"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_NAME)!!)
            i15.childView=myData
            i15.answer = results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_NAME)!!
            list.add(i15)
        }
        if (results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_STRINGS)!=null){
            val i16 = ExpandableData()
            i16.title = "MRZ lines"
            val myData: MutableList<String> = ArrayList()
            myData.add(results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_STRINGS)!!)
            i16.childView=myData
            i16.answer = results.getTextFieldValueByType(eVisualFieldType.FT_MRZ_STRINGS)!!
            list.add(i16)
        }
        return list
    }

}