package com.app.ipassplus.ui.menu.settings.epassport_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.FragmentEpassportSettingBinding

class EpassportSettingFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentEpassportSettingBinding.inflate(layoutInflater) }
    private lateinit var pref: SharedPref

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
        setClickListeners()
//        setPreferences()

    }

    private fun setSwitchesEnabled(isenabled: Boolean) {

        binding.swActiveAuth.isEnabled = isenabled
        binding.swMachineSwitch.isEnabled = isenabled
        binding.swBiometrySwitch.isEnabled = isenabled
        binding.swBiometryFingerSwitch.isEnabled = isenabled
        binding.swBiometryDataSwitch.isEnabled = isenabled
        binding.swPortraitDataSwitch.isEnabled = isenabled
        binding.swNotdefinedSwitch.isEnabled = isenabled
        binding.swSignatureMarkSwitch.isEnabled = isenabled
        binding.swNotdefinedDG8Switch.isEnabled = isenabled
        binding.swNotdefinedDG9Switch.isEnabled = isenabled
        binding.swNotdefinedDG10Switch.isEnabled = isenabled
        binding.swAdditionalDetailsSwitch.isEnabled = isenabled
        binding.swOptionalDetailst.isEnabled = isenabled
        binding.swEacInfo.isEnabled = isenabled
        binding.swActiveAuth.isEnabled = isenabled
        binding.swPersonNotify.isEnabled = isenabled

    }

    private fun setPreferences() {
        binding.apply {
            swAdjustLevel.isChecked = pref.getBoolean(Constants.READ_E_PASSPORT)
            swMachineSwitch.isChecked = pref.getBoolean(Constants.MACHINE_READABLE_ZONE_DG1)
            swBiometrySwitch.isChecked = pref.getBoolean(Constants.BIOMETRY_FACIAL_DATA_DG2)
            swBiometryFingerSwitch.isChecked = pref.getBoolean(Constants.BIOMETRY_FINGERPRINT_DG3)
            swBiometryDataSwitch.isChecked = pref.getBoolean(Constants.BIOMETRY_IRIS_DATA_DG4)
            swPortraitDataSwitch.isChecked = pref.getBoolean(Constants.PORTRAIT_DG5)
            swNotdefinedSwitch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG6)
            swSignatureMarkSwitch.isChecked = pref.getBoolean(Constants.SIGNATURE_DG7)
            swNotdefinedDG8Switch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG8)
            swNotdefinedDG9Switch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG9)
            swNotdefinedDG10Switch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG10)
            swAdditionalDetailsSwitch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG11)
            swAdditionalDocumentSwitch.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG12)
            swOptionalDetailst.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG13)
            swEacInfo.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG14)
            swActiveAuth.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG15)
            swPersonNotify.isChecked = pref.getBoolean(Constants.E_PASSPORT_DG16)
        }
    }

    private fun setClickListeners() {
        binding.apply {
            swAdjustLevel.setOnClickListener(this@EpassportSettingFragment)
            swMachineSwitch.setOnClickListener(this@EpassportSettingFragment)
            swBiometrySwitch.setOnClickListener(this@EpassportSettingFragment)
            swBiometryFingerSwitch.setOnClickListener(this@EpassportSettingFragment)
            swBiometryDataSwitch.setOnClickListener(this@EpassportSettingFragment)
            swPortraitDataSwitch.setOnClickListener(this@EpassportSettingFragment)
            swNotdefinedSwitch.setOnClickListener(this@EpassportSettingFragment)
            swSignatureMarkSwitch.setOnClickListener(this@EpassportSettingFragment)
            swNotdefinedDG8Switch.setOnClickListener(this@EpassportSettingFragment)
            swNotdefinedDG9Switch.setOnClickListener(this@EpassportSettingFragment)
            swNotdefinedDG10Switch.setOnClickListener(this@EpassportSettingFragment)
            swAdditionalDetailsSwitch.setOnClickListener(this@EpassportSettingFragment)
            swAdditionalDocumentSwitch.setOnClickListener(this@EpassportSettingFragment)
            swOptionalDetailst.setOnClickListener(this@EpassportSettingFragment)
            swEacInfo.setOnClickListener(this@EpassportSettingFragment)
            swActiveAuth.setOnClickListener(this@EpassportSettingFragment)
            swPersonNotify.setOnClickListener(this@EpassportSettingFragment)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.swAdjustLevel -> {
                val isChecked = binding.swAdjustLevel.isChecked
                setSwitchesEnabled(isChecked)
                pref.setBoolean(Constants.READ_E_PASSPORT, isChecked)
            }

            R.id.swMachineSwitch -> {
                val isChecked = binding.swMachineSwitch.isChecked
                pref.setBoolean(Constants.MACHINE_READABLE_ZONE_DG1, isChecked)
            }

            R.id.swBiometrySwitch -> {
                val isChecked = binding.swBiometrySwitch.isChecked
                pref.setBoolean(Constants.BIOMETRY_FACIAL_DATA_DG2, isChecked)
            }

            R.id.swBiometryFingerSwitch -> {
                val isChecked = binding.swBiometryFingerSwitch.isChecked
                pref.setBoolean(Constants.BIOMETRY_FINGERPRINT_DG3, isChecked)
            }

            R.id.swBiometryDataSwitch -> {
                val isChecked = binding.swBiometryDataSwitch.isChecked
                pref.setBoolean(Constants.BIOMETRY_IRIS_DATA_DG4, isChecked)
            }

            R.id.swPortraitDataSwitch -> {
                val isChecked = binding.swPortraitDataSwitch.isChecked
                pref.setBoolean(Constants.PORTRAIT_DG5, isChecked)
            }

            R.id.swNotdefinedSwitch -> {
                val isChecked = binding.swNotdefinedSwitch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG6, isChecked)
            }

            R.id.swSignatureMarkSwitch -> {
                val isChecked = binding.swSignatureMarkSwitch.isChecked
                pref.setBoolean(Constants.SIGNATURE_DG7, isChecked)
            }

            R.id.swNotdefinedDG8Switch -> {
                val isChecked = binding.swNotdefinedDG8Switch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG8, isChecked)
            }

            R.id.swNotdefinedDG9Switch -> {
                val isChecked = binding.swNotdefinedDG9Switch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG9, isChecked)
            }

            R.id.swNotdefinedDG10Switch -> {
                val isChecked = binding.swNotdefinedDG10Switch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG10, isChecked)
            }

            R.id.swAdditionalDetailsSwitch -> {
                val isChecked = binding.swAdditionalDetailsSwitch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG11, isChecked)
            }

            R.id.swAdditionalDocumentSwitch -> {
                val isChecked = binding.swAdditionalDocumentSwitch.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG12, isChecked)
            }

            R.id.swOptionalDetailst -> {
                val isChecked = binding.swOptionalDetailst.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG13, isChecked)
            }

            R.id.swEacInfo -> {
                val isChecked = binding.swEacInfo.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG14, isChecked)
            }

            R.id.swActiveAuth -> {
                val isChecked = binding.swActiveAuth.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG15, isChecked)
            }

            R.id.swPersonNotify -> {
                val isChecked = binding.swPersonNotify.isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG16, isChecked)
            }
        }
    }
}