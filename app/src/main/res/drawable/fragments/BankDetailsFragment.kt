package com.ipassplus.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipassplus.R
import com.ipassplus.databinding.FragmentBankDetailsBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.results
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults


class BankDetailsFragment(var results: DocumentReaderResults) : Fragment() {

    lateinit var binding: FragmentBankDetailsBinding
    var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getString("results").toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank_details, container, false)
        binding = FragmentBankDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
    }

    private fun showData() {

        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE) != null) {
            val graphicFieldImage =
                results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE)
            if (graphicFieldImage != null) {
                // If it's a Bitmap, convert it to Drawable first
                val bitmapDrawable = if (graphicFieldImage is Bitmap) {
                    BitmapDrawable(resources, graphicFieldImage)
                } else {
                    graphicFieldImage as Drawable?
                }

                // Set the Drawable on the ImageView
                binding.bankImg.setImageDrawable(bitmapDrawable)
            }

        }

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_GIVEN_NAMES).isNullOrEmpty())
            binding.nameUserTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_GIVEN_NAMES))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER).isNullOrEmpty())
            binding.tvBankNumber.setText(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU).isNullOrEmpty())
            binding.tvBankValid.setText(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU))

    }

}