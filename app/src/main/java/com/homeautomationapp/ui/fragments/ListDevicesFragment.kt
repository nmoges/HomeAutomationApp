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

class ListDevicesFragment : Fragment() {

    private lateinit var binding: FragmentListDevicesBinding

    private val filters: BooleanArray = booleanArrayOf(true, true, true)

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
        initializeViewModelObserver()
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
                (activity as MainActivity).displayFragment(AppConstants.TAG_FRAGMENT_DEVICES_MANAGER)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayFilterDialog() {
        DialogFilter(filters) {
            onDialogFilterChanged()
        }.show(parentFragmentManager, AppConstants.TAG_DIALOG_FILTER)
    }

    private fun onDialogFilterChanged() {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeViewModelObserver() {
        (activity as MainActivity).viewModel.devicesLiveData.observe(viewLifecycleOwner, { list ->
            (binding.recyclerView.adapter as ListDevicesAdapter).apply {
                listDevices.apply {
                    clear()
                    addAll(list)
                }
                notifyDataSetChanged()
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
}