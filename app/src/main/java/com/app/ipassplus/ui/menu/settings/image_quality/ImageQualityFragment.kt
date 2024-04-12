package com.app.ipassplus.ui.menu.settings.image_quality
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.app.ipassplus.R
import com.app.ipassplus.Utils.Constants
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.databinding.FragmentImageQualityBinding

class ImageQualityFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentImageQualityBinding
    private lateinit var pref: SharedPref

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
        val view = inflater.inflate(R.layout.fragment_image_quality, container, false)
        binding = FragmentImageQualityBinding.bind(view)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPref(requireContext())
        setClickListeners()
        setPreferences()
        binding.ibBackArrowIQ.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })

        binding.rlThershold.setOnClickListener {
            ThresholdDialog(getString(R.string.dpi_threshold), pref.getString(Constants.DPI_THRESHOLD))
        }
        binding.rlAngleThershold.setOnClickListener {
            ThresholdDialog(getString(R.string.angle_threshold), pref.getString(Constants.ANGLE_THRESHOLD))
        }
        binding.rlDocumentPosition.setOnClickListener {
            ThresholdDialog(getString(R.string.document_position_indent), pref.getString(Constants.DOCUMENT_POSITION))
        }


    }

    @SuppressLint("SetTextI18n")
    private fun ThresholdDialog(titleText: String, value: String) {
        val dialog = Dialog(requireContext())
        var saveImageData=false
        dialog.setContentView(R.layout.custom_setting_dialog)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.window?.setWindowAnimations(R.style.Base_Theme_IPassPlus)

        val cancel = dialog.findViewById<TextView>(R.id.tvCancel)
        val save = dialog.findViewById<TextView>(R.id.tvSave)

        val text=dialog.findViewById<TextView>(R.id.tvTimeOut)
        val etTimeout = dialog.findViewById<EditText>(R.id.etTimeout)
        text.text = titleText
        etTimeout.setText(value)


        cancel.setOnClickListener {
            saveImageData=false
            dialog.dismiss()
        }

        save.setOnClickListener {
            saveImageData=true
            dialog.dismiss()
        }

        dialog.show()

        dialog.setOnDismissListener {
            if (saveImageData) {
                val selectedText = etTimeout.text.toString()
                if (selectedText.isNotEmpty()) {
                    when (titleText) {
                        getString(R.string.dpi_threshold) -> {
                            binding.tvDpiThresholdSet.text = selectedText
                            pref.setString(Constants.DPI_THRESHOLD, selectedText)
                        }
                        getString(R.string.angle_threshold) -> {
                            binding.tvAngleThresholdSet.text = selectedText

                            pref.setString(Constants.ANGLE_THRESHOLD, selectedText)
                        }
                        getString(R.string.document_position_indent) -> {
                            binding.tvDocumentPositionSet.text = selectedText
                            pref.setString(Constants.DOCUMENT_POSITION, selectedText)
                        }
                    }
                }
            }
        }
    }
    private fun setClickListeners(){
        binding.swGlares.setOnClickListener(this)
        binding.swFocus.setOnClickListener(this)
        binding.swColor.setOnClickListener(this)
    }

    private fun setPreferences() {
        binding.apply {
            swGlares.isChecked = pref.getBoolean(Constants.GLARES)
            swFocus.isChecked = pref.getBoolean(Constants.FOCUS)
            swColor.isChecked = pref.getBoolean(Constants.COLOR)
            tvDpiThresholdSet.text=pref.getString(Constants.DPI_THRESHOLD)
            tvAngleThresholdSet.text=pref.getString(Constants.ANGLE_THRESHOLD)
            tvDocumentPositionSet.text=pref.getString(Constants.DOCUMENT_POSITION)
        }
    }


    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.swGlares->{
                val isChecked = binding.swGlares.isChecked
                pref.setBoolean(Constants.GLARES, isChecked)
            }
            R.id.swFocus->{
                val isChecked = binding.swFocus.isChecked
                pref.setBoolean(Constants.FOCUS, isChecked)
            }
            R.id.swColor->{
                val isChecked = binding.swColor.isChecked
                pref.setBoolean(Constants.COLOR, isChecked)
            }

        }
    }
}