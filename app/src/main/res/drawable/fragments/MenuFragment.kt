package com.ipassplus.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {


    private lateinit var binding: FragmentMenuBinding
    private lateinit var dialog: Dialog
    private lateinit var btnCancel: Button
    private lateinit var btnSubmit: Button


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

        binding.rlSettings.setOnClickListener {
            navigateToSettings()
        }

        dialog = Dialog(requireContext())

        binding.rlReview.setOnClickListener {
            dialog.setContentView(R.layout.rating_dialogbox)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.gravity = Gravity.CENTER
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
         //   dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(false)
            dialog.window?.setWindowAnimations(R.style.Base_Theme_IPassPlus)

            btnCancel = dialog.findViewById(R.id.btnCancel)
            btnSubmit = dialog.findViewById(R.id.btnSubmit)

            btnCancel.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
            }

            btnSubmit.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Sumbit", Toast.LENGTH_SHORT).show()
            }

            dialog.show()
        }

       /* binding.ibBackArrow.setOnClickListener(View.OnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, DashboardFragment())
                ?.commit()
        })*/


        binding.ibBackArrow.setOnClickListener {
            findNavController().popBackStack()
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
        }

        binding.rlAboutapp.setOnClickListener{
            findNavController().navigate(R.id.aboutAppFragment)
        }


        binding.rlShareApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            //  shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing my app!")
            val chooser = Intent.createChooser(shareIntent, "Share via")
            if (shareIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(requireContext(), "No apps available for sharing", Toast.LENGTH_SHORT).show()
            }
        }

        binding.rlContactUs.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.ipass-mena.com/privacypolicy"))
            startActivity(i)
        })
        binding.rlHelp.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.ipass-mena.com/privacypolicy"))
            startActivity(i)
        })
        binding.rlVisit.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.ipass-mena.com/privacypolicy"))
            startActivity(i)
        })
        binding.rlLegal.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.ipass-mena.com/privacypolicy"))
            startActivity(i)
        })

       binding.rlVerifivation.setOnClickListener({
           findNavController().navigate(R.id.verificationStatusFragment)
       })

        binding.rlViewongplay.setOnClickListener {
            openPlayStoreForReview()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

    }

    private fun setClickListeners() {
        binding.apply {

        }
    }
    private fun navigateToSettings() {
        findNavController().navigate(R.id.settingsFragment)
    }

    private fun openPlayStoreForReview() {
        val appPackageName = requireContext().packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (e: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }
}