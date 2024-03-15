package com.ipassplus.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipassplus.R
import com.ipassplus.adapter.SettingBottomSheetAdapter
import com.ipassplus.databinding.BottomsheetSettingsBinding
import com.ipassplus.databinding.FragmentAdvanceSettingsBinding
import com.ipassplus.databinding.FragmentAuthenticationSettingsBinding
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.enums.CameraMode
import com.regula.documentreader.api.enums.CaptureMode
import com.regula.documentreader.api.enums.eRFID_AccessControl_ProcedureType
import com.regula.documentreader.api.enums.eRFID_AuthenticationProcedureType
import com.regula.documentreader.api.enums.eRFID_Password_Type
import com.regula.documentreader.api.enums.eRFID_SDK_ProfilerType

class AuthenticationSettingsFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentAuthenticationSettingsBinding.inflate(layoutInflater) }
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
        setPreferenes()


        binding.rlKeyValues.setOnClickListener {
            AuthVauleDialog(getString(R.string.values), pref.getString(Constants.AUTH_VALUE))
        }


        binding.rlAuthenticationType.setOnClickListener {
            val list = arrayListOf(
                "Standard inspection procedure",
                "Advance inspection procedure",
                "General inspection procedure",
            )
            val selectedItem = pref.getString(Constants.AUTHENTICATION_PROCEDURE_TYPE)
            AuthbottomSheet("Authentication procedure type", list, selectedItem)
        }

        binding.rlBasicSecurity.setOnClickListener {
            val list = arrayListOf(
                "BAC",
                "PACE",
            )
            val selectedItem = pref.getString(Constants.BASIC_SECURITY_MESSAGING_PROCEDURE)
            AuthbottomSheet("Basic Security Messaging procedure", list, selectedItem)
        }
        binding.rlDataAccessKey.setOnClickListener {
            val list = arrayListOf(
                "MRZ",
                "CAN",
                "PIN",
                "PUK",
                "SAI",
            )
            val selectedItem = pref.getString(Constants.DATA_ACCESS_KEY)
            AuthbottomSheet("Data access key", list, selectedItem)
        }
        binding.rlProfileType.setOnClickListener {
            val list = arrayListOf(
                "Doc 9303,6th edition,2006",
                "LDS and PKI Maintenance,v2.0,May 21,",
            )
            val selectedItem = pref.getString(Constants.PROFILE_TYPE)
            AuthbottomSheet("Profile type", list, selectedItem)
        }

    }

    private fun setPreferenes() {
        binding.apply {
            tvBasicSecurityType.text=pref.getString(Constants.BASIC_SECURITY_MESSAGING_PROCEDURE)
            tvDataAccessType.text=pref.getString(Constants.DATA_ACCESS_KEY)
            swDsSwitch.isChecked = pref.getBoolean(Constants.PRIORITY_OF_USING_DS_CERTIFICATES)
            swCscaSwitch.isChecked = pref.getBoolean(Constants.USE_OF_EXTERNAL_CSCA_CERTIFICATES)
            swPkdSwitch.isChecked = pref.getBoolean(Constants.TRUST_PKD_CERTIFICATES)
            swPASwitch.isChecked = pref.getBoolean(Constants.PASSIVE_AUTHENTICATION)
            swPerformSwitch.isChecked = pref.getBoolean(Constants.DO_NOT_PERFORM_AA_AFTER_CA)
            tvValueSet.text=pref.getString(Constants.AUTH_VALUE)
            swProtocolSwitch.isChecked = pref.getBoolean(Constants.STRICT_ISO_PROTOCOL)
        }
    }

    private fun setClickListeners() {
        binding.apply {
            swDsSwitch.setOnClickListener(this@AuthenticationSettingsFragment)
            swCscaSwitch.setOnClickListener(this@AuthenticationSettingsFragment)
            swPkdSwitch.setOnClickListener(this@AuthenticationSettingsFragment)
            swPASwitch.setOnClickListener(this@AuthenticationSettingsFragment)
            swPerformSwitch.setOnClickListener(this@AuthenticationSettingsFragment)
            swProtocolSwitch.setOnClickListener(this@AuthenticationSettingsFragment)
        }
    }


    private fun AuthbottomSheet(title: String, dataList: List<String>, selectedItem: String){
        var selectedValue: String = selectedItem
        val inflater = LayoutInflater.from(requireContext())
        val bottomSheeBinding = BottomsheetSettingsBinding.inflate(inflater)
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
                "Authentication procedure type" -> {
                    when(title) {
                        getString(R.string.standard_inspection_procedure) -> {
                            DocumentReader.Instance().rfidScenario().setAuthProcType(
                                eRFID_AuthenticationProcedureType.aptStandard)
                        }
                        getString(R.string.advance_inspection_procedure) -> {
                            DocumentReader.Instance().rfidScenario().setAuthProcType(
                                eRFID_AuthenticationProcedureType.aptAdvanced)
                        }
                        getString(R.string.general_inspection_procedure) -> {
                            DocumentReader.Instance().rfidScenario().setAuthProcType(
                                eRFID_AuthenticationProcedureType.aptGeneral)

                        }
                    }
                    pref.setString(Constants.AUTHENTICATION_PROCEDURE_TYPE, selectedValue)
                }

                "Basic Security Messaging procedure" -> {
                    binding.tvBasicSecurityType.setText(selectedValue)
                    when(title) {
                        getString(R.string.bac) -> {
                            DocumentReader.Instance().rfidScenario().setBaseSMProcedure(eRFID_AccessControl_ProcedureType.ACPT_BAC)
                        }
                        getString(R.string.pacee) -> {
                            DocumentReader.Instance().rfidScenario().setBaseSMProcedure(eRFID_AccessControl_ProcedureType.ACPT_PACE)
                        }
                    }
                    pref.setString(Constants.BASIC_SECURITY_MESSAGING_PROCEDURE, selectedValue)
                }

                "Data access key" -> {
                    binding.tvDataAccessType.setText(selectedValue)
                    when(title) {
                        getString(R.string.mrzs) -> {
                              DocumentReader.Instance().rfidScenario().setPacePasswordType(
                                 eRFID_Password_Type.PPT_MRZ)
                        }
                        getString(R.string.can) -> {
                            DocumentReader.Instance().rfidScenario().setPacePasswordType(
                                eRFID_Password_Type.PPT_CAN)
                        }
                        getString(R.string.pin) -> {
                            DocumentReader.Instance().rfidScenario().setPacePasswordType(
                                eRFID_Password_Type.PPT_PIN)
                        }
                        getString(R.string.puk) -> {
                            DocumentReader.Instance().rfidScenario().setPacePasswordType(
                                eRFID_Password_Type.PPT_PUK)
                        }
                        getString(R.string.sai) -> {
                            DocumentReader.Instance().rfidScenario().setPacePasswordType(
                                eRFID_Password_Type.PPT_SAI)
                        }
                    }
                    pref.setString(Constants.DATA_ACCESS_KEY, selectedValue)
                }
                "Profile type" -> {
                    when(title) {
                        getString(R.string.doc_9303_6th_edition_2006) -> {
                            DocumentReader.Instance().rfidScenario().setProfilerType(
                                eRFID_SDK_ProfilerType.SPT_DOC_9303_EDITION_2006)
                        }
                        getString(R.string.lds_and_pki_maintenance_v2_0_may_21) -> {
                            DocumentReader.Instance().rfidScenario().setProfilerType(
                                eRFID_SDK_ProfilerType.SPT_DOC_9303_LDS_PKI_MAINTENANCE)
                        }
                    }
                    pref.setString(Constants.PROFILE_TYPE, selectedValue)
                }
            }
        }
    }

    private fun AuthVauleDialog(titleText: String, value: String) {
        val dialog = Dialog(requireContext())
        var savedValue=false
        dialog.setContentView(R.layout.advance_setting__custom_dialog)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.window?.setWindowAnimations(R.style.Base_Theme_IPassPlus)

        val cancel = dialog.findViewById<TextView>(R.id.tvCancel)
        val save = dialog.findViewById<TextView>(R.id.tvSave)

        val text=dialog.findViewById<TextView>(R.id.tvTimeOut)
        val etTimeout = dialog.findViewById<EditText>(R.id.etTimeout)
        text.text = titleText
        etTimeout.setText(value)


        cancel.setOnClickListener {
            savedValue=false
            dialog.dismiss()
        }

        save.setOnClickListener {
            savedValue=true
            dialog.dismiss()
        }

        dialog.show()

        dialog.setOnDismissListener {
            if (savedValue) {
                val selectedText = etTimeout.text.toString()
                if (selectedText.isNotEmpty()) {
                    when (titleText) {
                        getString(R.string.values) -> {
                            binding.tvValueSet.text = selectedText
                            pref.setString(Constants.AUTH_VALUE, selectedText)
                        }
                    }
                }
            }
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.swDsSwitch -> {
                val isChecked = binding.swDsSwitch.isChecked
                DocumentReader.Instance().rfidScenario().isPkdDSCertPriority = isChecked
                pref.setBoolean(Constants.PRIORITY_OF_USING_DS_CERTIFICATES, isChecked)
            }
            R.id.swCscaSwitch -> {
                val isChecked = binding.swCscaSwitch.isChecked
                DocumentReader.Instance().rfidScenario().isPkdUseExternalCSCA = isChecked
                pref.setBoolean(Constants.USE_OF_EXTERNAL_CSCA_CERTIFICATES, isChecked)
            }
            R.id.swPkdSwitch -> {
                val isChecked = binding.swPkdSwitch.isChecked
                DocumentReader.Instance().rfidScenario().isTrustedPKD = isChecked
                pref.setBoolean(Constants.TRUST_PKD_CERTIFICATES, isChecked)
            }
            R.id.swPASwitch -> {
                val isChecked = binding.swPASwitch.isChecked
                DocumentReader.Instance().rfidScenario().isPassiveAuth = isChecked
                pref.setBoolean(Constants.PASSIVE_AUTHENTICATION, isChecked)
            }
            R.id.swPerformSwitch -> {
                val isChecked = binding.swPerformSwitch.isChecked
                DocumentReader.Instance().rfidScenario().isSkipAA = isChecked
                pref.setBoolean(Constants.DO_NOT_PERFORM_AA_AFTER_CA, isChecked)
            }
            R.id.swProtocolSwitch -> {
                val isChecked = binding.swProtocolSwitch.isChecked
                DocumentReader.Instance().rfidScenario().isStrictProcessing = isChecked
                pref.setBoolean(Constants.STRICT_ISO_PROTOCOL, isChecked)
            }
        }
    }

}