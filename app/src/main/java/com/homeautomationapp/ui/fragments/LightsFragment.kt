package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentLightsBinding
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogCancellation

class LightsFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentLightsBinding

    private lateinit var light: Device.Light

    private var dialogCancel: DialogCancellation? = null

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
        getSelectedLightDevice()
        initializeToolbarForFragment((activity as MainActivity), light.deviceName)
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
            saveNewLightValues()
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    private fun getSelectedLightDevice() {
        light = (activity as MainActivity).viewModel.selectedDevice as Device.Light
    }

    private fun saveNewLightValues() {
        light.apply {
            mode = if (binding.switchStatus.isChecked) "ON" else "OFF"
            luminosity = binding.slider.values[0].toInt()
        }
        (activity as MainActivity).viewModel.updateDevice(light)
    }

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
            dialogCancel?.let { this.putBoolean("dialog_cancel", it.showsDialog)}
        }
    }

    fun displayCancellationDialog() {
        dialogCancel = DialogCancellation {
            (activity as MainActivity).removeFragmentFromBackStack()
        }
        dialogCancel?.show(parentFragmentManager, AppConstants.TAG_DIALOG_CANCELLATION)
    }
}