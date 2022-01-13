package com.homeautomationapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentDevicesManagerBinding
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.adapters.ManagerAdapter
import com.homeautomationapp.ui.dialogs.DialogDelete
import com.homeautomationapp.utils.NameDeviceComparator
import java.util.*

class ManagerFragment : Fragment() {

    private lateinit var binding: FragmentDevicesManagerBinding

    private var dialogDelete: DialogDelete? = null

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDevicesManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment()
        initializeRecyclerView()
        initializeViewModelObserver()
        savedInstanceState?.let {
            restoreIndex(it)
            restoreDialogDelete(it)
        }
    }

    private fun initializeToolbarForFragment() {
        (activity as MainActivity).setToolbarProperties(resources.getString(R.string.name_toolbar_frg_devices_manager),
                                           true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            (activity as MainActivity).onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewManager.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ManagerAdapter { index -> displayDialog(index) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeViewModelObserver() {
        (activity as MainActivity).viewModel.devicesLiveData.observe(viewLifecycleOwner, { list ->
            (binding.recyclerViewManager.adapter as ManagerAdapter).apply {
                listDevices.apply {
                    clear()
                    Collections.sort(list, NameDeviceComparator)
                    addAll(list)
                }
                notifyDataSetChanged()
            }
        })
    }

    /**
     * Restores delete dialog display after a configuration change.
     */
    private fun restoreDialogDelete(savedInstanceState: Bundle) {
        if (savedInstanceState.getBoolean("dialog_delete")) {
            if (parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_DELETE) != null) {
                // Get previous instance (before configuration change) and reinitialize properties
                dialogDelete = parentFragmentManager.findFragmentByTag(AppConstants.TAG_DIALOG_DELETE)
                               as DialogDelete
                dialogDelete?.apply {
                    callbackDialog = { deleteDevice(index) }
                }
            }
        }
    }

    /**
     * Restore index of a selected item in the list before a configuration change.
     */
    private fun restoreIndex(savedInstanceState: Bundle) {
        index = savedInstanceState.getInt("selected_item")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt("selected_item", index)
            dialogDelete?.let { this.putBoolean("dialog_delete", it.showsDialog) }
        }
    }

    private fun displayDialog(index: Int) {
        dialogDelete = DialogDelete(index) {
            deleteDevice(index)
        }
        dialogDelete?.show(parentFragmentManager, AppConstants.TAG_DIALOG_DELETE)
    }

    /**
     * Delete selected device in database.
     */
    private fun deleteDevice(index: Int) {
        val device = (binding.recyclerViewManager.adapter as ManagerAdapter).listDevices[index]
        (activity as MainActivity).viewModel.deleteDevice(device)
        Toast.makeText(context,
                       resources.getString(R.string.toast_deleted_device),
                       Toast.LENGTH_SHORT).show()
    }
}