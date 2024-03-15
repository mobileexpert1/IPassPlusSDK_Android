package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.ipassplus.R
import com.ipassplus.adapter.BandDeatilsAdapter
import com.ipassplus.adapter.VerificationAdapter
import com.ipassplus.databinding.FragmentBandDetailsViewPagerBinding
import com.ipassplus.databinding.FragmentPersonalDetailBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.results
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class BandDetailsViewPagerFragment : Fragment() {

  lateinit var binding:FragmentBandDetailsViewPagerBinding
    private lateinit var view_pager: ViewPager
    private lateinit var indicator: DotsIndicator
    lateinit var adapter: BandDeatilsAdapter

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
        val view = inflater.inflate(R.layout.fragment_band_details_view_pager, container, false)
        binding = FragmentBandDetailsViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BandDeatilsAdapter(results,childFragmentManager, 3)
        binding.bandViewPager.adapter = adapter
        binding.dotsIndicator.setViewPager(binding.bandViewPager)
    }
}