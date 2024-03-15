package com.app.ipassplus.ui.menu.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.databinding.FragmentMenuBinding

class MenuFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        binding = FragmentMenuBinding.bind(view)

        val dialog = Dialog(requireContext())
        binding.rlReview.setOnClickListener {
            dialog.setContentView(R.layout.rating_dialogbox)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.gravity = Gravity.CENTER
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
            dialog.setCancelable(false)
            dialog.window?.setWindowAnimations(R.style.Base_Theme_IPassPlus)

            val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
            val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmit)

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSubmit.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }
    private fun setClickListeners() {
        binding.apply {
            ibBackArrowMenu.setOnClickListener(this@MenuFragment)
            rlSettings.setOnClickListener(this@MenuFragment)
            rlVerifivation.setOnClickListener(this@MenuFragment)
            rlAboutapp.setOnClickListener(this@MenuFragment)
            //    rlReview.setOnClickListener(this@MenuFragment)
            rlShareApp.setOnClickListener(this@MenuFragment)
            rlContactUs.setOnClickListener(this@MenuFragment)
            rlHelp.setOnClickListener(this@MenuFragment)
            rlVisit.setOnClickListener(this@MenuFragment)
            rlViewongplay.setOnClickListener(this@MenuFragment)
            rlLegal.setOnClickListener(this@MenuFragment)
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ibBackArrowMenu -> {
                findNavController().popBackStack()
            }
            R.id.rlSettings -> {
                findNavController().navigate(R.id.settingFragment)
            }
            R.id.rlVerifivation -> {
                findNavController().navigate(R.id.verificationStatusFragment)
            }
            R.id.rlAboutapp -> {
                findNavController().navigate(R.id.aboutAppFragment)
            }
            R.id.rlShareApp -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val chooser = Intent.createChooser(shareIntent, "Share via")
                if (shareIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(chooser)
                } else {
                    Toast.makeText(requireContext(), "No apps available for sharing", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.rlContactUs -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CONTACT_US))
                startActivity(i)
            }
            R.id.rlHelp -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.HELP_CENTER))
                startActivity(i)
            }
            R.id.rlVisit -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.VISIT_WEBSITE))
                startActivity(i)
            }
            R.id.rlViewongplay -> {
                val appPackageName = requireContext().packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (e: android.content.ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }
            }
            R.id.rlLegal -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRIVACY_POLICY))
                startActivity(i)
            }
        }
    }
}