package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipassplus.R
import com.ipassplus.databinding.FragmentEidSettingsBinding
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader


class EidSettingsFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentEidSettingsBinding.inflate(layoutInflater) }
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
            sweID.setOnClickListener(this@EidSettingsFragment)
            swDocumentType.setOnClickListener(this@EidSettingsFragment)
            swIssueState.setOnClickListener(this@EidSettingsFragment)
            swDateOfExpiry.setOnClickListener(this@EidSettingsFragment)
            swGiveName.setOnClickListener(this@EidSettingsFragment)
            swFamilyName.setOnClickListener(this@EidSettingsFragment)
            swPseudonym.setOnClickListener(this@EidSettingsFragment)
            swAcademicTitle.setOnClickListener(this@EidSettingsFragment)
            swDateOfBirth.setOnClickListener(this@EidSettingsFragment)
            swPlaceOfBirth.setOnClickListener(this@EidSettingsFragment)
            swNationality.setOnClickListener(this@EidSettingsFragment)
            swSex.setOnClickListener(this@EidSettingsFragment)
            swOptional.setOnClickListener(this@EidSettingsFragment)
            swUndefined.setOnClickListener(this@EidSettingsFragment)
            swUndefinedDG14.setOnClickListener(this@EidSettingsFragment)
            swUndefinedDG15.setOnClickListener(this@EidSettingsFragment)
            swUndefinedDG16.setOnClickListener(this@EidSettingsFragment)
            swPlaceRegistration.setOnClickListener(this@EidSettingsFragment)
            swPlaceRegistration18.setOnClickListener(this@EidSettingsFragment)
            swResidence.setOnClickListener(this@EidSettingsFragment)
            swResidencePermit.setOnClickListener(this@EidSettingsFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.sweID -> {
                val isChecked = binding.sweID.isChecked
                setSwitchesEnabled(isChecked)
                DocumentReader.Instance().rfidScenario().isReadEID = isChecked
                pref.setBoolean(Constants.READ_E_ID, isChecked)
            }
            R.id.swDocumentType -> {
                val isChecked = binding.swDocumentType.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG1 = isChecked
                pref.setBoolean(Constants.E_ID_DG1, isChecked)
            }
            R.id.swIssueState -> {
                val isChecked = binding.swIssueState.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG2 = isChecked
                pref.setBoolean(Constants.E_ID_DG2, isChecked)
            }
            R.id.swDateOfExpiry -> {
                val isChecked = binding.swDateOfExpiry.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG3 = isChecked
                pref.setBoolean(Constants.E_ID_DG3, isChecked)
            }
            R.id.swGiveName -> {
                val isChecked = binding.swGiveName.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG4 = isChecked
                pref.setBoolean(Constants.E_ID_DG4, isChecked)
            }
            R.id.swFamilyName -> {
                val isChecked = binding.swFamilyName.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG5 = isChecked
                pref.setBoolean(Constants.E_ID_DG5, isChecked)
            }
            R.id.swPseudonym -> {
                val isChecked = binding.swPseudonym.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG6 = isChecked
                pref.setBoolean(Constants.E_ID_DG6, isChecked)
            }
            R.id.swAcademicTitle -> {
                val isChecked = binding.swAcademicTitle.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG7 = isChecked
                pref.setBoolean(Constants.E_ID_DG7, isChecked)
            }
            R.id.swDateOfBirth -> {
                val isChecked = binding.swDateOfBirth.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG8 = isChecked
                pref.setBoolean(Constants.E_ID_DG8, isChecked)
            }
            R.id.swPlaceOfBirth -> {
                val isChecked = binding.swPlaceOfBirth.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG9 = isChecked
                pref.setBoolean(Constants.E_ID_DG9, isChecked)
            }
            R.id.swNationality -> {
                val isChecked = binding.swNationality.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG10 = isChecked
                pref.setBoolean(Constants.E_ID_DG10, isChecked)
            }
            R.id.swSex -> {
                val isChecked = binding.swSex.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG11 = isChecked
                pref.setBoolean(Constants.E_ID_DG11, isChecked)
            }
            R.id.swOptional -> {
                val isChecked = binding.swOptional.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG12 = isChecked
                pref.setBoolean(Constants.E_ID_DG12, isChecked)
            }
            R.id.swUndefined -> {
                val isChecked = binding.swUndefined.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG13 = isChecked
                pref.setBoolean(Constants.E_ID_DG13, isChecked)
            }
            R.id.swUndefinedDG14 -> {
                val isChecked = binding.swUndefinedDG14.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG14 = isChecked
                pref.setBoolean(Constants.E_ID_DG14, isChecked)
            }
            R.id.swUndefinedDG15 -> {
                val isChecked = binding.swUndefinedDG15.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG15 = isChecked
                pref.setBoolean(Constants.E_ID_DG15, isChecked)
            }
            R.id.swUndefinedDG16 -> {
                val isChecked = binding.swUndefinedDG16.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG16 = isChecked
                pref.setBoolean(Constants.E_ID_DG16, isChecked)
            }
            R.id.swPlaceRegistration -> {
                val isChecked = binding.swPlaceRegistration.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG17 = isChecked
                pref.setBoolean(Constants.E_ID_DG17, isChecked)
            }
            R.id.swPlaceRegistration18 -> {
                val isChecked = binding.swPlaceRegistration18.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG18 = isChecked
                pref.setBoolean(Constants.E_ID_DG18, isChecked)
            }
            R.id.swResidence -> {
                val isChecked = binding.swResidence.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG19 = isChecked
                pref.setBoolean(Constants.E_ID_DG19, isChecked)
            }
            R.id.swResidencePermit -> {
                val isChecked = binding.swResidencePermit.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG20 = isChecked
                pref.setBoolean(Constants.E_ID_DG20, isChecked)
            }
            R.id.swOptionalsDetails -> {
                val isChecked = binding.swOptionalsDetails.isChecked
                DocumentReader.Instance().rfidScenario().eIDDataGroups().isDG21 = isChecked
                pref.setBoolean(Constants.E_ID_DG21, isChecked)
            }
        }
    }

}