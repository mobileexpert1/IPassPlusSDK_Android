package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ipassplus.R
import com.ipassplus.adapter.RfidSettingsAdapter
import com.ipassplus.databinding.FragmentRfidSettingsBinding

class RfidSettingsFragment : Fragment(), View.OnClickListener {

    private val binding by lazy { FragmentRfidSettingsBinding.inflate(layoutInflater) }
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
            ibBackArrowAS.setOnClickListener(this@RfidSettingsFragment)
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
            R.id.ibBackArrowAS -> {
                findNavController().popBackStack()
            }
        }
    }
}