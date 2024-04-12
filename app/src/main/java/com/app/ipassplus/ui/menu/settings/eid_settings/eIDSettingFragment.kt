package com.app.ipassplus.ui.menu.settings.eid_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.FragmentEIDSettingBinding

class eIDSettingFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentEIDSettingBinding.inflate(layoutInflater) }
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
    private fun setSwitchesEnabled(isenabled:Boolean){
        binding.swDocumentType.isEnabled=isenabled
        binding.swIssueState.isEnabled=isenabled
        binding.swDateOfExpiry.isEnabled=isenabled
        binding.swGiveName.isEnabled=isenabled
        binding.swFamilyName.isEnabled=isenabled
        binding.swPseudonym.isEnabled=isenabled
        binding.swAcademicTitle.isEnabled=isenabled
        binding.swDateOfBirth.isEnabled=isenabled
        binding.swPlaceOfBirth.isEnabled=isenabled
        binding.swNationality.isEnabled=isenabled
        binding.swSex.isEnabled=isenabled
        binding.swOptional.isEnabled=isenabled
        binding.swUndefined.isEnabled=isenabled
        binding.swUndefinedDG14.isEnabled=isenabled
        binding.swUndefinedDG15.isEnabled=isenabled
        binding.swUndefinedDG16.isEnabled=isenabled
        binding.swPlaceRegistration.isEnabled=isenabled
        binding.swPlaceRegistration18.isEnabled=isenabled
        binding.swResidence.isEnabled=isenabled
        binding.swResidencePermit.isEnabled=isenabled
        binding.swOptionalsDetails.isEnabled=isenabled
    }

    private fun setPreferences() {
        binding.apply {
            sweID.isChecked = pref.getBoolean(Constants.READ_E_ID)
            swDocumentType.isChecked = pref.getBoolean(Constants.E_ID_DG1)
            swIssueState.isChecked = pref.getBoolean(Constants.E_ID_DG2)
            swDateOfExpiry.isChecked = pref.getBoolean(Constants.E_ID_DG3)
            swGiveName.isChecked = pref.getBoolean(Constants.E_ID_DG4)
            swFamilyName.isChecked = pref.getBoolean(Constants.E_ID_DG5)
            swPseudonym.isChecked = pref.getBoolean(Constants.E_ID_DG6)
            swAcademicTitle.isChecked = pref.getBoolean(Constants.E_ID_DG7)
            swDateOfBirth.isChecked = pref.getBoolean(Constants.E_ID_DG8)
            swPlaceOfBirth.isChecked = pref.getBoolean(Constants.E_ID_DG9)
            swNationality.isChecked = pref.getBoolean(Constants.E_ID_DG10)
            swSex.isChecked = pref.getBoolean(Constants.E_ID_DG11)
            swOptional.isChecked = pref.getBoolean(Constants.E_ID_DG12)
            swUndefined.isChecked = pref.getBoolean(Constants.E_ID_DG13)
            swUndefinedDG14.isChecked = pref.getBoolean(Constants.E_ID_DG14)
            swUndefinedDG15.isChecked = pref.getBoolean(Constants.E_ID_DG15)
            swUndefinedDG16.isChecked = pref.getBoolean(Constants.E_ID_DG16)
            swPlaceRegistration.isChecked = pref.getBoolean(Constants.E_ID_DG17)
            swPlaceRegistration18.isChecked = pref.getBoolean(Constants.E_ID_DG18)
            swResidence.isChecked = pref.getBoolean(Constants.E_ID_DG19)
            swResidencePermit.isChecked = pref.getBoolean(Constants.E_ID_DG20)
            swOptionalsDetails.isChecked = pref.getBoolean(Constants.E_ID_DG21)
        }
    }

    private fun setClickListeners() {
        binding.apply {
            sweID.setOnClickListener(this@eIDSettingFragment)
            swDocumentType.setOnClickListener(this@eIDSettingFragment)
            swIssueState.setOnClickListener(this@eIDSettingFragment)
            swDateOfExpiry.setOnClickListener(this@eIDSettingFragment)
            swGiveName.setOnClickListener(this@eIDSettingFragment)
            swFamilyName.setOnClickListener(this@eIDSettingFragment)
            swPseudonym.setOnClickListener(this@eIDSettingFragment)
            swAcademicTitle.setOnClickListener(this@eIDSettingFragment)
            swDateOfBirth.setOnClickListener(this@eIDSettingFragment)
            swPlaceOfBirth.setOnClickListener(this@eIDSettingFragment)
            swNationality.setOnClickListener(this@eIDSettingFragment)
            swSex.setOnClickListener(this@eIDSettingFragment)
            swOptional.setOnClickListener(this@eIDSettingFragment)
            swUndefined.setOnClickListener(this@eIDSettingFragment)
            swUndefinedDG14.setOnClickListener(this@eIDSettingFragment)
            swUndefinedDG15.setOnClickListener(this@eIDSettingFragment)
            swUndefinedDG16.setOnClickListener(this@eIDSettingFragment)
            swPlaceRegistration.setOnClickListener(this@eIDSettingFragment)
            swPlaceRegistration18.setOnClickListener(this@eIDSettingFragment)
            swResidence.setOnClickListener(this@eIDSettingFragment)
            swResidencePermit.setOnClickListener(this@eIDSettingFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.sweID -> {
                val isChecked = binding.sweID.isChecked
                setSwitchesEnabled(isChecked)
                pref.setBoolean(Constants.READ_E_ID, isChecked)
            }
            R.id.swDocumentType -> {
                val isChecked = binding.swDocumentType.isChecked
                pref.setBoolean(Constants.E_ID_DG1, isChecked)
            }
            R.id.swIssueState -> {
                val isChecked = binding.swIssueState.isChecked
                pref.setBoolean(Constants.E_ID_DG2, isChecked)
            }
            R.id.swDateOfExpiry -> {
                val isChecked = binding.swDateOfExpiry.isChecked
                pref.setBoolean(Constants.E_ID_DG3, isChecked)
            }
            R.id.swGiveName -> {
                val isChecked = binding.swGiveName.isChecked
                pref.setBoolean(Constants.E_ID_DG4, isChecked)
            }
            R.id.swFamilyName -> {
                val isChecked = binding.swFamilyName.isChecked
                pref.setBoolean(Constants.E_ID_DG5, isChecked)
            }
            R.id.swPseudonym -> {
                val isChecked = binding.swPseudonym.isChecked
                pref.setBoolean(Constants.E_ID_DG6, isChecked)
            }
            R.id.swAcademicTitle -> {
                val isChecked = binding.swAcademicTitle.isChecked
                pref.setBoolean(Constants.E_ID_DG7, isChecked)
            }
            R.id.swDateOfBirth -> {
                val isChecked = binding.swDateOfBirth.isChecked
                pref.setBoolean(Constants.E_ID_DG8, isChecked)
            }
            R.id.swPlaceOfBirth -> {
                val isChecked = binding.swPlaceOfBirth.isChecked
                pref.setBoolean(Constants.E_ID_DG9, isChecked)
            }
            R.id.swNationality -> {
                val isChecked = binding.swNationality.isChecked
                pref.setBoolean(Constants.E_ID_DG10, isChecked)
            }
            R.id.swSex -> {
                val isChecked = binding.swSex.isChecked
                pref.setBoolean(Constants.E_ID_DG11, isChecked)
            }
            R.id.swOptional -> {
                val isChecked = binding.swOptional.isChecked
                pref.setBoolean(Constants.E_ID_DG12, isChecked)
            }
            R.id.swUndefined -> {
                val isChecked = binding.swUndefined.isChecked
                pref.setBoolean(Constants.E_ID_DG13, isChecked)
            }
            R.id.swUndefinedDG14 -> {
                val isChecked = binding.swUndefinedDG14.isChecked
                pref.setBoolean(Constants.E_ID_DG14, isChecked)
            }
            R.id.swUndefinedDG15 -> {
                val isChecked = binding.swUndefinedDG15.isChecked
                pref.setBoolean(Constants.E_ID_DG15, isChecked)
            }
            R.id.swUndefinedDG16 -> {
                val isChecked = binding.swUndefinedDG16.isChecked
                pref.setBoolean(Constants.E_ID_DG16, isChecked)
            }
            R.id.swPlaceRegistration -> {
                val isChecked = binding.swPlaceRegistration.isChecked
                pref.setBoolean(Constants.E_ID_DG17, isChecked)
            }
            R.id.swPlaceRegistration18 -> {
                val isChecked = binding.swPlaceRegistration18.isChecked
                pref.setBoolean(Constants.E_ID_DG18, isChecked)
            }
            R.id.swResidence -> {
                val isChecked = binding.swResidence.isChecked
                pref.setBoolean(Constants.E_ID_DG19, isChecked)
            }
            R.id.swResidencePermit -> {
                val isChecked = binding.swResidencePermit.isChecked
                pref.setBoolean(Constants.E_ID_DG20, isChecked)
            }
            R.id.swOptionalsDetails -> {
                val isChecked = binding.swOptionalsDetails.isChecked
                pref.setBoolean(Constants.E_ID_DG21, isChecked)
            }
        }
    }
}