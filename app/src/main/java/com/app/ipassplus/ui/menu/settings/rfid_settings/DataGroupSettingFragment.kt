package com.app.ipassplus.ui.menu.settings.rfid_settings
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentDataGroupSettingBinding
import com.app.ipassplus.ui.menu.settings.adapter.DataGroupsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DataGroupSettingFragment : Fragment() {
    private val binding by lazy { FragmentDataGroupSettingBinding.inflate(layoutInflater) }
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


}