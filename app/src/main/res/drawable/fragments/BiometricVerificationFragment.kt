package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnGroupExpandListener
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.adapter.ExpandableImageQualityAdapter
import com.ipassplus.databinding.FragmentBiometricVerificationBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.facePercentage
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.livenessConfidence
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.enums.eCheckResult
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults


class BiometricVerificationFragment(var results: DocumentReaderResults) : BottomSheetDialogFragment() {

    private var adapter: ExpandableImageQualityAdapter? = null
    private var titleList: List<String>? = null
    private lateinit var pref: SharedPref

    lateinit var binding:FragmentBiometricVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_personal_detail, container, false)
        binding = FragmentBiometricVerificationBinding.inflate(inflater)

        pref = SharedPref(requireContext())

        if (results.textResult != null) {

            val listData = results.imageQuality
            for (i in listData) {
                for (j in i.imageQualityList) {
                    println("@@@@@@@@@@@ ${j.result}")
                    println(j.type)
                    println(j.featureType)
//                    println(j.result)
                }
//                println(i.imageQualityList)
            }
            adapter = ExpandableImageQualityAdapter(requireContext(), listData)
            binding.expandableList.setAdapter(adapter)
            /*binding.expandableList.setOnGroupExpandListener { groupPosition ->

            }*/
            binding.expandableList.setOnGroupExpandListener {
                var height = 0
                for (i in 0 until binding.expandableList.childCount) {
                    height += binding.expandableList.getChildAt(i).measuredHeight
                    height += binding.expandableList.dividerHeight
                }
                binding.expandableList.layoutParams.height = (height + 6) * results.imageQuality.get(0).imageQualityList.size +1
            }
            binding.expandableList.setOnGroupCollapseListener { groupPosition ->
                binding.expandableList.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            binding.expandableList.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun showData() {

        if (MainActivity.isFaceMatchingEnabled && pref.getBoolean(Constants.FACE_MATCHING)) {

            if (livenessConfidence!! < 80f) {
                binding.ivLiveness.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.red_circleee
                    )
                )
            } else
                binding.ivLiveness.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.tick
                    )
                )

            if (facePercentage!! < 80) {
                binding.ivFaceMatching.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.red_circleee
                    )
                )
            } else
                binding.ivFaceMatching.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.tick
                    )
                )
        }


        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME).isNullOrEmpty())
            binding.nameUserTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_AGE).isNullOrEmpty())
            binding.ageTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_AGE))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SEX).isNullOrEmpty())
            binding.tvGender.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SEX))

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
        setStatus(results.status.detailsOptical.docType, binding.ivDocumentType)
        setStatus(results.status.detailsOptical.text, binding.ivTextFields)
        if (results.authenticityResult != null)
            setStatus(results.authenticityResult?.status!!, binding.ivAuthenticity)
        setStatus(results.status.detailsRFID.aa, binding.ivAA)
        setStatus(results.status.detailsRFID.bac, binding.ivBAC)
        setStatus(results.status.detailsRFID.pa, binding.ivPA)
        setStatus(results.status.detailsRFID.pace, binding.ivPACE)
        setStatus(results.status.detailsRFID.ca, binding.ivCA)
        setStatus(results.status.detailsRFID.ta, binding.ivTA)
    }

    private fun setStatus(status: Int, view: ImageView) {
        when(status) {
            eCheckResult.CH_CHECK_ERROR -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.red_circleee)) }
            eCheckResult.CH_CHECK_OK -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.tick))  }
            eCheckResult.CH_CHECK_WAS_NOT_DONE -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_horizontal_rule_24))  }
        }
    }

}