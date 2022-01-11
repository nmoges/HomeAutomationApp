package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentHeatersBinding
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.activities.MainActivity

class HeatersFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentHeatersBinding

    private lateinit var heater: Device.Heater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHeatersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment((activity as MainActivity),
                                     resources.getString(R.string.name_toolbar_frg_heaters))
        getSelectedHeaterDevice()
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
            updateMaterialTextViewBackgroundColor(status, binding.currentTempHeater)
            updateMaterialTextViewDeviceStatus(status, binding.statusOnOff)
        }
    }

    private fun handleSliderListener() {
        binding.slider.addOnChangeListener { _, value, _ ->
            val temperature = getString(R.string.current_temp_heater, value.toInt())
            updateMaterialTextViewSelectedTemperature(temperature)
        }
    }

    private fun updateMaterialTextViewSelectedTemperature(temperature: String) {
        binding.currentTempHeater.text = temperature
    }

    /**
     * Initializes all fragments views with selected heater device values.
     */
    private fun initializeViews() {
        val status = heater.mode == "ON"
        // Slider
        binding.slider.apply {
            this.isEnabled = status
            updateSliderActiveColor(status, this)
            heater.temperature?.let { this.setValues(it.toFloat()) }
        }
        // Switch
        binding.switchStatus.isChecked = status
        // MaterialTexts
        updateMaterialTextViewDeviceStatus(status, binding.statusOnOff)
        updateMaterialTextViewBackgroundColor(status, binding.currentTempHeater)
        heater.temperature?.let { updateMaterialTextViewSelectedTemperature(it.toString()) }
    }

    private fun handleSaveButtonListener() {
        binding.button.setOnClickListener {
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    private fun getSelectedHeaterDevice() {
        heater = (activity as MainActivity).viewModel.selectedDevice as Device.Heater
    }
}