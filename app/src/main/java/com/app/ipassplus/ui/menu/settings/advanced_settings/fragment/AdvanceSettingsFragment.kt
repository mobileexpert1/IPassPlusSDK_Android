package com.app.ipassplus.ui.menu.settings.advanced_settings.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.FragmentAdvanceSettingsBinding

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

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ibBackArrowAS ->{
                findNavController().popBackStack()
            }
            /*R.id.rlRfidChip -> {
                findNavController().navigate(R.id.rfidSettingsFragment)
            }*/
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