package com.app.ipassplus.ui.dashboard.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentFaceLivenessBinding
import com.google.gson.Gson
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.core.IPassSDK
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.session_create.FaceSessionCreateResponse

class FaceLivenessFragment : Fragment() {
    private val binding by lazy { FragmentFaceLivenessBinding.inflate(layoutInflater)}

    var token: String = "eyJhbGciOiJIUzI1NiJ9.YW5raXQxMjNAZ21haWwuY29tYW5raXQga3VtYXI.I-URE1Ft5LtpEK-G7Mr5EV8OR6-ozT1yUpkvuoHWyTs"
    var token1: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjVmODQ2MmIzMDg5MjM3MTZkY2ZiN2IwIiwiZW1haWwiOiJ5YXphbjEyQGdtYWlsLmNvbSIsImlhdCI6MTcxMDgzNDg3NCwiZXhwIjoxNzEwODM2Njc0fQ.CtLY1Ub-s8j1f03F5koQRxAHkt6nD1u8u56dbAxe1CE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

//            IPassSDK.ConfigureAws(requireContext())
            //IPassSDK.FaceDetector(requireContext())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvGobtn.setOnClickListener(View.OnClickListener {
            sessionCreateRequest()
//            initFaceDetector(sessionId)
        })
        binding.ibCross.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.scenarioResultFragment)
        })
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sessionCreateRequest() {

        var req = SessionCreateRequest()
        req.authToken = ""
        req.email = "yazann123@gmail.com"

//        IPassSDK.faceSessionCreateRequest(requireContext(),token, req, object :
//            ResultListener<FaceSessionCreateResponse> {
//            override fun onSuccess(t: FaceSessionCreateResponse?) {
//                print(t)
//                Log.e("call","FaceSessionCreate Response:: "+t.toString())
//
//                val jsonString = Gson().toJson(t)
//                // Log the JSON string
//                Log.d("JSON String::::::  ", jsonString)
//
//                Toast.makeText(requireContext(), "sessionCreated::  "+t.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(requireContext(), exception, Toast.LENGTH_LONG).show()
//            }
//        })
    }
}