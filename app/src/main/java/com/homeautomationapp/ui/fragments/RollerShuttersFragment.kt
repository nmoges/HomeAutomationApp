package com.homeautomationapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentRollerShuttersBinding
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogCancellation

class RollerShuttersFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentRollerShuttersBinding

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
        handleSliderListener()
        handleSaveButtonListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) displayCancellationDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun handleSliderListener() {
        binding.slider.addOnChangeListener { _, value, _ ->
            updateMaterialTextViewSelectedValue(value.toInt().toString())
        }
    }

    override fun updateMaterialTextViewSelectedValue(selectedValue: String) {
        binding.currentRollerShutter.text = selectedValue
    }

    private fun handleSaveButtonListener() {
        binding.button.setOnClickListener {
            displayToastSaveMessage(context, resources.getString(R.string.toast_modifications_applied))
            (activity as MainActivity).removeFragmentFromBackStack()
        }
    }

    fun displayCancellationDialog() {
        DialogCancellation {
            (activity as MainActivity).removeFragmentFromBackStack()
        }.show(parentFragmentManager, AppConstants.TAG_DIALOG_CANCELLATION)
    }
}