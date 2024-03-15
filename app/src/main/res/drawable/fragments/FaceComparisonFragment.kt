package com.ipassplus.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.databinding.FragmentFaceComparisonBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.facePercentage
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.livenessConfidence
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.referenceImage
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.enums.eGraphicFieldType


class FaceComparisonFragment : Fragment() {

    val binding: FragmentFaceComparisonBinding by lazy { FragmentFaceComparisonBinding.inflate(layoutInflater) }
    private lateinit var pref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPref(requireContext())
        val numm:Float = String.format("%.2f", livenessConfidence).toFloat()
        val numm2:Float = String.format("%.2f", facePercentage).toFloat()
        binding.tvFaceSimilarity.setText("Face Match Similarity : $numm2%")
        binding.tvFaceLiveness.setText("Results Confidence : $numm%")

        if (MainScreenViewpagerFragment.results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null) {
            val graphicFieldImage =
                MainScreenViewpagerFragment.results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)
            if (graphicFieldImage != null) {
                // If it's a Bitmap, convert it to Drawable first
                val bitmapDrawable = if (graphicFieldImage is Bitmap) {
                    BitmapDrawable(resources, graphicFieldImage)
                } else {
                    graphicFieldImage as Drawable?
                }

                // Set the Drawable on the ImageView
                binding.ivDoc.setImageDrawable(bitmapDrawable)
            }

        }

        if (MainActivity.isFaceMatchingEnabled && pref.getBoolean(Constants.FACE_MATCHING)) {

            if (!referenceImage.equals("")) {
                val imageBytes = Base64.decode(referenceImage, 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.ivReference.setImageBitmap(image)
            }

            if (facePercentage!! > 80) {
                binding.ivComparision1.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.tick
                    )
                )
                binding.ivComparision2.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.tick
                    )
                )
            } else {
                binding.ivComparision1.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.circle_cross
                    )
                )
                binding.ivComparision2.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.circle_cross
                    )
                )
            }
        }
    }
}