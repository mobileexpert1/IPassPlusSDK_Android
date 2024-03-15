package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.ipassplus.R
import com.ipassplus.databinding.FragmentEpassportSettingsBinding
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader

class EpassportSettingsFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentEpassportSettingsBinding.inflate(layoutInflater) }
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
            swAdjustLevel.setOnClickListener(this@EpassportSettingsFragment)
            swMachineSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swBiometrySwitch.setOnClickListener(this@EpassportSettingsFragment)
            swBiometryFingerSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swBiometryDataSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swPortraitDataSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swNotdefinedSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swSignatureMarkSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swNotdefinedDG8Switch.setOnClickListener(this@EpassportSettingsFragment)
            swNotdefinedDG9Switch.setOnClickListener(this@EpassportSettingsFragment)
            swNotdefinedDG10Switch.setOnClickListener(this@EpassportSettingsFragment)
            swAdditionalDetailsSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swAdditionalDocumentSwitch.setOnClickListener(this@EpassportSettingsFragment)
            swOptionalDetailst.setOnClickListener(this@EpassportSettingsFragment)
            swEacInfo.setOnClickListener(this@EpassportSettingsFragment)
            swActiveAuth.setOnClickListener(this@EpassportSettingsFragment)
            swPersonNotify.setOnClickListener(this@EpassportSettingsFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.swAdjustLevel -> {
                val isChecked = binding.swAdjustLevel.isChecked
                setSwitchesEnabled(isChecked)
                DocumentReader.Instance().rfidScenario().isReadEPassport = isChecked
                pref.setBoolean(Constants.READ_E_PASSPORT, isChecked)
            }
            R.id.swMachineSwitch -> {
                val isChecked = binding.swMachineSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG1 = isChecked
                pref.setBoolean(Constants.MACHINE_READABLE_ZONE_DG1, isChecked)
            }
            R.id.swBiometrySwitch -> {
                val isChecked = binding.swBiometrySwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG2 = isChecked
                pref.setBoolean(Constants.BIOMETRY_FACIAL_DATA_DG2, isChecked)
            }
            R.id.swBiometryFingerSwitch -> {
                val isChecked = binding.swBiometryFingerSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG3 = isChecked
                pref.setBoolean(Constants.BIOMETRY_FINGERPRINT_DG3, isChecked)
            }
            R.id.swBiometryDataSwitch -> {
                val isChecked = binding.swBiometryDataSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG4 = isChecked
                pref.setBoolean(Constants.BIOMETRY_IRIS_DATA_DG4, isChecked)
            }
            R.id.swPortraitDataSwitch -> {
                val isChecked = binding.swPortraitDataSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG5 = isChecked
                pref.setBoolean(Constants.PORTRAIT_DG5, isChecked)
            }
            R.id.swNotdefinedSwitch -> {
                val isChecked = binding.swNotdefinedSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG6 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG6, isChecked)
            }
            R.id.swSignatureMarkSwitch -> {
                val isChecked = binding.swSignatureMarkSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG7 = isChecked
                pref.setBoolean(Constants.SIGNATURE_DG7, isChecked)
            }
            R.id.swNotdefinedDG8Switch -> {
                val isChecked = binding.swNotdefinedDG8Switch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG8 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG8, isChecked)
            }
            R.id.swNotdefinedDG9Switch -> {
                val isChecked = binding.swNotdefinedDG9Switch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG9 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG9, isChecked)
            }
            R.id.swNotdefinedDG10Switch -> {
                val isChecked = binding.swNotdefinedDG10Switch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG10 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG10, isChecked)
            }
            R.id.swAdditionalDetailsSwitch -> {
                val isChecked = binding.swAdditionalDetailsSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG11 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG11, isChecked)
            }
            R.id.swAdditionalDocumentSwitch -> {
                val isChecked = binding.swAdditionalDocumentSwitch.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG12 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG12, isChecked)
            }
            R.id.swOptionalDetailst -> {
                val isChecked = binding.swOptionalDetailst.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG13 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG13, isChecked)
            }
            R.id.swEacInfo -> {
                val isChecked = binding.swEacInfo.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG14 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG14, isChecked)
            }
            R.id.swActiveAuth -> {
                val isChecked = binding.swActiveAuth.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG15 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG15, isChecked)
            }
            R.id.swPersonNotify -> {
                val isChecked = binding.swPersonNotify.isChecked
                DocumentReader.Instance().rfidScenario().ePassportDataGroups().isDG16 = isChecked
                pref.setBoolean(Constants.E_PASSPORT_DG16, isChecked)
            }
        }
    }

}