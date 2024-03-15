package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ipassplus.R
import com.ipassplus.adapter.DataGroupsAdapter
import com.ipassplus.adapter.RfidSettingsAdapter
import com.ipassplus.databinding.FragmentDataGroupsSettingsBinding

class DataGroupsSettingsFragment : Fragment() {

    private val binding by lazy { FragmentDataGroupsSettingsBinding.inflate(layoutInflater) }
    private var adapter: DataGroupsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DataGroupsAdapter(this)
        val viewPager = binding.vpDataGroups
        viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vpDataGroups) { tab, position ->
            when(position) {
                0 -> { tab.text = getString(R.string.epassport) }
                1 -> { tab.text = getString(R.string.eid) }
                2 -> { tab.text = getString(R.string.edl) }
            }
        }.attach()
    }

//    }
}