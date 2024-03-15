package com.ipassplus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ipassplus.R
import com.ipassplus.databinding.FragmentMenuBinding
import com.ipassplus.databinding.FragmentVerificationStatusBinding


class VerificationStatusFragment : Fragment() {

    private lateinit var binding:FragmentVerificationStatusBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_verification_status, container, false)

        binding = FragmentVerificationStatusBinding.bind(view)


        binding.ibBackArrowVS.setOnClickListener {
            findNavController().popBackStack()
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
        }
        return view
    }


}