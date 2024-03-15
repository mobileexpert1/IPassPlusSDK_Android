package com.app.ipassplus.ui.menu.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.databinding.FragmentVerificationStatusBinding

class VerificationStatusFragment : Fragment(),View.OnClickListener {
    private val binding by lazy { FragmentVerificationStatusBinding.inflate(layoutInflater) }

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
        setClickListeners()
    }

    private fun setClickListeners(){
        binding.apply {
            ibBackArrowVS.setOnClickListener(this@VerificationStatusFragment)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.ibBackArrowVS -> {
                findNavController().popBackStack()
            }
        }
    }
}