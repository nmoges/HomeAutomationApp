package com.homeautomationapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentListDevicesBinding
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.adapters.ListDevicesAdapter
import com.homeautomationapp.ui.dialogs.DialogFilter
import com.homeautomationapp.utils.NameDeviceComparator
import java.util.*

class ListDevicesFragment : Fragment() {

    private lateinit var binding: FragmentListDevicesBinding

    /**
     * Defines a list containing the status of all filters.
     */
    private val filters: BooleanArray = booleanArrayOf(true, true, true)

    /**
     * Defines if a filter operation is performed.
     */
    private var isListFiltered: Boolean = false

    /**
     * Defines a dialog to perform a filter operation.
     */
    private var dialogFilter: DialogFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentListDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment()
        initializeRecyclerView()
        savedInstanceState?.let {
            restoreFilterValues(it)
            restoreDialogFilter(it)
        }
        initializeObserverListDevices()
        initializeObserverListDevicesFiltered()
    }

    private fun initializeToolbarForFragment() {
        (activity as MainActivity)
            .setToolbarProperties(resources.getString(R.string.name_toolbar_frg_list_devices),
                     false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_list_devices, menu)
        displayItemsIconInMenu(menu)
    }

    @SuppressLint("RestrictedApi")
    private fun displayItemsIconInMenu(menu: Menu) {
        if (menu is MenuBuilder) {
            val menuBuilder: MenuBuilder = menu
            menuBuilder.setOptionalIconsVisible(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> {
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_USER_PROFILE)
            }
            R.id.filter -> { displayFilterDialog() }
            R.id.manage_devices -> {
                resetFiltering()
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_DEVICES_MANAGER)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayFilterDialog() {
        dialogFilter = DialogFilter(filters) { onDialogFilterChanged() }
        dialogFilter?.show(parentFragmentManager, AppConstants.TAG_DIALOG_FILTER)
    }

    /**
     * Called when a filter operation is confirmed by user.
     */
    private fun onDialogFilterChanged() {
        isListFiltered = !(filters[0] && filters[1] && filters[2])
        performFiltering()
    }

    /**
     * Sends filter parameters values to the view model.
     */
    private fun performFiltering() {
        (activity as MainActivity).viewModel.filterDevices(filters, isListFiltered)
    }

    /**
     * Resets all filter parameters.
     */
    private fun resetFiltering() {
        isListFiltered = false
        filters[0] = true
        filters[1] = true
        filters[2] = true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeObserverListDevices() {
        (activity as MainActivity).viewModel.devicesLiveData.observe(viewLifecycleOwner, { list ->
            // LiveData updates will only be sent to the adapter if no filter operation is performed
            if (!isListFiltered) {
                (binding.recyclerView.adapter as ListDevicesAdapter).apply {
                    listDevices.apply {
                        Collections.sort(list, NameDeviceComparator)
                        clear()
                        addAll(list)
                    }
                    notifyDataSetChanged()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeObserverListDevicesFiltered() {
        (activity as MainActivity).viewModel.devicesFilteredLiveData.observe(viewLifecycleOwner, { list ->
            // LiveData updates will only be sent to the adapter if a filter operation is perform
            if (isListFiltered) {
                (binding.recyclerView.adapter as ListDevicesAdapter).apply {
                    listDevices.apply {
                        Collections.sort(list, NameDeviceComparator)
                        clear()
                        addAll(list)
                    }
                    notifyDataSetChanged()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ListDevicesAdapter(context) { position -> onItemClicked(position) }
        }
    }

    private fun onItemClicked(index: Int) {
        // Filters are cancelled before displaying a new Fragment
        resetFiltering()
        val device = (binding.recyclerView.adapter as ListDevicesAdapter).listDevices[index]
        (activity as MainActivity).viewModel.selectedDevice = device
        when (device.productType) {
            "Heater" -> {
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_HEATERS)
            }
            "Light" -> {
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_LIGHTS)
            }
            "RollerShutter" -> {
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_ROLLER_SHUTTERS)
            }
        }
    }

    /**
     * Restores filters values after a configuration change.
     */
    private fun restoreFilterValues(savedInstanceState: Bundle) {
        savedInstanceState.let {
            filters[0] = it.getBoolean("filter_lights")
            filters[1] = it.getBoolean("filter_heaters")
            filters[2] = it.getBoolean("filter_roller_shutters")
            isListFiltered = it.getBoolean(("is_list_filtered"))
            if (isListFiltered) performFiltering()
        }
    }

    /**
     * Restores dialog filter display after a configuration change.
     */
    private fun restoreDialogFilter(savedInstanceState: Bundle) {
        if (savedInstanceState.getBoolean("dialog_filter_status")) {
            if (parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_FILTER) != null) {
                // Get previous instance (before configuration change) and reinitialize properties
                dialogFilter = parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_FILTER)
                               as DialogFilter
                dialogFilter?.apply {
                    arrayDialog = filters
                    callbackDialog = { onDialogFilterChanged() }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            this.putBoolean("is_list_filtered", isListFiltered)
            this.putBoolean("filter_lights", filters[0])
            this.putBoolean("filter_heaters", filters[1])
            this.putBoolean("filter_roller_shutters", filters[2])
            dialogFilter?.let { this.putBoolean("dialog_filter_status", it.showsDialog) }
        }
    }
}