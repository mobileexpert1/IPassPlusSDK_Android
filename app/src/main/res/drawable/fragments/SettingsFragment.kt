package com.ipassplus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ipassplus.R
import com.ipassplus.adapter.SettingBottomSheetAdapter
import com.ipassplus.databinding.BottomsheetSettingsBinding
import com.ipassplus.databinding.FragmentSettingsBinding
import com.ipassplus.models.modelData.BottomSheetItem
import com.ipassplus.utils.Constants
import com.ipassplus.utils.Constants.DOUBLE_PAGE_SPREAD_PROCESSING
import com.ipassplus.utils.Constants.FACE_MATCHING
import com.ipassplus.utils.Constants.LIVENESS
import com.ipassplus.utils.Constants.MULTIPAGE_PROCESSING
import com.ipassplus.utils.Constants.SOUND
import com.ipassplus.utils.Constants.VIBRATION
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader.Instance
import com.regula.documentreader.api.enums.CameraMode
import com.regula.documentreader.api.params.Functionality


class SettingsFragment : Fragment(), View.OnClickListener {

    private var selectedTabIndex = 0
    lateinit var pref: SharedPref
    private lateinit var binding: FragmentSettingsBinding

    companion object {
        var isRfidEnabled = false
        var functionality = Functionality()
        var isDataEncryptionEnabled = false
        var customString:String? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_settings, container, false)
        binding = FragmentSettingsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPref(requireContext())
        setClickListeners()
        setPreferences()

        binding.rlCameraToUse.setOnClickListener {
            val list = arrayListOf(
             "Camera 0",
             "Camera 1",
            )
            val selectedItem=pref.getString(Constants.CAMERA_TO_USE)
            settingBottom("Camera To Use",list,selectedItem)
        }

    }

    private fun settingBottom(title: String, dataList: List<String>, selectedItem: String){
        var selectedValues = selectedItem
        val inflater = LayoutInflater.from(requireContext())
        val bottomSheeBinding = BottomsheetSettingsBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext())
        val rvSettings = bottomSheeBinding.rvSettings
        val titl=bottomSheeBinding.tvTopTittle
        //val text=dialog.findViewById<TextView>(R.id.tvTopTittle)
        titl.text =title

        val adapter = SettingBottomSheetAdapter(dataList, selectedValues) {
            selectedValues = it
        }
        rvSettings.setHasFixedSize(true)
        rvSettings.setLayoutManager(LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
        rvSettings.setAdapter(adapter)
        dialog.setContentView(bottomSheeBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        dialog.setOnDismissListener {
            when(title) {
                "Camera To Use" -> {
                    binding.tvCameraCount.setText(selectedValues)

                    pref.setString(Constants.CAMERA_TO_USE, selectedValues)
                }
            }
        }
    }


    private fun setClickListeners() {
        binding.rlReset.setOnClickListener(this)
        binding.ibBackArrow.setOnClickListener(this)
        binding.rlAdvanced.setOnClickListener(this)
        binding.rlCameraToUse.setOnClickListener(this)
        binding.swFacematcing.setOnClickListener(this)
        binding.swMultipageSwitch.setOnClickListener(this)
        binding.swMultipageSwitch.setOnClickListener(this)
        binding.swDoublepageSwitch.setOnClickListener(this)
        binding.swSoundSwitch.setOnClickListener(this)
        binding.swVibrationSwitch.setOnClickListener(this)
    }

    private fun resetDialog() = MaterialAlertDialogBuilder(requireContext(), R.style.ConfirmAlertDialogTheme)
        .setTitle(getString(R.string.strResetAllSettings))
        .setBackground(ContextCompat.getDrawable(requireContext(), R.color.white))
        .setMessage(
            getString(
                if (selectedTabIndex == 0)
                    R.string.strResetAllAppSettingsConfirmation
                else
                    R.string.strResetAllAPISettingsConfirmation
            )
        )
        .setPositiveButton(getString(R.string.reset).uppercase()) { _, _ -> reset() }
        .setNegativeButton(getString(R.string.strCancel).uppercase(), null)
        .show()

    private fun reset() {
        if (selectedTabIndex == 0) {
            isRfidEnabled = false
            isDataEncryptionEnabled = false
            customString = null

            binding.idSwitch.isChecked = false
            binding.swFacematcing.isChecked = false
            binding.swMultipageSwitch.isChecked = false
            binding.swDoublepageSwitch.isChecked = false
            binding.swSoundSwitch.isChecked = false
            binding.swVibrationSwitch.isChecked = false

            pref.clearData()

        } else {
            val scenario = Instance().processParams().scenario
            Instance().processParams().scenario = scenario

        }
    }

    private fun setPreferences() {
        binding.apply {
            swFacematcing.isChecked = pref.getBoolean(FACE_MATCHING)
            idSwitch.isChecked = pref.getBoolean(LIVENESS)
            swMultipageSwitch.isChecked = pref.getBoolean(MULTIPAGE_PROCESSING)
            swDoublepageSwitch.isChecked = pref.getBoolean(DOUBLE_PAGE_SPREAD_PROCESSING)
            swSoundSwitch.isChecked = pref.getBoolean(SOUND)
            swVibrationSwitch.isChecked = pref.getBoolean(VIBRATION)
            tvCameraCount.text=pref.getString(Constants.CAMERA_TO_USE)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.ibBackArrow -> {
                findNavController().popBackStack()
            }
            R.id.rlAdvanced -> {
                findNavController().navigate(R.id.advanceSettingsFragment)
            }

            R.id.swFacematcing -> {
                val isChecked = binding.swFacematcing.isChecked
                pref.setBoolean(FACE_MATCHING, isChecked)
            }
            R.id.idSwitch -> {
                val isChecked = binding.idSwitch.isChecked
                pref.setBoolean(LIVENESS, isChecked)
            }
            R.id.swMultipageSwitch -> {
                val isChecked = binding.swMultipageSwitch.isChecked
                Instance().processParams().multipageProcessing = isChecked
                pref.setBoolean(MULTIPAGE_PROCESSING, isChecked)
            }
            R.id.swDoublepageSwitch -> {
                val isChecked = binding.swDoublepageSwitch.isChecked
                Instance().processParams().doublePageSpread = isChecked
                pref.setBoolean(DOUBLE_PAGE_SPREAD_PROCESSING, isChecked)
            }
            R.id.swSoundSwitch -> {
                val isChecked = binding.swSoundSwitch.isChecked
                pref.setBoolean(SOUND, isChecked)
            }
            R.id.swVibrationSwitch -> {
                val isChecked = binding.swVibrationSwitch.isChecked
                pref.setBoolean(VIBRATION, isChecked)
            }
            R.id.rlReset -> { resetDialog() }
        }
    }
}