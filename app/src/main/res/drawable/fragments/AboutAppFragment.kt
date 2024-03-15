package com.ipassplus.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ipassplus.R
import com.ipassplus.databinding.FragmentAboutAppBinding


class AboutAppFragment : Fragment() {
    private lateinit var binding:FragmentAboutAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutAppBinding.inflate(inflater, container, false)


       /* binding.ibBackArrowAbout.setOnClickListener{
            findNavController().popBackStack()
        }*/

        binding.ibBackArrowAbout.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })

        binding.IvFaceBook.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"))
            startActivity(i)
        })

        binding.IvLinkedln.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/"))
            startActivity(i)
        })


        binding.IvTwitter.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/?lang=en"))
            startActivity(i)
        })

        binding.IvGithub.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"))
            startActivity(i)
        })

        return binding.root
    }

}