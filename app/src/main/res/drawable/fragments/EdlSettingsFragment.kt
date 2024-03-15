package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipassplus.R
import com.ipassplus.databinding.FragmentEdlSettingsBinding
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader


class EdlSettingsFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentEdlSettingsBinding.inflate(layoutInflater) }
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
        setPreferences()

    }
    private fun setSwitchesEnabled(isenabled: Boolean) {
        binding.swTextData.isEnabled=isenabled
        binding.swLicenseInfo.isEnabled=isenabled
        binding.swIssueDetails.isEnabled=isenabled
        binding.swPortraitImg.isEnabled=isenabled
        binding.swSignatureImg.isEnabled=isenabled
        binding.swBiometryFacial.isEnabled=isenabled
        binding.swBiometryFingerprint.isEnabled=isenabled
        binding.swBiometryIris.isEnabled=isenabled
        binding.swBiometryOther.isEnabled=isenabled
        binding.swBotdefinedeDL.isEnabled=isenabled
        binding.swDomesticData.isEnabled=isenabled
        binding.swNonMarch.isEnabled=isenabled
        binding.swActiveAuthInfo.isEnabled=isenabled
        binding.swrlEacInfoeDl.isEnabled=isenabled
    }

    private fun setPreferences() {
        binding.apply {
            swAdjustLevel.isChecked = pref.getBoolean(Constants.READ_E_DL)
            swTextData.isChecked = pref.getBoolean(Constants.E_DL_DG1)
            swLicenseInfo.isChecked = pref.getBoolean(Constants.E_DL_DG2)
            swIssueDetails.isChecked = pref.getBoolean(Constants.E_DL_DG3)
            swPortraitImg.isChecked = pref.getBoolean(Constants.E_DL_DG4)
            swSignatureImg.isChecked = pref.getBoolean(Constants.E_DL_DG5)
            swBiometryFacial.isChecked = pref.getBoolean(Constants.E_DL_DG6)
            swBiometryFingerprint.isChecked = pref.getBoolean(Constants.E_DL_DG7)
            swBiometryIris.isChecked = pref.getBoolean(Constants.E_DL_DG8)
            swBiometryOther.isChecked = pref.getBoolean(Constants.E_DL_DG9)
            swBotdefinedeDL.isChecked = pref.getBoolean(Constants.E_DL_DG10)
            swDomesticData.isChecked = pref.getBoolean(Constants.E_DL_DG11)
            swNonMarch.isChecked = pref.getBoolean(Constants.E_DL_DG12)
            swActiveAuthInfo.isChecked = pref.getBoolean(Constants.E_DL_DG13)
            swrlEacInfoeDl.isChecked = pref.getBoolean(Constants.E_DL_DG14)
        }
    }

    private fun setClickListeners() {
        binding.apply {
            swAdjustLevel.setOnClickListener(this@EdlSettingsFragment)
            swTextData.setOnClickListener(this@EdlSettingsFragment)
            swLicenseInfo.setOnClickListener(this@EdlSettingsFragment)
            swIssueDetails.setOnClickListener(this@EdlSettingsFragment)
            swPortraitImg.setOnClickListener(this@EdlSettingsFragment)
            swSignatureImg.setOnClickListener(this@EdlSettingsFragment)
            swBiometryFacial.setOnClickListener(this@EdlSettingsFragment)
            swBiometryFingerprint.setOnClickListener(this@EdlSettingsFragment)
            swBiometryIris.setOnClickListener(this@EdlSettingsFragment)
            swBiometryOther.setOnClickListener(this@EdlSettingsFragment)
            swBotdefinedeDL.setOnClickListener(this@EdlSettingsFragment)
            swDomesticData.setOnClickListener(this@EdlSettingsFragment)
            swNonMarch.setOnClickListener(this@EdlSettingsFragment)
            swActiveAuthInfo.setOnClickListener(this@EdlSettingsFragment)
            swrlEacInfoeDl.setOnClickListener(this@EdlSettingsFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.swAdjustLevel -> {
                val isChecked = binding.swAdjustLevel.isChecked
                setSwitchesEnabled(isChecked)
                DocumentReader.Instance().rfidScenario().isReadEDL = isChecked
                pref.setBoolean(Constants.READ_E_DL, isChecked)
            }
            R.id.swTextData -> {
                val isChecked = binding.swTextData.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG1 = isChecked
                pref.setBoolean(Constants.E_DL_DG1, isChecked)
            }
            R.id.swLicenseInfo -> {
                val isChecked = binding.swLicenseInfo.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG2 = isChecked
                pref.setBoolean(Constants.E_DL_DG2, isChecked)
            }
            R.id.swIssueDetails -> {
                val isChecked = binding.swIssueDetails.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG3 = isChecked
                pref.setBoolean(Constants.E_DL_DG3, isChecked)
            }
            R.id.swPortraitImg -> {
                val isChecked = binding.swPortraitImg.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG4 = isChecked
                pref.setBoolean(Constants.E_DL_DG4, isChecked)
            }
            R.id.swSignatureImg -> {
                val isChecked = binding.swSignatureImg.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG5 = isChecked
                pref.setBoolean(Constants.E_DL_DG5, isChecked)
            }
            R.id.swBiometryFacial -> {
                val isChecked = binding.swBiometryFacial.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG6 = isChecked
                pref.setBoolean(Constants.E_DL_DG6, isChecked)
            }
            R.id.swBiometryFingerprint -> {
                val isChecked = binding.swBiometryFingerprint.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG7 = isChecked
                pref.setBoolean(Constants.E_DL_DG7, isChecked)
            }
            R.id.swBiometryIris -> {
                val isChecked = binding.swBiometryIris.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG8 = isChecked
                pref.setBoolean(Constants.E_DL_DG8, isChecked)
            }
            R.id.swBiometryOther -> {
                val isChecked = binding.swBiometryOther.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG9 = isChecked
                pref.setBoolean(Constants.E_DL_DG9, isChecked)
            }
            R.id.swBotdefinedeDL -> {
                val isChecked = binding.swBotdefinedeDL.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG10 = isChecked
                pref.setBoolean(Constants.E_DL_DG10, isChecked)
            }
            R.id.swDomesticData -> {
                val isChecked = binding.swDomesticData.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG11 = isChecked
                pref.setBoolean(Constants.E_DL_DG11, isChecked)
            }
            R.id.swNonMarch -> {
                val isChecked = binding.swNonMarch.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG12 = isChecked
                pref.setBoolean(Constants.E_DL_DG12, isChecked)
            }
            R.id.swActiveAuthInfo -> {
                val isChecked = binding.swActiveAuthInfo.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG13 = isChecked
                pref.setBoolean(Constants.E_DL_DG13, isChecked)
            }
            R.id.swrlEacInfoeDl -> {
                val isChecked = binding.swrlEacInfoeDl.isChecked
                DocumentReader.Instance().rfidScenario().eDLDataGroups().isDG14 = isChecked
                pref.setBoolean(Constants.E_DL_DG14, isChecked)
            }
        }
    }

}