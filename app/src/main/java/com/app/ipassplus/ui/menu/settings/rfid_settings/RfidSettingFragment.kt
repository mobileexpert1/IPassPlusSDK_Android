package com.app.ipassplus.ui.menu.settings.rfid_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentRfidSettingBinding
import com.app.ipassplus.ui.menu.settings.adapter.RfidSettingsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RfidSettingFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentRfidSettingBinding.inflate(layoutInflater) }
    private var adapter: RfidSettingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ibBackArrowRF.setOnClickListener(this@RfidSettingFragment)
        }

        adapter = RfidSettingsAdapter(this)
        val viewPager = binding.vpAdvancedSettings
        viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vpAdvancedSettings) { tab, position ->
            when(position) {
                0 -> { tab.text = resources.getString(R.string.authentication) }
                1 -> { tab.text = resources.getString(R.string.data_groups) }
            }
        }.attach()
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.ibBackArrowRF -> {
                findNavController().popBackStack()
            }
        }
    }


}