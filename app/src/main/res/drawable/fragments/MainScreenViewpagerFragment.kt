package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.activity.MainActivity.Companion.isFaceMatchingEnabled
import com.ipassplus.activity.MainActivity.Companion.sessionId
import com.ipassplus.adapter.VerificationAdapter
import com.ipassplus.databinding.FragmentMainScreenViewpagerBinding
import com.ipassplus.models.TestReq
import com.ipassplus.models.response.FaceMatchingResponse
import com.ipassplus.models.response.newr.FaceLivenessResponse
import com.ipassplus.retrofit.ApiClient
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.completions.rfid.IRfidReaderCompletion
import com.regula.documentreader.api.enums.eGraphicFieldType
import com.regula.documentreader.api.enums.eVisualFieldType
import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.results.DocumentReaderResults
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.Calendar


class MainScreenViewpagerFragment : Fragment(), View.OnClickListener {

    val binding by lazy { FragmentMainScreenViewpagerBinding.inflate(layoutInflater) }
    private lateinit var view_pager: ViewPager
    private lateinit var indicator: DotsIndicator
    lateinit var adapter: VerificationAdapter
    val dataMap = HashMap<String, String>()
    private lateinit var pref: SharedPref

    companion object {
        lateinit var results: DocumentReaderResults

        var livenessConfidence: Float? = 0f
        var referenceImage: String? = ""
        var facePercentage: Float? = 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private fun getLivenessResults() {
        binding.pbMain.visibility = View.VISIBLE
        val call = ApiClient.apiService.getSessionResult(sessionId)

        call.enqueue(object : Callback<FaceLivenessResponse> {
            override fun onResponse(
                call: Call<FaceLivenessResponse>,
                response: Response<FaceLivenessResponse>
            ) {
                if (response.isSuccessful) {
                    val post = response.body()
                    livenessConfidence = post?.response?.Confidence
                    referenceImage = post?.response?.ReferenceImage?.Bytes
                    Log.e("livenessConfidence", livenessConfidence.toString())
                    faceMatchingRequest()
                } else {
                    binding.pbMain.visibility = View.GONE
                    Log.e("!!!!!!!!!!!", response.message())
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                    setDetails()

                    adapter = VerificationAdapter(childFragmentManager)
                    adapter.addFragment(PersonalDetailFragment(results))
                    adapter.addFragment(FullInformationFragment(results))
                    adapter.addFragment(ComparisonPortraitFragment(results))
                    adapter.addFragment(FaceComparisonFragment())
                    adapter.addFragment(BiometricVerificationFragment(results))
                    view_pager.adapter = adapter
                    indicator.setViewPager(view_pager)
                    // Handle error
                }
            }

            override fun onFailure(call: Call<FaceLivenessResponse>, t: Throwable) {
                // Handle failure
                binding.pbMain.visibility = View.GONE
                Log.e("!!!!!!!!!!!", t.message.toString())
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                setDetails()
                adapter = VerificationAdapter(childFragmentManager)
                adapter.addFragment(PersonalDetailFragment(results))
                adapter.addFragment(FullInformationFragment(results))
                adapter.addFragment(ComparisonPortraitFragment(results))
                adapter.addFragment(FaceComparisonFragment())
                adapter.addFragment(BiometricVerificationFragment(results))
                view_pager.adapter = adapter
                indicator.setViewPager(view_pager)
            }
        })
    }

    @SuppressLint("MissingInflatedId", "WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_screen_viewpager, container, false)

        pref = SharedPref(requireContext())

        if (pref.getBoolean(Constants.FACE_MATCHING)) binding.ivCheckFaceLiveness.visibility = View.VISIBLE
        view_pager = binding.viewPager
        indicator = binding.dotsIndicator
        val ivNfc = binding.ivNfc
        ivNfc.setOnClickListener(this)
        binding.ivCheckFaceLiveness.setOnClickListener(this)
        binding.ivOverallStatus.setOnClickListener(this)
        binding.ivFaceLiveness.setOnClickListener(this)
        binding.ivFaceMatching.setOnClickListener(this)

        if (isFaceMatchingEnabled && pref.getBoolean(Constants.FACE_MATCHING)) {
            getLivenessResults()
        } else {
            setDetails()
            adapter = VerificationAdapter(childFragmentManager)
            adapter.addFragment(PersonalDetailFragment(results))
            adapter.addFragment(FullInformationFragment(results))
            adapter.addFragment(ComparisonPortraitFragment(results))
            adapter.addFragment(BiometricVerificationFragment(results))
            view_pager.adapter = adapter
            indicator.setViewPager(view_pager)
        }

//        adapter = VerificationAdapter(childFragmentManager)
//        adapter.addFragment(PersonalDetailFragment(results))
//        adapter.addFragment(FullInformationFragment(results))
//        adapter.addFragment(ComparisonPortraitFragment(results))
//        adapter.addFragment(BiometricVerificationFragment(results))
//        view_pager.adapter = adapter
//        indicator.setViewPager(view_pager)

//        setDetails()





//        val manager = requireContext().getSystemService(Context.NFC_SERVICE) as NfcManager
//        val adapter = manager.defaultAdapter
//        if (adapter != null && adapter.isEnabled) {
//            ivNfc.visibility = View.VISIBLE
//            //Yes NFC available
//        } else if (adapter != null && !adapter.isEnabled) {
//            ivNfc.visibility = View.GONE
//            //NFC is not enabled.Need to enable by the user.
//        } else {
//            ivNfc.visibility = View.GONE
//            //NFC is not supported
//        }

        if (MainActivity.supportsRfid) {
            ivNfc.visibility = View.VISIBLE
        } else ivNfc.visibility = View.GONE


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    //do what you want here
                    findNavController().popBackStack(R.id.dashboardTempFragment, false)

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    private fun faceMatchingRequest() {
        val faceMatchingRequest = TestReq()
        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null) {
            val encoded: String = encodeTobase64(results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT)!!)!!
            faceMatchingRequest.sourceImageBase64 = encoded
        }
        faceMatchingRequest.targetImageBase64 = referenceImage!!
        val faceMatchingCall = ApiClient.apiService.faceMatching(faceMatchingRequest)

        faceMatchingCall.enqueue(object : Callback<FaceMatchingResponse> {
            override fun onResponse(
                call: Call<FaceMatchingResponse>,
                response: Response<FaceMatchingResponse>
            ) {
                if (response.isSuccessful) {
                    binding.pbMain.visibility = View.GONE
                    var response = response.body()
                    var facePercentage = response?.data?.facePercentage

//                    Toast.makeText(context,""+facePercentage.toString(),Toast.LENGTH_SHORT).show()
                    Companion.facePercentage =facePercentage

                    Log.e("facePercentage@@@", "" + facePercentage)
                    setDetails()
                    adapter = VerificationAdapter(childFragmentManager)
                    adapter.addFragment(PersonalDetailFragment(results))
                    adapter.addFragment(FullInformationFragment(results))
                    adapter.addFragment(ComparisonPortraitFragment(results))
                    adapter.addFragment(FaceComparisonFragment())
                    adapter.addFragment(BiometricVerificationFragment(results))
                    view_pager.adapter = adapter
                    indicator.setViewPager(view_pager)

                } else {
                    binding.pbMain.visibility = View.GONE
                    Log.e("FailureMEssage@@", "" + response.message())
                    setDetails()
                    adapter = VerificationAdapter(childFragmentManager)
                    adapter.addFragment(PersonalDetailFragment(results))
                    adapter.addFragment(FullInformationFragment(results))
                    adapter.addFragment(ComparisonPortraitFragment(results))
                    adapter.addFragment(FaceComparisonFragment())
                    adapter.addFragment(BiometricVerificationFragment(results))
                    view_pager.adapter = adapter
                    indicator.setViewPager(view_pager)
                }
            }

            override fun onFailure(call: Call<FaceMatchingResponse>, t: Throwable) {
                binding.pbMain.visibility = View.GONE
                Log.e("FaceMatchingResponse!!!!!!!!!!!", t.message.toString())
                setDetails()
                adapter = VerificationAdapter(childFragmentManager)
                adapter.addFragment(PersonalDetailFragment(results))
                adapter.addFragment(FullInformationFragment(results))
                adapter.addFragment(ComparisonPortraitFragment(results))
                adapter.addFragment(FaceComparisonFragment())
                adapter.addFragment(BiometricVerificationFragment(results))
                view_pager.adapter = adapter
                indicator.setViewPager(view_pager)
            }

        })
    }

    fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

    private fun setDetails() {

        if (isFaceMatchingEnabled && pref.getBoolean(Constants.FACE_MATCHING)) {
            if (livenessConfidence!! > 80) {
                binding.ivFaceLiveness.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_liveness_true
                    )
                )
            } else binding.ivFaceLiveness.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_face_liveness_false
                )
            )
            if (facePercentage!! > 80) {
                binding.ivFaceMatching.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_matching_true
                    )
                )
            } else binding.ivFaceMatching.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_face_matching_false
                )
            )
        } else {
            binding.ivFaceMatching.visibility = View.GONE
            binding.ivFaceLiveness.visibility = View.GONE
        }


        if (results.textResult != null && results.textResult?.fields?.size!! != 0 ) {
            binding.ivOverallStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.tick))
        } else  binding.ivOverallStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.circle_cross))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY).isNullOrEmpty()) {
            val currentDate = Calendar.getInstance().time

            try {
                val sdf = SimpleDateFormat("dd/MM/yy")
                val strDate = sdf.parse(results.getTextFieldValueByType(eVisualFieldType.FT_DATE_OF_EXPIRY))
                if (currentDate.after(strDate)) {
                    binding.timer.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.vector3x))
                    binding.timer.visibility = View.VISIBLE
                } else {
                    binding.timer.visibility = View.GONE
                }
            } catch (e: Exception) {
                binding.timer.visibility = View.GONE
            }

        }

        if (results?.getTextFieldByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES) != null) {
            val name = results.getTextFieldValueByType(eVisualFieldType.FT_SURNAME_AND_GIVEN_NAMES)
            Log.e("name@@", name!!)
            binding.nameUserTV.setText(name)
        } //else binding.nameUserTV.setText(givenName)


        if (!results.getTextFieldValueByType(eVisualFieldType.FT_AGE).isNullOrEmpty())
            binding.ageTV.setText(results.getTextFieldValueByType(eVisualFieldType.FT_AGE))

        if (!results.getTextFieldValueByType(eVisualFieldType.FT_SEX).isNullOrEmpty())
            binding.tvGenderDetail.setText(results.getTextFieldValueByType(eVisualFieldType.FT_SEX))


        if (results.getGraphicFieldImageByType(eGraphicFieldType.GF_PORTRAIT) != null) {
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivOverallStatus -> {
                binding.viewPager.currentItem = 1
            }
            R.id.ivFaceLiveness -> {
//                binding.viewPager.currentItem = 3
            }
            R.id.ivFaceMatching -> {
//                binding.viewPager.currentItem = 3
            }
            R.id.ivCheckFaceLiveness -> {
                isFaceMatchingEnabled = true
                findNavController().navigate(R.id.selfieTimeFragment)
            }
            R.id.ivNfc -> {
                DocumentReader.Instance()
                    .startRFIDReader(this.requireContext(), object : IRfidReaderCompletion() {
                        override fun onCompleted(
                            rfidAction: Int,
                            documentReaderResults: DocumentReaderResults?,
                            e: DocumentReaderException?
                        ) {
                            results = documentReaderResults!!
                            adapter = VerificationAdapter(childFragmentManager)
                            adapter.addFragment(PersonalDetailFragment(results))
                            adapter.addFragment(FullInformationFragment(results))
                            adapter.addFragment(ComparisonPortraitFragment(results))
                            adapter.addFragment(RfidResultFragment(results))
                            adapter.addFragment(BiometricVerificationFragment(results))
                            view_pager.adapter = adapter
                            indicator.setViewPager(view_pager)
                        }
                    })
            }
        }
    }

}