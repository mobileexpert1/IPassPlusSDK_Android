package com.app.ipassplus.ui.menu.settings.edl_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.FragmentEDLSettingBinding

class eDLSettingFragment : Fragment() , View.OnClickListener {

    private val binding by lazy { FragmentEDLSettingBinding.inflate(layoutInflater) }
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
            swAdjustLevel.setOnClickListener(this@eDLSettingFragment)
            swTextData.setOnClickListener(this@eDLSettingFragment)
            swLicenseInfo.setOnClickListener(this@eDLSettingFragment)
            swIssueDetails.setOnClickListener(this@eDLSettingFragment)
            swPortraitImg.setOnClickListener(this@eDLSettingFragment)
            swSignatureImg.setOnClickListener(this@eDLSettingFragment)
            swBiometryFacial.setOnClickListener(this@eDLSettingFragment)
            swBiometryFingerprint.setOnClickListener(this@eDLSettingFragment)
            swBiometryIris.setOnClickListener(this@eDLSettingFragment)
            swBiometryOther.setOnClickListener(this@eDLSettingFragment)
            swBotdefinedeDL.setOnClickListener(this@eDLSettingFragment)
            swDomesticData.setOnClickListener(this@eDLSettingFragment)
            swNonMarch.setOnClickListener(this@eDLSettingFragment)
            swActiveAuthInfo.setOnClickListener(this@eDLSettingFragment)
            swrlEacInfoeDl.setOnClickListener(this@eDLSettingFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.swAdjustLevel -> {
                val isChecked = binding.swAdjustLevel.isChecked
                setSwitchesEnabled(isChecked)
                pref.setBoolean(Constants.READ_E_DL, isChecked)
            }
            R.id.swTextData -> {
                val isChecked = binding.swTextData.isChecked
                pref.setBoolean(Constants.E_DL_DG1, isChecked)
            }
            R.id.swLicenseInfo -> {
                val isChecked = binding.swLicenseInfo.isChecked
                pref.setBoolean(Constants.E_DL_DG2, isChecked)
            }
            R.id.swIssueDetails -> {
                val isChecked = binding.swIssueDetails.isChecked
                pref.setBoolean(Constants.E_DL_DG3, isChecked)
            }
            R.id.swPortraitImg -> {
                val isChecked = binding.swPortraitImg.isChecked
                pref.setBoolean(Constants.E_DL_DG4, isChecked)
            }
            R.id.swSignatureImg -> {
                val isChecked = binding.swSignatureImg.isChecked
                pref.setBoolean(Constants.E_DL_DG5, isChecked)
            }
            R.id.swBiometryFacial -> {
                val isChecked = binding.swBiometryFacial.isChecked
                pref.setBoolean(Constants.E_DL_DG6, isChecked)
            }
            R.id.swBiometryFingerprint -> {
                val isChecked = binding.swBiometryFingerprint.isChecked
                pref.setBoolean(Constants.E_DL_DG7, isChecked)
            }
            R.id.swBiometryIris -> {
                val isChecked = binding.swBiometryIris.isChecked
                pref.setBoolean(Constants.E_DL_DG8, isChecked)
            }
            R.id.swBiometryOther -> {
                val isChecked = binding.swBiometryOther.isChecked
                pref.setBoolean(Constants.E_DL_DG9, isChecked)
            }
            R.id.swBotdefinedeDL -> {
                val isChecked = binding.swBotdefinedeDL.isChecked
                pref.setBoolean(Constants.E_DL_DG10, isChecked)
            }
            R.id.swDomesticData -> {
                val isChecked = binding.swDomesticData.isChecked
                pref.setBoolean(Constants.E_DL_DG11, isChecked)
            }
            R.id.swNonMarch -> {
                val isChecked = binding.swNonMarch.isChecked
                pref.setBoolean(Constants.E_DL_DG12, isChecked)
            }
            R.id.swActiveAuthInfo -> {
                val isChecked = binding.swActiveAuthInfo.isChecked
                pref.setBoolean(Constants.E_DL_DG13, isChecked)
            }
            R.id.swrlEacInfoeDl -> {
                val isChecked = binding.swrlEacInfoeDl.isChecked
                pref.setBoolean(Constants.E_DL_DG14, isChecked)
            }
        }
    }

}