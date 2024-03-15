package com.app.ipassplus.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ipassplus.R
import com.app.ipassplus.ui.dashboard.adapter.ScenariosListAdapter
import com.app.ipassplus.ui.dashboard.model.ScenariosItemModel
import com.app.ipassplus.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private lateinit var adapter: ScenariosListAdapter
    private lateinit var rvitems:RecyclerView
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scenarioList = arrayListOf(
            ScenariosItemModel(R.drawable.full2,"Face Processing",getString(R.string.processing_scenario_for_obtaining_all_document_data)),
            ScenariosItemModel(R.drawable.bankkk, "Bank Card",getString(R.string.procesing_scenario_for_obtaning_bank_card_data)),
            ScenariosItemModel(R.drawable.mrz, "MRZ",getString(R.string.procesing_scenario_for_obtaning_mrz_n_data)),
            ScenariosItemModel(R.drawable.barcode, "Barcode",getString(R.string.procesing_scenario_for_obtaning_barcode_ndata)),
            ScenariosItemModel(R.drawable.visual, "Visual OCR",getString(R.string.procesing_scenario_for_obtaning_nvisaul_zone_ocr_results))
        )

         adapter = ScenariosListAdapter(scenarioList)
         binding.rvitems.setHasFixedSize(true)
         binding.rvitems.setLayoutManager(LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
         binding.rvitems.setAdapter(adapter)
    }

}