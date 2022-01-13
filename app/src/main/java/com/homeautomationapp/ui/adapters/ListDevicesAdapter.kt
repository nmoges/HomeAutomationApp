package com.homeautomationapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.homeautomationapp.R
import com.homeautomationapp.model.Device
import com.homeautomationapp.ui.fragments.ListDevicesFragment
import com.homeautomationapp.utils.StringModifier

/**
 * Adapter class for [ListDevicesFragment].
 */
class ListDevicesAdapter(private val context: Context, private val onItemClicked: (Int) -> Unit):
    RecyclerView.Adapter<ListDevicesAdapter.ListDevicesViewHolder>(){

    var listDevices: MutableList<Device> = mutableListOf()

    inner class ListDevicesViewHolder(view: View, onItemClicked: (Int) -> Unit) :
            RecyclerView.ViewHolder(view) {

            var icon: AppCompatImageView = view.findViewById(R.id.icon_device)
            var name: MaterialTextView = view.findViewById(R.id.name_device)
            var value: MaterialTextView = view.findViewById(R.id.value_device)
            var status: MaterialTextView = view.findViewById(R.id.status_device)
            var layout: ConstraintLayout = view.findViewById(R.id.constraint_layout_item)

        init {
                layout.setOnClickListener { onItemClicked(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDevicesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.item_device_details, parent, false)
        return ListDevicesViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ListDevicesViewHolder, index: Int) {
        holder.apply {
            name.text = listDevices[index].deviceName
            displayIcon(icon, listDevices[index].productType)
            displayValue(value, listDevices[index])
            displayStatus(status, listDevices[index])
        }
    }

    /**
     * Display device icon.
     * @param icon : icon to display
     * @param type : type of device
     */
    private fun displayIcon(icon: AppCompatImageView, type: String) {
        when(type){
            "Heater" -> {
                icon.setImageDrawable(ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_baseline_ac_unit_24dp_white, null))
            }
            "Light" -> {
                icon.setImageDrawable(ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_baseline_light_mode_24dp_white, null))
            }
            "RollerShutter" -> {
                icon.setImageDrawable(ResourcesCompat.getDrawable(context.resources,
                    R.drawable.ic_baseline_window_24dp_white, null))
            }
        }

    }

    /**
     * Display device current value.
     * @param text : item MaterialTextView to update
     * @param device : associated device
     */
    private fun displayValue(text: MaterialTextView, device: Device) {
        when(device.productType) {
            "Heater" -> {
                val temperature = context.resources.getString(
                    R.string.current_temp_heater,
                                                              (device as Device.Heater).temperature)
                text.text = temperature
            }
            "Light" -> {
                val intensity = (device as Device.Light).intensity.toString()
                text.text = intensity
            }
            "RollerShutter" -> {
                val position = (device as Device.RollerShutter).position.toString()
                text.text = position
            }
        }
    }

    /**
     * Display device status for heaters and lights.
     * @param text : item MaterialTextView to update
     * @param device : associated device
     */
    private fun displayStatus(text: MaterialTextView, device: Device) {
        when(device.productType) {
            "Heater" -> {
                (device as Device.Heater).mode?.let {
                    val coloredText = StringModifier.getColoredText(it, context.resources)
                    text.text = coloredText
                }

            }
            "Light" -> {
                (device as Device.Light).mode?.let {
                    val coloredText = StringModifier.getColoredText(it, context.resources)
                    text.text = coloredText
                }
            }
            "RollerShutter" -> { text.text = "" }
        }
    }

    override fun getItemCount(): Int = listDevices.size
}