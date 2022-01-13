package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentHeatersBinding
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogCancellation

/**
 * Fragment displaying a heater device information.
 */
class HeatersFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentHeatersBinding

    private lateinit var heater: Device.Heater

    private var dialogCancel: DialogCancellation? = null

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
        getSelectedHeaterDevice()
        initializeToolbarForFragment((activity as MainActivity), heater.deviceName)
        initializeViews()
        handleSwitchListener()
        handleSliderListener()
        handleSaveButtonListener()
        savedInstanceState?.let { restoreDialogCancellation(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) displayCancellationDialog()
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
            saveNewHeaterValues()
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    /**
     * Get heater device stored in view model.
     */
    private fun getSelectedHeaterDevice() {
        heater = (activity as MainActivity).viewModel.selectedDevice as Device.Heater
    }

    /**
     * Sends heater updates parameters to view model for database storage.
     */
    private fun saveNewHeaterValues() {
        heater.apply {
            mode = if (binding.switchStatus.isChecked) "ON" else "OFF"
            temperature = binding.slider.values[0].toInt()
        }
        (activity as MainActivity).viewModel.updateDevice(heater)
    }

    /**
     * Allows dialog display restoration after a configuration change.
     */
    private fun restoreDialogCancellation(savedInstanceState: Bundle) {
        if (savedInstanceState.getBoolean("dialog_cancel")) {
            if (parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_CANCELLATION) != null) {
                // Get previous instance (before configuration change) and reinitialize properties
                dialogCancel = parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_CANCELLATION)
                        as DialogCancellation
                dialogCancel?.apply {
                    callbackDialog = { (activity as MainActivity).removeFragmentFromBackStack() }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            dialogCancel?.let { this.putBoolean("dialog_cancel", it.showsDialog) }
        }
    }

    fun displayCancellationDialog() {
        dialogCancel = DialogCancellation {
            (activity as MainActivity).removeFragmentFromBackStack()
        }
        dialogCancel?.show(parentFragmentManager, AppConstants.TAG_DIALOG_CANCELLATION)
    }
}