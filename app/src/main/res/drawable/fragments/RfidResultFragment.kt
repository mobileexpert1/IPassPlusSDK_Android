package com.ipassplus.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.ipassplus.R
import com.ipassplus.databinding.FragmentRfidResultBinding
import com.regula.documentreader.api.enums.eCheckResult.CH_CHECK_ERROR
import com.regula.documentreader.api.enums.eCheckResult.CH_CHECK_OK
import com.regula.documentreader.api.enums.eCheckResult.CH_CHECK_WAS_NOT_DONE
import com.regula.documentreader.api.enums.eCheckResult.CheckResult
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults

class RfidResultFragment(var results: DocumentReaderResults) : Fragment() {

    val binding by lazy { FragmentRfidResultBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setData()

        return binding.root
    }

    private fun setData() {
        if (results.getTextFieldByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES) != null) {
            val name = results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES)
            binding.nameUserTV.setText(name)
        }
        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SEX).isNullOrEmpty() && !results.getTextFieldValueByType(eVisualFieldType.FT_AGE).isNullOrEmpty()) {
            val str = "${results.getTextFieldValueByType(eVisualFieldType.FT_SEX)}, ${results.getTextFieldValueByType(eVisualFieldType.FT_AGE)}"
            binding.tvGenderDetail.setText(str)
        }

        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null) {
            val graphicFieldImage = results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)
             binding.bioProfile.setImageBitmap(graphicFieldImage)
            }

        setRfidStatus(results.status.detailsRFID.aa, binding.ivAA)
        setRfidStatus(results.status.detailsRFID.bac, binding.ivBAC)
        setRfidStatus(results.status.detailsRFID.pa, binding.ivPA)
        setRfidStatus(results.status.detailsRFID.pace, binding.ivPACE)
        setRfidStatus(results.status.detailsRFID.ca, binding.ivCA)
        setRfidStatus(results.status.detailsRFID.ta, binding.ivTA)

    }

    private fun setRfidStatus(status: Int, view: ImageView) {
        when(status) {
            CH_CHECK_ERROR -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.red_circleee)) }
            CH_CHECK_OK -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.tick))  }
            CH_CHECK_WAS_NOT_DONE -> { view.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_horizontal_rule_24))  }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}