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
import com.ipassplus.adapter.EgyptCardAdapter
import com.ipassplus.databinding.FragmentBankImageBinding
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults


class BankImageFragment(var results: DocumentReaderResults) : Fragment() {
   lateinit var binding:FragmentBankImageBinding


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
        val view = inflater.inflate(R.layout.fragment_bank_image, container, false)
        binding = FragmentBankImageBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDetails()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setDetails() {

        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE)!=null){
            val graphicFieldImage = results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE)
            if (graphicFieldImage != null) {
                // If it's a Bitmap, convert it to Drawable first
                val bitmapDrawable = if (graphicFieldImage is Bitmap) {
                    BitmapDrawable(resources, graphicFieldImage)
                } else {
                    graphicFieldImage as Drawable?
                }

                // Set the Drawable on the ImageView
                binding.documentImg.setImageDrawable(bitmapDrawable)
            }

        }



    }

}