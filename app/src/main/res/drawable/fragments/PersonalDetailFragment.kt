package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.activity.MainActivity.Companion.sessionId
import com.ipassplus.adapter.EgyptCardAdapter
import com.ipassplus.databinding.FragmentPersonalDetailBinding
import com.ipassplus.models.response.LivenessResult
import com.ipassplus.models.response.SessionIdResponse
import com.ipassplus.models.response.SessionResult
import com.ipassplus.retrofit.ApiClient
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.results.DocumentReaderResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar


class PersonalDetailFragment(val results: DocumentReaderResults) : Fragment() {

    private lateinit var adapter: EgyptCardAdapter
    private lateinit var binding: FragmentPersonalDetailBinding
    var index:Int?=null
    var fieldValue:String?=null
    var fieldName:String?=null
    var portraitImages: ArrayList<Bitmap>? = null
    private var givenName = "----"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivTest.setOnClickListener{
//            setBitmap()
//        }

        setDetails()

    }

//    private fun setBitmap() {
//        val call = ApiClient.apiService.getSessionResult(sessionId)
//
//        call.enqueue(object : Callback<LivenessResult> {
//            override fun onResponse(call: Call<LivenessResult>, response: Response<LivenessResult>) {
//                if (response.isSuccessful) {
//                    val post = response.body()
//                    Log.e("!!!!!!!!!!!", post?.response?.Status!!)
////                    val image = byteArrayToBitmap(post.response?.ReferenceImage?.Bytes!!)
////                    binding.ivTest.setImageBitmap(image)
////                    initFaceDetector(post.data?.sessionId!!)
//                    // Handle the retrieved post data
//                } else {
//                    Log.e("!!!!!!!!!!!", response.message())
//                    // Handle error
//                }
//            }
//            override fun onFailure(call: Call<LivenessResult>, t: Throwable) {
//                // Handle failure
//                Log.e("!!!!!!!!!!!", t.message.toString())
//            }
//        })
//    }
//    private fun setBitmap() {
//        val call = ApiClient.apiService.getSessionResult(sessionId)
//
//        call.enqueue(object : Callback<SessionResult> {
//            override fun onResponse(call: Call<SessionResult>, response: Response<SessionResult>) {
//                if (response.isSuccessful) {
//                    val post = response.body()
//                    Log.e("!!!!!!!!!!!", post?.response?.Status!!)
//                    val image = byteArrayToBitmap(post.response?.ReferenceImage?.Bytes!!)
//                    binding.ivTest.setImageBitmap(image)
////                    initFaceDetector(post.data?.sessionId!!)
//                    // Handle the retrieved post data
//                } else {
//                    Log.e("!!!!!!!!!!!", response.message())
//                    // Handle error
//                }
//            }
//            override fun onFailure(call: Call<SessionResult>, t: Throwable) {
//                // Handle failure
//                Log.e("!!!!!!!!!!!", t.message.toString())
//            }
//        })
//    }

