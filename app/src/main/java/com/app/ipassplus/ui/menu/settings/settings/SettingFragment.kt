package com.app.ipassplus.ui.menu.settings.settings
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ipassplus.R
import com.app.ipassplus.adapter.SettingBottomSheetAdapter
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.BottomsheetAllSettingsBinding
import com.app.ipassplus.databinding.FragmentSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingFragment : Fragment(),View.OnClickListener {
    private val binding by lazy { FragmentSettingBinding.inflate(layoutInflater) }
    private var selectedTabIndex = 0
    lateinit var pref: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
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
        val bottomSheetBinding = BottomsheetAllSettingsBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext())
        val rvSettings = bottomSheetBinding.rvSettings
        val titl=bottomSheetBinding.tvTopTittle
        titl.text =title

        val adapter = SettingBottomSheetAdapter(dataList, selectedValues) {
            selectedValues = it
        }
        rvSettings.setHasFixedSize(true)
        rvSettings.setLayoutManager(LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
        rvSettings.setAdapter(adapter)
        dialog.setContentView(bottomSheetBinding.root)
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
    private fun setClickListeners(){
        binding.apply {
            ibBackArrowSetting.setOnClickListener(this@SettingFragment)
            swFacematcing.setOnClickListener(this@SettingFragment)
            swLiveness.setOnClickListener(this@SettingFragment)
            rlCameraToUse.setOnClickListener(this@SettingFragment)
            swMultipageSwitch.setOnClickListener(this@SettingFragment)
            swDoublepageSwitch.setOnClickListener(this@SettingFragment)
            swSoundSwitch.setOnClickListener(this@SettingFragment)
            swVibrationSwitch.setOnClickListener(this@SettingFragment)
            rlInstruction.setOnClickListener(this@SettingFragment)
            rlAdvanced.setOnClickListener(this@SettingFragment)
            rlReset.setOnClickListener(this@SettingFragment)
        }
    }

    private fun setPreferences() {
        binding.apply {
            swFacematcing.isChecked = pref.getBoolean(Constants.FACE_MATCHING)
            swLiveness.isChecked = pref.getBoolean(Constants.LIVENESS)
            swMultipageSwitch.isChecked = pref.getBoolean(Constants.MULTIPAGE_PROCESSING)
            swDoublepageSwitch.isChecked = pref.getBoolean(Constants.DOUBLE_PAGE_SPREAD_PROCESSING)
            swSoundSwitch.isChecked = pref.getBoolean(Constants.SOUND)
            swVibrationSwitch.isChecked = pref.getBoolean(Constants.VIBRATION)
            tvCameraCount.text=pref.getString(Constants.CAMERA_TO_USE)
        }
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
        /*    SettingsFragment.isRfidEnabled = false
            SettingsFragment.isDataEncryptionEnabled = false
            SettingsFragment.customString = null
            binding.idSwitch.isChecked = false
            binding.swFacematcing.isChecked = false
            binding.swMultipageSwitch.isChecked = false
            binding.swDoublepageSwitch.isChecked = false
            binding.swSoundSwitch.isChecked = false
            binding.swVibrationSwitch.isChecked = false
*/
           // pref.clearData()

        } else {
           /* val scenario = DocumentReader.Instance().processParams().scenario
            DocumentReader.Instance().processParams().scenario = scenario*/

        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.ibBackArrowSetting -> {
                findNavController().popBackStack()
            }
            R.id.swFacematcing -> {
                val isChecked = binding.swFacematcing.isChecked
                pref.setBoolean(Constants.FACE_MATCHING, isChecked)
            }
            R.id.swLiveness -> {
                val isChecked = binding.swLiveness.isChecked
                pref.setBoolean(Constants.LIVENESS, isChecked)
            }
            R.id.swMultipageSwitch -> {
                val isChecked = binding.swMultipageSwitch.isChecked
                pref.setBoolean(Constants.MULTIPAGE_PROCESSING, isChecked)
            }
            R.id.swDoublepageSwitch -> {
                val isChecked = binding.swDoublepageSwitch.isChecked
                pref.setBoolean(Constants.DOUBLE_PAGE_SPREAD_PROCESSING, isChecked)
            }
            R.id.swSoundSwitch -> {
                val isChecked = binding.swSoundSwitch.isChecked
                pref.setBoolean(Constants.SOUND, isChecked)
            }
            R.id.swVibrationSwitch -> {
                val isChecked = binding.swVibrationSwitch.isChecked
                pref.setBoolean(Constants.VIBRATION, isChecked)
            }
            R.id.rlInstruction -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SHOW_INSTRUCTION))
                startActivity(i)
            }
            R.id.rlAdvanced -> {
                findNavController().navigate(R.id.advanceSettingsFragment)
            }
            R.id.rlReset -> { resetDialog()}
        }
    }
}