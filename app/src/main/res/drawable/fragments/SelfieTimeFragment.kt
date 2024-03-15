package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.amplifyframework.ui.liveness.ui.FaceLivenessDetector
import com.amplifyframework.ui.liveness.ui.LivenessColorScheme
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.activity.MainActivity.Companion.isFaceMatchingEnabled
import com.ipassplus.activity.MainActivity.Companion.sessionId
import com.ipassplus.activity.SettingsActivity
import com.ipassplus.databinding.FragmentDashboardTempBinding
import com.ipassplus.databinding.FragmentSelfieTimeBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.results
import com.ipassplus.models.modelData.request.SessionRequest
import com.ipassplus.models.response.SessionIdResponse
import com.ipassplus.models.response.SessionLiveness
import com.ipassplus.retrofit.ApiClient
import com.regula.documentreader.api.internal.parser.DocReaderResultsJsonParser
import com.regula.documentreader.api.results.DocumentReaderResults
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SelfieTimeFragment : Fragment() {

    private val binding by lazy { FragmentSelfieTimeBinding.inflate(layoutInflater) }

    private val typeOfData = 1
    @Transient
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvGobtn.setOnClickListener(View.OnClickListener {
            getSessionId()
//            initFaceDetector(sessionId)
        })
        binding.ibCross.setOnClickListener(View.OnClickListener {
//            getSessionId()
            isFaceMatchingEnabled = false
            findNavController().navigate(R.id.mainScreenViewpagerFragment)
//            findNavController().popBackStack()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getSessionId()
    }

    private fun getSessionId() {
        val sessionRequest = SessionRequest()
        sessionRequest.S3Bucket = "facelivenessimage"
        sessionRequest.S3KeyPrefix = "livenessimages"
        sessionRequest.accessKeyId = "AKIATYNYEEOCH5HZ5X2E"
        sessionRequest.secretAccessKey = "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"

        binding.pbMain.visibility = View.VISIBLE
        val call = ApiClient.apiService.getSessionId(sessionRequest)

        call.enqueue(object : Callback<SessionLiveness> {
            override fun onResponse(call: Call<SessionLiveness>, response: Response<SessionLiveness>) {
                binding.pbMain.visibility = View.GONE
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("!!!!!!!!!!!", data?.sessionId!!)
                    sessionId = data.sessionId!!
                    isFaceMatchingEnabled = true
                    initFaceDetector(data.sessionId!!)
                } else {
                    Log.e("!!!!!!!!!!!", response.message())
                    isFaceMatchingEnabled = false
                }
            }
            override fun onFailure(call: Call<SessionLiveness>, t: Throwable) {
                binding.pbMain.visibility = View.GONE
                isFaceMatchingEnabled = false
                Log.e("!!!!!!!!!!!", t.message.toString())
            }
        })
    }

    private fun initFaceDetector(sessionId: String) {

        val composeView = ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(
                    colorScheme = LivenessColorScheme.default()
                ) {
                    FaceLivenessDetector(
                        sessionId = sessionId,
                        region = "us-east-1",
                        disableStartView = true,
                        onComplete = {
                            Log.i("MyApp", "Face Liveness flow is complete")
                            displayResults(results)
                            // The Face Liveness flow is complete and the session
                            // results are ready. Use your backend to retrieve the
                            // results for the Face Liveness session.


                        },
                        onError = { error ->
                            Toast.makeText(requireContext(), error.throwable?.message.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("MyApp", "Error during Face Liveness flow", error.throwable)
                            Log.e("MyApp", "Error during Face Liveness flow ${error.message}")
                            Log.e("MyApp", "Error during Face Liveness flow ${error.recoverySuggestion}")
                            findNavController().popBackStack()
                            // An error occurred during the Face Liveness flow, such as
                            // time out or missing the required permissions.
                        }
                    )
                }
            }
        }
//        binding.root.removeAllViews()
        (binding.root as ViewGroup).removeAllViewsInLayout()
        (binding.root as ViewGroup).addView(composeView)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun displayResults(documentReaderResults: DocumentReaderResults) {

//        if (documentReaderResults.textResult?.comparisonStatus == 0) {
//            Toast.makeText(requireContext(), "Please make sure rear image is of the same document", Toast.LENGTH_LONG).show()
////            return
//        }
//        if (documentReaderResults.textResult?.validityStatus == 0) {
//            Toast.makeText(requireContext(), "Image is blurry", Toast.LENGTH_SHORT).show()
//            return
//        }

        if (typeOfData!!.equals(1)){
            val fragment=MainScreenViewpagerFragment()
            var bundle=Bundle()
            MainScreenViewpagerFragment.results = documentReaderResults
            MainActivity.supportsRfid = MainScreenViewpagerFragment.results.chipPage != 0
//            if (MainScreenViewpagerFragment.results.documentType != null && MainScreenViewpagerFragment.results.documentType.size > 0)
//                MainActivity.supportsRfid = MainScreenViewpagerFragment.results.documentType.get(0).dMRZ
//            val jsonObject = JsonParser().parse(MainScreenViewpagerFragment.results.rawResult).asJsonObject
//            if (jsonObject.get("ChipPage").equals("0")) MainActivity.supportsRfid = false else true
//            val map = Gson().fromJson(jsonString, )
            if (SettingsActivity.isDataEncryptionEnabled) {
                val input = JSONObject(MainScreenViewpagerFragment.results.rawResult)
                val processParam = JSONObject()
                    .put("alreadyCropped", true)
                    .put("scenario", "FullProcess")
                val output = JSONObject()
                    .put("List", input.getJSONObject("ContainerList").getJSONArray("List"))
                    .put("processParam", processParam)
                postRequest(output.toString())
            } else
            //startActivity(Intent(this, ResultsActivity::class.java))

                bundle.putString("results", MainScreenViewpagerFragment.results.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.mainScreenViewpagerFragment)
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.fragment_container, fragment)/*?.addToBackStack(fragment.tag)*/
//                ?.commitAllowingStateLoss()
        }else if (typeOfData!!.equals(2)){
            val fragment=BandDetailsViewPagerFragment()
            var bundle=Bundle()
            MainScreenViewpagerFragment.results = documentReaderResults
            MainActivity.supportsRfid = MainScreenViewpagerFragment.results.chipPage != 0
//            MainScreenViewpagerFragment.results = documentReaderResults
//            if (MainScreenViewpagerFragment.results.documentType != null && MainScreenViewpagerFragment.results.documentType.size > 0)
//                MainActivity.supportsRfid = MainScreenViewpagerFragment.results.documentType.get(0).dMRZ
//            val jsonObject = JsonParser().parse(MainScreenViewpagerFragment.results.rawResult).asJsonObject
//            if (jsonObject.get("ChipPage").equals("0")) MainActivity.supportsRfid = false else true
            if (SettingsActivity.isDataEncryptionEnabled) {
                val input = JSONObject(MainScreenViewpagerFragment.results.rawResult)
                val processParam = JSONObject()
                    .put("alreadyCropped", true)
                    .put("scenario", "FullProcess")
                val output = JSONObject()
                    .put("List", input.getJSONObject("ContainerList").getJSONArray("List"))
                    .put("processParam", processParam)
                postRequest(output.toString())
            } else
            //startActivity(Intent(this, ResultsActivity::class.java))

                bundle.putString("results", MainScreenViewpagerFragment.results.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.mainScreenViewpagerFragment)
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.fragment_container, fragment)/*?.addToBackStack(fragment.tag)*/
//                ?.commitNowAllowingStateLoss()
        } else if (typeOfData!!.equals(3)){
            val fragment=MainScreenViewpagerFragment()
            var bundle=Bundle()
            MainScreenViewpagerFragment.results = documentReaderResults
            MainActivity.supportsRfid = MainScreenViewpagerFragment.results.chipPage != 0
//            if (MainScreenViewpagerFragment.results.documentType != null && MainScreenViewpagerFragment.results.documentType.size > 0)
//                MainActivity.supportsRfid = MainScreenViewpagerFragment.results.documentType.get(0).dMRZ
//            val jsonObject = JsonParser().parse(MainScreenViewpagerFragment.results.rawResult).asJsonObject
//            if (jsonObject.get("ChipPage").equals("0")) MainActivity.supportsRfid = false else true
//            Log.e("!!!!!!!",   jsonObject.get("ChipPage").toString())
            if (SettingsActivity.isDataEncryptionEnabled) {
                val input = JSONObject(MainScreenViewpagerFragment.results.rawResult)
                val processParam = JSONObject()
                    .put("alreadyCropped", true)
                    .put("scenario", "FullProcess")
                val output = JSONObject()
                    .put("List", input.getJSONObject("ContainerList").getJSONArray("List"))
                    .put("processParam", processParam)
                postRequest(output.toString())
            } else
            //startActivity(Intent(this, ResultsActivity::class.java))

                bundle.putString("results", MainScreenViewpagerFragment.results.toString())
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.fragment_container, fragment)/*?.addToBackStack(fragment.tag)*/
                ?.commitNowAllowingStateLoss()
        }
    }

    private fun hideDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showDialog(msg: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded, requireContext().theme)
        dialog.setTitle(msg)
        dialog.setView(layoutInflater.inflate(R.layout.simple_dialog, binding.root, false))
        dialog.setCancelable(false)
        return dialog.show()
    }

    private fun postRequest(jsonInputString: String) {
        loadingDialog = showDialog("Getting results from server")
        MainActivity.ENCRYPTED_RESULT_SERVICE
            .httpPost()
            .header(mapOf("Content-Type" to "application/json; utf-8"))
            .body(jsonInputString)
            .responseString { _, _, result ->
                hideDialog()
                when (result) {
                    is Result.Success -> {
                        val map = result.component1()
                            ?.let { DocReaderResultsJsonParser.parseCoreResults(it) }
                        val resultsNew = map?.get("docReaderResults") as DocumentReaderResults
                        MainScreenViewpagerFragment.results = MainScreenViewpagerFragment.results
                        // startActivity(Intent(this, ResultsActivity::class.java))
                    }
                    is Result.Failure -> {
                        println(result.getException())
                        activity?.runOnUiThread {
                            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                                .setTitle("Something went wrong")
                                .setMessage("Check your internet connection and try again")
                                .setPositiveButton("Retry") { _, _ ->
                                    postRequest(jsonInputString)
                                }
                                .setNegativeButton("Cancel", null)
                                .show()
                        }
                    }
                }
            }
    }


}