    @SuppressLint("SuspiciousIndentation")
    private fun setDetails() {

        if (results.textResult == null) {
//            binding.rlDetails.visibility = View.GONE
//            binding.PersonalLL.visibility = View.INVISIBLE
        }else {
            with(results.textResult) {
                for (i in this!!.fields.indices) {
                    index = i
                    fieldName = fields[i].getFieldName(requireContext())
                    Log.e("fieldName###", fieldName.toString()+"==="+fields[i].value)
                    fieldValue = fields[i].value
                    if (fieldName.equals("Surname and given names")) givenName = fieldValue!!
//                if (fieldName=="Surname and given names"){
//                    if (fieldValue != null) {
//                        Log.e("fiedlValue\$", fieldValue!!)
//                       // binding.nameUserTV.setText(fieldValue)
//                    }
//                }
//                if (fieldName=="Sex"){
//                    if (fieldValue != null) {
//                        Log.e("fiedlValue\$", fieldValue!!)
//                        binding.tvGenderDetail.setText(fieldValue)
//                    }
//                }
//
//                if (fieldName=="Age"){
//                    if (fieldValue != null) {
//                        Log.e("fiedlValue\$", fieldValue!!)
//                        binding.ageTV.setText(fieldValue)
//                    }
//                }
//
//                if (fieldName=="Date of birth"){
//                    if (fieldValue != null) {
//                        Log.e("fiedlValue\$", fieldValue!!)
//                        binding.tvDOBset.setText(fieldValue)
//                    }
//                }
//                if (fieldName=="Date of birth"){
//                    if (fieldValue != null) {
//                        Log.e("fiedlValue\$", fieldValue!!)
//                        binding.tvDocumentNumberSet.setText(fieldValue)
//                    }
//                }
//                if (fieldValue != null) {
//                    dataMap[fieldName!!] = fieldValue!!
//                }
                }
            }
        }

        if (MainActivity.typeOfData == 2) {
            binding.llBankDetails.visibility = View.VISIBLE

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_CATEGORY).isNullOrEmpty())
                binding.tvCategory.setText(results.getTextFieldValueByType(eVisualFieldType.FT_CATEGORY))
            if (!results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU).isNullOrEmpty())
                binding.tvBankCardValidity.setText(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDVALIDTHRU))
            if (!results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER).isNullOrEmpty())
                binding.tvBankCardNumber.setText(results.getTextFieldValueByType(eVisualFieldType.FT_BANKCARDNUMBER))

        } else {
            binding.rlDetails.visibility = View.VISIBLE

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_BIRTH).isNullOrEmpty())
                binding.tvDOBset.setText(results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_BIRTH))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_NUMBER).isNullOrEmpty())
                binding.tvDocumentNumberSet.setText(results.getTextFieldValueByType(eVisualFieldType.FT_DOCUMENT_NUMBER))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_NAME)
                    .isNullOrEmpty()
            )
                binding.tvAddressSet.setText(results.getTextFieldValueByType(eVisualFieldType.FT_ISSUING_STATE_NAME))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_AGE).isNullOrEmpty())
                binding.ageTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_AGE))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_PERSONAL_NUMBER).isNullOrEmpty())
                binding.tvPersonalNumberSet.setText(results.getTextFieldValueByType(eVisualFieldType.FT_PERSONAL_NUMBER))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_SEX).isNullOrEmpty())
                binding.tvGenderDetail.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SEX))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY).isNullOrEmpty())
                binding.tvNationalityset.setText(results.getTextFieldValueByType(eVisualFieldType.FT_NATIONALITY))

            if (!results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY).isNullOrEmpty())
                binding.tvDateOfExpirySet.setText(results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY))
        }

        if (results?.getTextFieldByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES) != null) {
            val name = results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES)
            Log.e("name@@", name!!)
            binding.nameUserTV.setText(name)
        } else binding.nameUserTV.setText(givenName)


        if (results.documentType.size >= 1) {
            if (!results.documentType.get(0).name.isNullOrEmpty()) {
                binding.tvEgypt.setText(results.documentType.get(0).name)
            }
        }

        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null) {
            val graphicFieldImage =
                results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)
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

            portraitImages = arrayListOf()
            portraitImages!!.add(results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)!!)

        }

        if (!MainScreenViewpagerFragment.results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY).isNullOrEmpty()) {
            val currentDate = Calendar.getInstance().time

            try {
                val sdf = SimpleDateFormat("dd/MM/yy")
                val strDate = sdf.parse(MainScreenViewpagerFragment.results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY))
                if (currentDate.after(strDate)) {
                    binding.tvDateOfExpirySet.setTextColor(context?.getColor(R.color.confirm_red)!!)
                } else {
                    binding.tvDateOfExpirySet.setTextColor(context?.getColor(com.regula.common.R.color.reg_green_ok)!!)
                }
            } catch (e: Exception) {
                binding.tvDateOfExpirySet.setTextColor(context?.getColor(R.color.black)!!)
            }

        }
        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE) != null)
            if (portraitImages != null)
                portraitImages!!.add(results.getGraphicFieldImageByType(eGraphicFieldType.GF_DOCUMENT_IMAGE)!!)
        adapter = EgyptCardAdapter(requireContext(), portraitImages!!)
        binding.imagesPager.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_personal_detail, container, false)
        binding = FragmentPersonalDetailBinding.inflate(inflater)

        portraitImages = arrayListOf()

        binding.imgNext.setOnClickListener {
            val currentItem = binding.imagesPager.currentItem
            if (currentItem < adapter.count - 1) {
                binding.imagesPager.setCurrentItem(currentItem + 1, true)
            }
        }

        binding.imgPrevious.setOnClickListener {
            val currentItem = binding.imagesPager.currentItem
            if (currentItem > 0) {
                binding.imagesPager.setCurrentItem(currentItem - 1, true)
            }
        }
        return binding.root
    }

}