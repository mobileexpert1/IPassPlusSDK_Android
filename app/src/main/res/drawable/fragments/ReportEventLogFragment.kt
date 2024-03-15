package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ipassplus.R
import com.ipassplus.databinding.FragmentReportEventLogBinding

class ReportEventLogFragment : Fragment() {
    private lateinit var binding:FragmentReportEventLogBinding

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
        val view=inflater.inflate(R.layout.fragment_report_event_log, container, false)
        binding=FragmentReportEventLogBinding.bind(view)

        binding.ibBackArrowReport.setOnClickListener{
            findNavController().popBackStack()
        }

//        binding.ibBackArrowReport.setOnClickListener(View.OnClickListener {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.nav_host_fragment, AdvanceSettingsFragment())
//                ?.commit()
//        })
        return view
    }

}