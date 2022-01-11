package com.homeautomationapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.homeautomationapp.R
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.fragments.ManagerFragment

/**
 * Adapter class for [ManagerFragment].
 */
class ManagerAdapter(): RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>() {

    var listDevices: MutableList<Device> = mutableListOf()

    inner class ManagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon: AppCompatImageView = view.findViewById(R.id.icon_trash)
        var name: MaterialTextView = view.findViewById(R.id.name_device)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                                            .inflate(R.layout.item_device, parent, false)
        return ManagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManagerViewHolder, position: Int) {
        holder.apply {
            name.text = listDevices[position].deviceName
            handleClickOnItem(icon, position)
        }
    }

    private fun handleClickOnItem(icon: AppCompatImageView, position: Int) {
        icon.setOnClickListener {
            /* TODO() : Not implemented yet */
        }
    }
    override fun getItemCount(): Int = listDevices.size
}