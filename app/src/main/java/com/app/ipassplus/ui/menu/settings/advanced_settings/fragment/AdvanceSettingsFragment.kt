package com.app.ipassplus.ui.menu.settings.advanced_settings.fragment
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
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.adapter.SettingBottomSheetAdapter
import com.app.ipassplus.databinding.BottomsheetAllSettingsBinding
import com.app.ipassplus.databinding.FragmentAdvanceSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdvanceSettingsFragment : Fragment(),View.OnClickListener {
    private val binding by lazy { FragmentAdvanceSettingsBinding.inflate(layoutInflater) }
    private lateinit var pref: SharedPref

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
        pref = SharedPref(requireContext())
        setOnClickListners()
        setPreferences()

        binding.rlProcessingModes.setOnClickListener {
            val list = arrayListOf(
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
    private fun setOnClickListners(){
        binding.apply {
            ibBackArrowAS.setOnClickListener(this@AdvanceSettingsFragment)
            rlRfidChip.setOnClickListener(this@AdvanceSettingsFragment)
            rlDebug.setOnClickListener(this@AdvanceSettingsFragment)
            swCaptureButton.setOnClickListener(this@AdvanceSettingsFragment)
            swCameraSwitchButton.setOnClickListener(this@AdvanceSettingsFragment)
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
        }
    }
    private fun setPreferences() {
        binding.apply {
            swCaptureButton.isChecked = pref.getBoolean(Constants.CAPTURE_BUTTON)
            swCameraSwitchButton.isChecked = pref.getBoolean(Constants.CAMERA_SWITCH_BUTTON)
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
    private fun bottomSheet(title: String, dataList: List<String>, selectedItem: String){
        var selectedValue: String = selectedItem
        val inflater = LayoutInflater.from(requireContext())
        val bottomSheeBinding = BottomsheetAllSettingsBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext())
        val rvSettings = bottomSheeBinding.rvSettings
        val titl=bottomSheeBinding.tvTopTittle
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
                        }
                        getString(R.string.frame_processing) -> {
                        }
                        getString(R.string.autos) -> {
                        }
                    }
                    pref.setString(Constants.PROCESSING_MODES, selectedValue)
                }
                "Camera Resolution" -> {
                    binding.tvCameraResolutionSet.setText(selectedValue)
                    val l1 = selectedValue.split("x")
                    pref.setString(Constants.CAMERA_RESOLUTION, selectedValue)
                }
                "Camera API" -> {
                    binding.tvCameraAPISet.setText(selectedValue)
                    when(title) {
                        getString(R.string.camera_apis) -> {
                        }
                        getString(R.string.camera2_api) -> {
                        }
                        getString(R.string.auto_selection) -> {
                        }
                    }
                    pref.setString(Constants.CAMERA_API, selectedValue)
                }
                getString(R.string.date_formats) -> {
                    binding.tvDateFormatSet.setText(selectedValue)
                    pref.setString(Constants.DATE_FORMAT, selectedValue)
                }
            }
        }
    }
    private fun TimeOutDialog(titleText: String, value: String) {
        var saveData = false
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_setting_dialog)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
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
                            pref.setString(Constants.TIME_OUT, selectedText.toString())
                        }

                        getString(R.string.timeout_document_detection) -> {
                            binding.tvTimeoutStartSet.text = selectedText.toString()
                            pref.setString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION, selectedText.toString()
                            )
                        }

                        getString(R.string.timeout_identification) -> {
                            binding.tvTimeoutIdentifySet.text = selectedText.toString()
                            pref.setString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION, selectedText.toString())
                        }

                        getString(R.string.zoom_level) -> {
                            binding.tvZoomLevelSet.text = selectedText.toString()
                            pref.setString(Constants.ZOOM_LEVEL, selectedText.toString())
                        }

                        getString(R.string.minimum_dpi) -> {
                            binding.tvMinimumDPISet.text = selectedText.toString()
                            pref.setString(Constants.MINIMUM_DPI, selectedText.toString())
                        }

                        getString(R.string.perspective_angle) -> {
                            binding.tvPerspectiveSet.text = selectedText.toString()
                            pref.setString(Constants.PERPECTIVE_ANGLE, selectedText.toString())
                        }

                        getString(R.string.document_filter) -> {
                            binding.tvDocumentFilterSet.text = selectedText.toString()
                            pref.setString(Constants.DOCUMENT_FILTER, selectedText.toString())
                        }

                        getString(R.string.custom_parameters) -> {
                            binding.tvCustomParamsSet.text = selectedText.toString()
                            pref.setString(Constants.CUSTOM_PARAMETERS, selectedText.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ibBackArrowAS ->{
                findNavController().popBackStack()
            }
            R.id.rlRfidChip -> {
                findNavController().navigate(R.id.rfidSettingFragment)
            }
            R.id.rlDebug -> {
                findNavController().navigate(R.id.debugSettingFragment)
            }
            R.id.rlImageQuality -> {
                findNavController().navigate(R.id.imageQualityFragment)
            }
            R.id.swCaptureButton -> {
                val isChecked = binding.swCaptureButton.isChecked
                pref.setBoolean(Constants.CAPTURE_BUTTON, isChecked)
            }
            R.id.swCameraSwitchButton -> {
                val isChecked = binding.swCameraSwitchButton.isChecked
                pref.setBoolean(Constants.CAMERA_SWITCH_BUTTON, isChecked)
            }
            R.id.swHint -> {
                val isChecked = binding.swHint.isChecked
                pref.setBoolean(Constants.HINT_MESSAGES, isChecked)
            }
            R.id.swHelp -> {
                val isChecked = binding.swHelp.isChecked
                pref.setBoolean(Constants.HELP, isChecked)
            }
            R.id.swMotiondetection -> {
                val isChecked = binding.swMotiondetection.isChecked
                pref.setBoolean(Constants.MOTION_DETECTION, isChecked)
            }
            R.id.swFocusingDetection -> {
                val isChecked = binding.swFocusingDetection.isChecked
                pref.setBoolean(Constants.FOCUSING_DETECTION, isChecked)
            }
            R.id.swCaptureDetected -> {
                val isChecked = binding.swCaptureDetected.isChecked
                pref.setBoolean(Constants.CAPTURE_AFTER_BOUNDARIES_DETECTED, isChecked)
            }
            R.id.swAdjustLevel -> {
                val isChecked = binding.swAdjustLevel.isChecked
                pref.setBoolean(Constants.ADJUST_ZOOM_LEVEL, isChecked)
            }
            R.id.swManualCrop -> {
                val isChecked = binding.swManualCrop.isChecked
                pref.setBoolean(Constants.MANUAL_CROP, isChecked)
            }
            R.id.swInternalImage -> {
                val isChecked = binding.swInternalImage.isChecked
                pref.setBoolean(Constants.INTERNAL_IMAGE, isChecked)
            }
            R.id.swHologramDectection -> {
                val isChecked = binding.swHologramDectection.isChecked
                pref.setBoolean(Constants.HOLOGRAM_DETECTION, isChecked)
            }
        }
    }
}