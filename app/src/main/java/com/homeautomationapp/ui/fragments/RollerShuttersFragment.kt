package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentRollerShuttersBinding
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.activities.MainActivity

class RollerShuttersFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentRollerShuttersBinding

    private lateinit var rollerShutter: Device.RollerShutter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRollerShuttersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment((activity as MainActivity),
                                    resources.getString(R.string.name_toolbar_frg_roller_shutters))
        getSelectedRollerShutterDevice()
        initializeViews()


        handleSliderListener()
        handleSaveButtonListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) displayCancellationDialog((activity as MainActivity),
                                                                        parentFragmentManager)
        return super.onOptionsItemSelected(item)
    }

    private fun handleSliderListener() {
        binding.slider.addOnChangeListener { _, value, _ ->
            updateMaterialTextViewRollerShuttersValue(value.toInt().toString())
        }
    }

    private fun updateMaterialTextViewRollerShuttersValue(value: String) {
        binding.currentRollerShutter.text = value
    }

    /**
     * Initializes all fragment views with selected roller shutter device values.
     */
    private fun initializeViews() {
        binding.slider.apply {
            rollerShutter.position?.let { this.setValues(it.toFloat()) }
            updateSliderActiveColor(true, this)
        }
        updateMaterialTextViewRollerShuttersValue(rollerShutter.position.toString())
        updateMaterialTextViewBackgroundColor(true, binding.currentRollerShutter)
    }

    private fun handleSaveButtonListener() {
        binding.button.setOnClickListener {
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    private fun getSelectedRollerShutterDevice() {
        rollerShutter = (activity as MainActivity).viewModel.selectedDevice as Device.RollerShutter
    }
}