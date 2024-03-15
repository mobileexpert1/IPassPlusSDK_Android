package com.ipassplus.fragments

import android.animation.ValueAnimator
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ipassplus.adapter.MainViewAdapter
import com.ipassplus.databinding.FragmentDashboardBinding
import com.ipassplus.utils.Base
import com.regula.documentreader.api.results.DocumentReaderResults


class DashboardFragment : Fragment(), View.OnClickListener{


    private lateinit var binding: FragmentDashboardBinding
    private var isSelected: Boolean = false
    lateinit var mainViewAdapter: MainViewAdapter
    var isInitializedByBleDevice: Boolean = false
    var modelList = ArrayList<Base>()
    private var currentScenario: String = ""
    private var isAnimationStarted: Boolean = false
    private var mTimerAnimator: ValueAnimator? = null
    var isDataEncryptionEnabled = false
    private var initDialog: AlertDialog? = null
    val ENCRYPTED_RESULT_SERVICE = "https://api.regulaforensics.com/api/process"
    var isRfidEnabled = false
    var results: DocumentReaderResults? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }


    fun init() {


    }


    override fun onClick(v: View?) {



     /*   binding.rlFullProcessing.setOnClickListener {
            // Get the current state of isSelected from the tag, defaulting to false
            isSelected = (binding.rlFullProcessing.tag as? Boolean) ?: false

            Toast.makeText(requireContext(), "Shoooooowwww", Toast.LENGTH_SHORT).show()

            val rvData = mutableListOf<Base>()
            rvData.add(Section("Default"))
            rvData.add(Scan("Default (showScanner)", resetFunctionality = false) {
                Helpers.setFunctionality(functionality)
            })


//            if (isSelected) {
//                binding.rlFullProcessing.setBackgroundResource(R.drawable.bg_purple)
//                binding.tvFullProcess.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                binding.tvdescreption.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                binding.ivImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
//            } else {
//                binding.rlFullProcessing.setBackgroundResource(R.drawable.mainviewbg)
//                binding.tvFullProcess.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//                binding.tvdescreption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//                binding.ivImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.purple))
//            }

            binding.rlFullProcessing.tag = !isSelected

            binding.rlBankCard.setBackgroundResource(R.drawable.mainviewbg)
            binding.rlBankCard.tag = false
        }

        binding.rlBankCard.setOnClickListener {
            val isSelected = (binding.rlBankCard.tag as? Boolean) ?: false

            if (isSelected) {
                binding.rlBankCard.setBackgroundResource(R.drawable.bg_purple)
                binding.tvBankCard.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvdescreptionBank.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.ivImageBank.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                binding.rlBankCard.setBackgroundResource(R.drawable.mainviewbg)
                binding.tvBankCard.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.tvdescreptionBank.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.ivImageBank.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.purple
                    )
                )
            }
            binding.rlBankCard.tag = !isSelected
            binding.rlFullProcessing.setBackgroundResource(R.drawable.mainviewbg)
            binding.rlFullProcessing.tag = false
        }*/
    }
}
