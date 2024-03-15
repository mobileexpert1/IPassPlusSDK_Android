package com.app.ipassplus.ui.menu.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.databinding.FragmentAboutAppBinding

class AboutAppFragment : Fragment(),View.OnClickListener {
    private val binding by lazy { FragmentAboutAppBinding.inflate(layoutInflater) }


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
         setClickListeners()
    }
    private fun setClickListeners(){
        binding.apply {
            ibBackArrowAbout.setOnClickListener(this@AboutAppFragment)
            IvFaceBook.setOnClickListener(this@AboutAppFragment)
            IvLinkedln.setOnClickListener(this@AboutAppFragment)
            IvTwitter.setOnClickListener(this@AboutAppFragment)
            IvGithub.setOnClickListener(this@AboutAppFragment)
        }
    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.ibBackArrowAbout -> {
               findNavController().popBackStack()
           }
           R.id.IvFaceBook -> {
               val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FACEBOOK))
               startActivity(i)
           }
           R.id.IvLinkedln -> {
               val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LINKEDLN))
               startActivity(i)
           }
           R.id.IvTwitter -> {
               val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER))
               startActivity(i)
           }
           R.id.IvGithub -> {
               val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB))
               startActivity(i)
           }

       }
    }

}