package com.homeautomationapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentDevicesManagerBinding
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.adapters.ManagerAdapter

class ManagerFragment : Fragment() {

    private lateinit var binding: FragmentDevicesManagerBinding

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
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ManagerAdapter()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeViewModelObserver() {
        (activity as MainActivity).viewModel.devicesLiveData.observe(viewLifecycleOwner, { list ->
            (binding.recyclerView.adapter as ManagerAdapter).apply {
                listDevices.apply {
                    addAll(list)
                }
                notifyDataSetChanged()
            }
        })
    }
}