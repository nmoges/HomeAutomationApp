package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentLightsBinding
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.activities.MainActivity

class LightsFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentLightsBinding

    private lateinit var light: Device.Light

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment((activity as MainActivity),
                                    resources.getString(R.string.name_toolbar_frg_lights))
        getSelectedLightDevice()
        initializeViews()
        handleSwitchListener()
        handleSliderListener()
        handleSaveButtonListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) displayCancellationDialog((activity as MainActivity),
                                                                        parentFragmentManager)
        return super.onOptionsItemSelected(item)
    }

    private fun handleSwitchListener() {
        binding.switchStatus.setOnCheckedChangeListener { _, status ->
            updateSliderStatus(status, binding.slider)
            updateMaterialTextViewBackgroundColor(status, binding.currentLuminosity)
            updateMaterialTextViewDeviceStatus(status, binding.statusOnOff)
        }
    }

    private fun handleSliderListener() {
        binding.slider.addOnChangeListener { _, value, _ ->
            updateMaterialTextViewSelectedLuminosity(value.toInt().toString())
        }
    }

    private fun updateMaterialTextViewSelectedLuminosity(luminosity: String) {
        binding.currentLuminosity.text = luminosity
    }

    /**
     * Initializes all fragment views with selected light device values.
     */
    private fun initializeViews() {
        val status = light.mode == "ON"
        // Slider
        binding.slider.apply {
            this.isEnabled = status
            updateSliderActiveColor(status, this)
            light.luminosity?.let { this.setValues(it.toFloat()) }
        }
        // Switch
        binding.switchStatus.isChecked = status
        // MaterialTexts
        updateMaterialTextViewDeviceStatus(status, binding.statusOnOff)
        updateMaterialTextViewBackgroundColor(status, binding.currentLuminosity)
        light.luminosity?.let { updateMaterialTextViewSelectedLuminosity(it.toString()) }
    }

    private fun handleSaveButtonListener() {
        binding.button.setOnClickListener {
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    private fun getSelectedLightDevice() {
        light = (activity as MainActivity).viewModel.selectedDevice as Device.Light
    }
}