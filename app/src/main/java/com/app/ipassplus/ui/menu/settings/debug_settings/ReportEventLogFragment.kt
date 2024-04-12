package com.app.ipassplus.ui.menu.settings.debug_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentReportEventLogBinding

class ReportEventLogFragment : Fragment(),View.OnClickListener {

    private val binding by lazy { FragmentReportEventLogBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListners()
    }

    fun setOnClickListners(){
        binding.apply {
            ibBackArrowReport.setOnClickListener(this@ReportEventLogFragment)
        }
    }

    override fun onClick(p0: View?) {
      when(p0?.id){
          R.id.ibBackArrowReport -> {
              findNavController().popBackStack()
          }
      }
    }

}