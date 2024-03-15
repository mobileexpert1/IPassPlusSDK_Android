package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ipassplus.R
import com.ipassplus.databinding.FragmentDebugSettingBinding
import com.ipassplus.utils.Constants
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader

class DebugSettingFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentDebugSettingBinding
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
        val view=inflater.inflate(R.layout.fragment_debug_setting, container, false)
        binding=FragmentDebugSettingBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPref(requireContext())
        setClickListeners()
        setPreferences()
    }

    private fun setClickListeners() {
        binding.apply {
            rlReportEvent.setOnClickListener(this@DebugSettingFragment)
            ibBackArrowDS.setOnClickListener(this@DebugSettingFragment)
            swSaveEvent.setOnClickListener(this@DebugSettingFragment)
            swSaveImage.setOnClickListener(this@DebugSettingFragment)
            swSaveCroppedImages.setOnClickListener(this@DebugSettingFragment)
            swShowMetaData.setOnClickListener(this@DebugSettingFragment)
            swShowScenarioList.setOnClickListener(this@DebugSettingFragment)
        }
    }


    private fun setPreferences() {
        binding.apply {
            swSaveEvent.isChecked = pref.getBoolean(Constants.SAVE_EVENT_LOGS)
            swSaveImage.isChecked = pref.getBoolean(Constants.SAVE_IMAGES)
            swSaveCroppedImages.isChecked = pref.getBoolean(Constants.SAVE_CROPPED_IMAGES)
            swShowMetaData.isChecked = pref.getBoolean(Constants.SHOW_METADATA)
            swShowScenarioList.isChecked = pref.getBoolean(Constants.SHOW_LIST_SCENARIOS)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.rlReportEvent -> {
                findNavController().navigate(R.id.reportEventLogFragment)
            }
            R.id.ibBackArrowDS -> {
                findNavController().popBackStack()
            }

            R.id.swSaveEvent->{
                val isChecked = binding.swSaveEvent.isChecked
                DocumentReader.Instance().processParams().debugSaveLogs  = isChecked
                pref.setBoolean(Constants.SAVE_EVENT_LOGS, isChecked)
            }
            R.id.swSaveImage->{
                val isChecked = binding.swSaveImage.isChecked
                DocumentReader.Instance().processParams().debugSaveImages  = isChecked
                pref.setBoolean(Constants.SAVE_IMAGES, isChecked)
            }
            R.id.swSaveCroppedImages->{
                val isChecked = binding.swSaveCroppedImages.isChecked
                DocumentReader.Instance().processParams().debugSaveCroppedImages = isChecked
                pref.setBoolean(Constants.SAVE_CROPPED_IMAGES, isChecked)
            }


            R.id.swShowMetaData->{
                val isChecked = binding.swShowMetaData.isChecked
                DocumentReader.Instance().functionality().edit().setDisplayMetadata(isChecked).apply()
                pref.setBoolean(Constants.SHOW_METADATA, isChecked)
            }
            R.id.swShowScenarioList->{
                val isChecked = binding.swShowScenarioList.isChecked
                pref.setBoolean(Constants.SHOW_LIST_SCENARIOS, isChecked)
            }
        }
    }

}