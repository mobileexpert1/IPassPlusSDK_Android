package com.app.ipassplus.ui.dashboard.fragment

import ScenariosListAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ipassplus.MainActivity
import com.app.ipassplus.R
import com.app.ipassplus.ui.dashboard.model.ScenariosItemModel
import com.app.ipassplus.databinding.FragmentDashboardBinding
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.core.IPassSDK
import com.sdk.ipassplussdk.model.response.document_scanner.DocumentScannerResponse
import com.sdk.ipassplussdk.model.response.liveness_facesimilarity.FaceScannerResponse

class DashboardFragment : Fragment(), ScenariosListAdapter.OnClickListener {

    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private lateinit var adapter: ScenariosListAdapter
    private val email = "ipassmobsdk@yopmail.com"
//    private val email = "mabusanimeh@access2arabia.com"
    private val password = "Admin@123#"
    private val apptoken = "eyJhbGciOiJIUzI1NiJ9.aXBhc3Ntb2JzZGtAeW9wbWFpbC5jb21pcGFzcyBpcGFzcyAgIDcxNWFkYTI4LWFmODEtNGM5MC1iY2IyLTJmZjc1Mjg1YzhkYg.OfsTPtj41geOVQ9riQdTpCVEgWqfoqfpva93xez2xJk"
//    private val apptoken = "eyJhbGciOiJIUzI1NiJ9.aXBhc3Ntb2JpbGVAeW9wbWFpbC5jb21pcGFzcyBpcGFzcyAgIGE3YzI0NWNkLWFlMjItNDQ5OS1iNWE4LWJjZWE5MDQ5NWMyYQ.Gz7KzdzDR2_AD1yIuWYxIn_-6OMZngmZxEVtVCcNYJk"
//    private val apptoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYzODg4MWYyYzNmMDFmMTg5OTNlMWI4IiwiZW1haWwiOiJtYWJ1c2FuaW1laEBhY2Nlc3MyYXJhYmlhLmNvbSIsImlhdCI6MTcxNTE2MDU1MywiZXhwIjoxNzE1MTYyMzUzfQ.Jyp8s_c3oc2grx2_Xip8yMTIU3_TZCctbEXnsyAMKLw"
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scenarioList = arrayListOf(
            ScenariosItemModel(R.drawable.full2,"Full Processing",getString(R.string.processing_scenario_for_obtaining_all_document_data),true),
            ScenariosItemModel(R.drawable.bankkk, "Bank Card",getString(R.string.procesing_scenario_for_obtaning_bank_card_data),false),
            ScenariosItemModel(R.drawable.mrz, "MRZ",getString(R.string.procesing_scenario_for_obtaning_mrz_n_data),false),
            ScenariosItemModel(R.drawable.barcode, "Barcode",getString(R.string.procesing_scenario_for_obtaning_barcode_ndata),false),
            ScenariosItemModel(R.drawable.visual, "Visual OCR",getString(R.string.procesing_scenario_for_obtaning_nvisaul_zone_ocr_results),false)
        )

        adapter = ScenariosListAdapter(scenarioList,requireContext())
        adapter.setOnClickListener(this)

        binding.rvitems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@DashboardFragment.adapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onScenarioPickClick(position: Int, model: ScenariosItemModel) {
        IPassSDK.showScannerRequest(requireContext(),email, MainActivity.authToken, apptoken, binding.root as ViewGroup) {
            status, message ->
            if (status) {
                Log.e("showScannerRequest", message)
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                getDocData()
            } else {
                Log.e("showScannerRequest", message)
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDocData() {
        IPassSDK.getDocumentScannerData(requireContext(), apptoken, object : ResultListener<DocumentScannerResponse> {
            override fun onSuccess(response: DocumentScannerResponse?) {
                if (response?.Apistatus!!) {
                    Log.e("onSuccess", response.Apimessage!!)
                    Log.e("onSuccess", response.data.toString())
                    getFaceData()
                } else {
                    Log.e("error", response.Apimessage!!)
                }
            }

            override fun onError(exception: String) {
                Log.e("onSuccess", exception)
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFaceData() {
        IPassSDK.getFaceScannerData(requireContext(), apptoken, object : ResultListener<FaceScannerResponse> {
            override fun onSuccess(response: FaceScannerResponse?) {
//                Log.e("onSuccess", response?.message!!)
//                Log.e("onSuccess", response.data.toString())
//                Toast.makeText(context, response?.message, Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: String) {
                Log.e("onSuccess", exception)
            }
        })
    }

}