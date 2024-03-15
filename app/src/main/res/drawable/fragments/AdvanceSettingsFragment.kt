package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipassplus.R
import com.ipassplus.adapter.SettingBottomSheetAdapter
import com.ipassplus.databinding.BottomsheetSettingsBinding
import com.ipassplus.databinding.FragmentAdvanceSettingsBinding
import com.ipassplus.fragments.SettingsFragment.Companion.functionality
import com.ipassplus.utils.Constants
import com.ipassplus.utils.Helpers.Companion.captureModeIntToString
import com.ipassplus.utils.Helpers.Companion.captureModeStringToInt
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.DocumentReader.Instance
import com.regula.documentreader.api.enums.CameraMode
import com.regula.documentreader.api.enums.CaptureMode
import org.json.JSONObject


class AdvanceSettingsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAdvanceSettingsBinding
     lateinit var bottomSheetDialog: BottomSheetDialog
     private lateinit var pref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_advance_settings, container, false)
        binding = FragmentAdvanceSettingsBinding.bind(view)

//        binding.ibBackArrowAS.setOnClickListener(View.OnClickListener {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.nav_host_fragment, SettingsFragment())
//                ?.commit()
//        })
//
//        binding.rlDebug.setOnClickListener(View.OnClickListener {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.nav_host_fragment, DebugSettingFragment())
//                ?.commit()
//        })
//
//        binding.rlImageQuality.setOnClickListener(View.OnClickListener {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.nav_host_fragment, ImageQualityFragment())
//                ?.commit()
//        })
//        binding.rlCameraAPI.setOnClickListener {
//            val dialog = BottomSheetDialog(requireContext())
//            val view = layoutInflater.inflate(R.layout.bottomsheet_cameraapi, null)
//         //   dialog.setCancelable(true)
//            dialog.setContentView(view)
//            dialog.setCanceledOnTouchOutside(true)
//            dialog.show()
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPref(requireContext())
        setClickListeners()
        setPreferences()


        binding.rlProcessingModes.setOnClickListener {
            val list = arrayListOf(
                // getString(R.string.timeout_document_detection)
                "Auto",
                "Video processing",
                "Frame processing",
            )
            val selectedItem=pref.getString(Constants.PROCESSING_MODES)
            bottomSheet("Processing modes", list, selectedItem)

        }

        binding.rlCameraResolution.setOnClickListener {
            val list = arrayListOf(
                "2560x1440",
                "2400x1080",
                "2340x1080",
                "2330x1080",
                "2310x1080",
                "2360x1080",
                "2340x1080",
                "2040x1080",
                "2080x1080",
                "2380x1080",
                "2440x1080",
                "1920x1080",
                "1600x720",
                "1560x720",
                "1520x720",
                "1440x1080",
                "1440x720",
                "192x144",
                "192x108",
                "176x144",
                "160x96",
            )
            val selectedItem=pref.getString(Constants.CAMERA_RESOLUTION)
            bottomSheet("Camera Resolution", list, selectedItem)
        }


        binding.rlCameraAPI.setOnClickListener {
            val list = arrayListOf(
            "Auto selection",
            "Camera API",
            "Camera2 API",
            )
            val selectedItem = pref.getString(Constants.CAMERA_API)
            bottomSheet("Camera API", list, selectedItem)
        }


        binding.rlDateFormat.setOnClickListener {
            val list = arrayListOf(
               "Default",
               "dd/mm/yyyy",
               "mm/dd/yyyy",
               "dd-mm-yyyy",
               "mm-dd-yyyy",
               "dd/mm/yy",
                )
            val selectedItem=pref.getString(Constants.DATE_FORMAT)
            bottomSheet("Date Format", list, selectedItem)
        }


        binding.rlTimeout.setOnClickListener{
            TimeOutDialog(getString(R.string.time_out), pref.getString(Constants.TIME_OUT))
        }

        binding.rlTimeoutStarting.setOnClickListener{
            TimeOutDialog(getString(R.string.timeout_document_detection),pref.getString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION))
        }
        binding.rlTimeoutIdentify.setOnClickListener{
            TimeOutDialog(getString(R.string.timeout_identification), pref.getString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION))
        }
        binding.rlZoomLevel.setOnClickListener{
            TimeOutDialog(getString(R.string.zoom_level),  pref.getString(Constants.ZOOM_LEVEL))
        }
        binding.rlMinimumDPI.setOnClickListener{
            TimeOutDialog(getString(R.string.minimum_dpi),  pref.getString(Constants.MINIMUM_DPI))
        }

        binding.rlPerspectiveangle.setOnClickListener{
            TimeOutDialog(getString(R.string.perspective_angle),  pref.getString(Constants.PERPECTIVE_ANGLE))
        }
        binding.rlDocumentFilter.setOnClickListener{
            TimeOutDialog(getString(R.string.document_filter),  pref.getString(Constants.DOCUMENT_FILTER))
        }
        binding.rlCustomParams.setOnClickListener{
            TimeOutDialog(getString(R.string.custom_parameters),  pref.getString(Constants.CUSTOM_PARAMETERS))
        }

    }

    private fun bottomSheet(title: String, dataList: List<String>, selectedItem: String){
        var selectedValue: String = selectedItem
        val inflater = LayoutInflater.from(requireContext())
        val bottomSheeBinding = BottomsheetSettingsBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext())
        val rvSettings = bottomSheeBinding.rvSettings
        val titl=bottomSheeBinding.tvTopTittle
        //val text=dialog.findViewById<TextView>(R.id.tvTopTittle)
        titl.text =title

        val adapter = SettingBottomSheetAdapter(dataList, selectedValue) {
            selectedValue = it
        }
        rvSettings.setHasFixedSize(true)
        rvSettings.setLayoutManager(LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
        rvSettings.setAdapter(adapter)
        dialog.setContentView(bottomSheeBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        dialog.setOnDismissListener {
            when(title) {
                "Processing modes" -> {
                    binding.tvProcessingModesSet.setText(selectedValue)
                    when(title) {
                        getString(R.string.video_processing) -> {
                            Instance().functionality().edit().setCaptureMode(CaptureMode.CAPTURE_VIDEO)
                        }
                        getString(R.string.frame_processing) -> {
                            Instance().functionality().edit().setCaptureMode(CaptureMode.CAPTURE_FRAME)
                        }
                        getString(R.string.autos) -> {
                            Instance().functionality().edit().setCaptureMode(CaptureMode.AUTO)
                        }
                    }
                    pref.setString(Constants.PROCESSING_MODES, selectedValue)
                }
                "Camera Resolution" -> {
                    binding.tvCameraResolutionSet.setText(selectedValue)
                    val l1 = selectedValue.split("x")
                    Instance().functionality().edit().setCameraSize(l1.get(0).toInt(), l1.get(1).toInt()).apply()
                    pref.setString(Constants.CAMERA_RESOLUTION, selectedValue)
                }
                "Camera API" -> {
                    binding.tvCameraAPISet.setText(selectedValue)
                    when(title) {
                        getString(R.string.camera_apis) -> {
                            Instance().functionality().edit().setCameraMode(CameraMode.CAMERA1).apply()
                        }
                        getString(R.string.camera2_api) -> {
                            Instance().functionality().edit().setCameraMode(CameraMode.CAMERA2).apply()
                        }
                        getString(R.string.auto_selection) -> {
                            Instance().functionality().edit().setCameraMode(CameraMode.AUTO).apply()
                        }
                    }
                    pref.setString(Constants.CAMERA_API, selectedValue)
                }
                getString(R.string.date_formats) -> {
                    binding.tvDateFormatSet.setText(selectedValue)
                    Instance().processParams().dateFormat=selectedValue
                    pref.setString(Constants.DATE_FORMAT, selectedValue)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun TimeOutDialog(titleText: String, value: String) {
        var saveData = false
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.advance_setting__custom_dialog)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        //   layoutParams.gravity = Gravity.CENTER
        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.window?.setWindowAnimations(R.style.Base_Theme_IPassPlus)

        val cancel = dialog.findViewById<TextView>(R.id.tvCancel)
        val save = dialog.findViewById<TextView>(R.id.tvSave)
        val text=dialog.findViewById<TextView>(R.id.tvTimeOut)
        val etTimeout = dialog.findViewById<EditText>(R.id.etTimeout)
        text.text = titleText
        etTimeout.hint=value
        etTimeout.setText(value)

        cancel.setOnClickListener {
            saveData = false
            dialog.dismiss()
        }

        save.setOnClickListener {
            saveData = true
            dialog.dismiss()
        }

        dialog.show()
        dialog.setOnDismissListener {
            if (saveData) {
                val selectedText = etTimeout.text.toString().toDoubleOrNull()
                if (selectedText != null) {
                    when (titleText) {
                        getString(R.string.time_out) -> {
                            binding.tvTimeoutSet.text = selectedText.toString()
                            Instance().processParams().timeout = selectedText.toDouble()
                            pref.setString(Constants.TIME_OUT, selectedText.toString())
                        }

                        getString(R.string.timeout_document_detection) -> {
                            binding.tvTimeoutStartSet.text = selectedText.toString()
                            Instance().processParams().timeoutFromFirstDetect = selectedText.toDouble()
                            pref.setString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION, selectedText.toString()
                            )
                        }

                        getString(R.string.timeout_identification) -> {
                            binding.tvTimeoutIdentifySet.text = selectedText.toString()
                            Instance().processParams().timeoutFromFirstDocType = selectedText.toDouble()
                            pref.setString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION, selectedText.toString())
                        }

                        getString(R.string.zoom_level) -> {
                            binding.tvZoomLevelSet.text = selectedText.toString()
                            Instance().functionality().edit().setZoomFactor(selectedText.toFloat()).apply()
                            /*Instance().functionality().isZoomEnabled
                            functionality.edit().setZoomEnabled(true)*/
                            pref.setString(Constants.ZOOM_LEVEL, selectedText.toString())
                        }

                        getString(R.string.minimum_dpi) -> {
                            binding.tvMinimumDPISet.text = selectedText.toString()
                            Instance().processParams().minDPI = selectedText.toInt()
                            pref.setString(Constants.MINIMUM_DPI, selectedText.toString())
                        }

                        getString(R.string.perspective_angle) -> {
                            binding.tvPerspectiveSet.text = selectedText.toString()
                            Instance().processParams().perspectiveAngle = selectedText.toInt()
                            pref.setString(Constants.PERPECTIVE_ANGLE, selectedText.toString())
                        }

                        getString(R.string.document_filter) -> {
                            binding.tvDocumentFilterSet.text = selectedText.toString()


                            pref.setString(Constants.DOCUMENT_FILTER, selectedText.toString())
                        }

                        getString(R.string.custom_parameters) -> {
                            binding.tvCustomParamsSet.text = selectedText.toString()
                           Instance().processParams().customParams = JSONObject("{\"dePersonalize\":[{\"allTextFields\":true},{\"allGraphicFields\":true}]}")
                            Instance().processParams().customParams = JSONObject("{\"imageDpiOutMax\":0}")
                            pref.setString(Constants.CUSTOM_PARAMETERS, selectedText.toString())
                        }
                    }
                }
            }
        }
    }


        /*dialog.setOnDismissListener {
            if (saveData) {
                val selectedText = etTimeout.text.toString().toDouble()
                if (selectedText != null) {
                    when (titleText) {
                        getString(R.string.time_out) -> {
                            binding.tvTimeoutSet.text = selectedText.toString()
                            Instance().processParams().timeout = selectedText
                            pref.setString(Constants.TIME_OUT, selectedText.toString())

                        }

                        getString(R.string.timeout_document_detection) -> {
                            binding.tvTimeoutStartSet.text = selectedText.toString()
                            Instance().processParams().timeoutFromFirstDetect =
                                selectedText.toDouble()
                            pref.setString(
                                Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION,
                                selectedText.toString()
                            )
                        }

                        getString(R.string.timeout_identification) -> {
                            binding.tvTimeoutIdentifySet.text = selectedText.toString()
                            Instance().processParams().timeoutFromFirstDocType =
                                selectedText.toDouble()
                            pref.setString(
                                Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION,
                                selectedText.toString()
                            )
                        }

                        getString(R.string.zoom_level) -> {
                            binding.tvZoomLevelSet.text = selectedText.toString()
                            Instance().functionality().isZoomEnabled
                            functionality.edit().setZoomEnabled(true)
                            pref.setString(Constants.ZOOM_LEVEL, selectedText.toString())
                        }

                        getString(R.string.minimum_dpi) -> {
                            binding.tvMinimumDPISet.text = selectedText.toString()
                            Instance().processParams().minDPI = selectedText.toInt()
                            pref.setString(Constants.MINIMUM_DPI, selectedText.toString())
                        }

                        getString(R.string.perspective_angle) -> {
                            binding.tvPerspectiveSet.text = selectedText.toString()
                            Instance().processParams().perspectiveAngle = selectedText.toInt()
                            pref.setString(Constants.PERPECTIVE_ANGLE, selectedText.toString())
                        }

                        getString(R.string.document_filter) -> {
                            binding.tvDocumentFilterSet.text = selectedText.toString()
                            pref.setString(Constants.DOCUMENT_FILTER, selectedText.toString())
                        }

                        getString(R.string.custom_parameters) -> {
                            binding.tvCustomParamsSet.text = selectedText.toString()


                            //    Instance().processParams().customParams=selectedText.toLong()
                            pref.setString(Constants.CUSTOM_PARAMETERS, selectedText.toString())
                        }

                    }
                }
            }
            *//*if (saveData) {
                when (titleText) {
                    getString(R.string.time_out) -> {
                        val selectedText = etTimeout.text
                      //  Instance().functionality()
                        pref.setString(Constants.TIME_OUT,selectedText.toString())

                    }
                }
                when (titleText) {
                    getString(R.string.timeout_document_detection) -> {
                        val selectedText = etTimeout.text
                        pref.setString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION,selectedText.toString())

                    }
                }
            }*//*
        }
    }*/


    private fun setClickListeners() {
        binding.apply {
            rlRfidChip.setOnClickListener(this@AdvanceSettingsFragment)
            rlDebug.setOnClickListener(this@AdvanceSettingsFragment)
            ibBackArrowAS.setOnClickListener(this@AdvanceSettingsFragment)
            swCaptureButton.setOnClickListener(this@AdvanceSettingsFragment)
            idSwitch.setOnClickListener(this@AdvanceSettingsFragment)
            swHint.setOnClickListener(this@AdvanceSettingsFragment)
            swHelp.setOnClickListener(this@AdvanceSettingsFragment)
            swMotiondetection.setOnClickListener(this@AdvanceSettingsFragment)
            swFocusingDetection.setOnClickListener(this@AdvanceSettingsFragment)
            rlProcessingModes.setOnClickListener(this@AdvanceSettingsFragment)
            swCaptureDetected.setOnClickListener(this@AdvanceSettingsFragment)
            swAdjustLevel.setOnClickListener(this@AdvanceSettingsFragment)
            swManualCrop.setOnClickListener(this@AdvanceSettingsFragment)
            swInternalImage.setOnClickListener(this@AdvanceSettingsFragment)
            swHologramDectection.setOnClickListener(this@AdvanceSettingsFragment)
            rlImageQuality.setOnClickListener(this@AdvanceSettingsFragment)
      //      rlRfidChip.setOnClickListener(this@AdvanceSettingsFragment)
        }
    }
    private fun setPreferences() {
        binding.apply {
            swCaptureButton.isChecked = pref.getBoolean(Constants.CAPTURE_BUTTON)
            idSwitch.isChecked = pref.getBoolean(Constants.CAMERA_SWITCH_BUTTON)
            swHint.isChecked = pref.getBoolean(Constants.HINT_MESSAGES)
            swHelp.isChecked = pref.getBoolean(Constants.HELP)
            tvTimeoutSet.text=pref.getString(Constants.TIME_OUT)
            tvTimeoutStartSet.text=pref.getString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION)
            tvTimeoutIdentifySet.text=pref.getString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION)
            tvZoomLevelSet.text=pref.getString(Constants.ZOOM_LEVEL)
            tvMinimumDPISet.text=pref.getString(Constants.MINIMUM_DPI)
            tvPerspectiveSet.text=pref.getString(Constants.PERPECTIVE_ANGLE)
            tvDocumentFilterSet.text=pref.getString(Constants.DOCUMENT_FILTER)
            tvCustomParamsSet.text=pref.getString(Constants.CUSTOM_PARAMETERS)
            swMotiondetection.isChecked = pref.getBoolean(Constants.MOTION_DETECTION)
            swFocusingDetection.isChecked = pref.getBoolean(Constants.FOCUSING_DETECTION)
            tvProcessingModesSet.text=pref.getString(Constants.PROCESSING_MODES)
            tvCameraResolutionSet.text=pref.getString(Constants.CAMERA_RESOLUTION)
            tvCameraAPISet.text=pref.getString(Constants.CAMERA_API)
            tvDateFormatSet.text=pref.getString(Constants.DATE_FORMAT)
            swCaptureDetected.isChecked = pref.getBoolean(Constants.CAPTURE_AFTER_BOUNDARIES_DETECTED)
            swAdjustLevel.isChecked = pref.getBoolean(Constants.ADJUST_ZOOM_LEVEL)
            swManualCrop.isChecked = pref.getBoolean(Constants.MANUAL_CROP)
            swInternalImage.isChecked = pref.getBoolean(Constants.INTERNAL_IMAGE)
            swHologramDectection.isChecked = pref.getBoolean(Constants.HOLOGRAM_DETECTION)
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.rlRfidChip -> {
                findNavController().navigate(R.id.rfidSettingsFragment)
            }
            R.id.rlDebug -> {
                findNavController().navigate(R.id.debugSettingFragment)
            }
            R.id.rlImageQuality -> {
                findNavController().navigate(R.id.imageQualityFragment)
            }
            R.id.ibBackArrowAS -> {
                findNavController().popBackStack()
            }
            R.id.swCaptureButton -> {
                val isChecked = binding.swCaptureButton.isChecked
                Instance().functionality().edit().setShowCaptureButton(isChecked).apply()
                pref.setBoolean(Constants.CAPTURE_BUTTON, isChecked)
            }
            R.id.idSwitch -> {
                val isChecked = binding.idSwitch.isChecked
                Instance().functionality().edit().setShowCameraSwitchButton(isChecked).apply()
                pref.setBoolean(Constants.CAMERA_SWITCH_BUTTON, isChecked)
            }
            R.id.swHint -> {
                val isChecked = binding.swHint.isChecked
//                Instance().functionality().edit().setShowCameraSwitchButton(isChecked)
                pref.setBoolean(Constants.HINT_MESSAGES, isChecked)
            }
            R.id.swHelp -> {
                val isChecked = binding.swHelp.isChecked
                Instance().customization().edit().setShowHelpAnimation(isChecked).apply()
                pref.setBoolean(Constants.HELP, isChecked)
            }
            R.id.swMotiondetection -> {
                val isChecked = binding.swMotiondetection.isChecked
                Instance().functionality().edit().setVideoCaptureMotionControl(isChecked).apply()
                pref.setBoolean(Constants.MOTION_DETECTION, isChecked)
            }
            R.id.swFocusingDetection -> {
                val isChecked = binding.swFocusingDetection.isChecked
                Instance().functionality().edit().setSkipFocusingFrames(isChecked).apply()
                pref.setBoolean(Constants.FOCUSING_DETECTION, isChecked)
            }
            R.id.rlProcessingModes -> {
                val isChecked = binding.swFocusingDetection.isChecked
                Instance().functionality().edit().setSkipFocusingFrames(isChecked)
                pref.setBoolean(Constants.FOCUSING_DETECTION, isChecked)
            }
            R.id.swCaptureDetected -> {
                val isChecked = binding.swCaptureDetected.isChecked
//                Instance().functionality().edit().came(isChecked)
                pref.setBoolean(Constants.CAPTURE_AFTER_BOUNDARIES_DETECTED, isChecked)
            }
            /*R.id.swAdjustLevel -> {
                val isChecked = binding.tvProcessingModesSet.isEnabled
                Instance().functionality().edit().setZoomEnabled(isChecked).apply()
                pref.setBoolean(Constants.ADJUST_ZOOM_LEVEL, isChecked)
            }*/
            R.id.swManualCrop -> {
                val isChecked = binding.swManualCrop.isChecked
                Instance().processParams().manualCrop = isChecked
                pref.setBoolean(Constants.MANUAL_CROP, isChecked)
            }
            R.id.swInternalImage -> {
                val isChecked = binding.swInternalImage.isChecked
                Instance().processParams().integralImage = isChecked
                pref.setBoolean(Constants.INTERNAL_IMAGE, isChecked)
            }
            R.id.swHologramDectection -> {
                val isChecked = binding.swHologramDectection.isChecked
                Instance().processParams().checkHologram = isChecked
                pref.setBoolean(Constants.HOLOGRAM_DETECTION, isChecked)
            }
        }
    }
}