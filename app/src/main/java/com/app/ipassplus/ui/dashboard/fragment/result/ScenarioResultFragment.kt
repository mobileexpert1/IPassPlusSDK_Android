package com.app.ipassplus.ui.dashboard.fragment.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentScenarioResultBinding
import com.app.ipassplus.ui.dashboard.fragment.result.adapter.ScenarioDataAdapter
import com.app.ipassplus.ui.dashboard.fragment.result.adapter.ScenariosResultAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class ScenarioResultFragment : Fragment() {
    val binding by lazy { FragmentScenarioResultBinding.inflate(layoutInflater) }
    lateinit var adapter: ScenariosResultAdapter
    private lateinit var view_pager: ViewPager2
    private lateinit var indicator: DotsIndicator
    private var adapters: ScenarioDataAdapter? = null


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
     //   return inflater.inflate(R.layout.fragment_scenario_result, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



   /*     adapter = ScenariosResultAdapter(childFragmentManager)
        adapter.addFragment(ScenarioResultFragment())
        adapter.addFragment(TextDataFragment())
        adapter.addFragment(ImageListFragment())
        adapter.addFragment(ChecksDetailsFragment())
        view_pager.adapter = adapter
        indicator.setViewPager(view_pager)*/


        /*adapters = ScenarioDataAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = adapters

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> { tab.text = "OVERALL RESULT" }
                1 -> { tab.text = "TEXT DATA" }
                2 -> { tab.text = "IMAGES" }
                3 -> { tab.text = "AUTH" }
                4 -> { tab.text = "CHECKS" }
            }
        }.attach()*/
    }

}