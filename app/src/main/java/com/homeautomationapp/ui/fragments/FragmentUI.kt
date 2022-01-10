package com.homeautomationapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogCancellation
import com.homeautomationapp.utils.StringModifier

interface FragmentUI {

    fun initializeToolbarForFragment(activity: MainActivity, title: String) {
        activity.setToolbarProperties(title, true)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Suppress("DEPRECATION")
    fun updateMaterialTextViewBackgroundColor(status: Boolean, text: MaterialTextView) {
        text.apply {
            if (status) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.background = resources.getDrawable(R.drawable.background_material_text_view, null)
                else
                    this.background = resources.getDrawable(R.drawable.background_material_text_view)
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.background = resources.getDrawable(R.drawable.background_material_text_view_grey, null)
                else
                    this.background = resources.getDrawable(R.drawable.background_material_text_view_grey)
            }
        }
    }

    fun updateSliderStatus(status: Boolean, slider: RangeSlider) {
        slider.isEnabled = status
        updateSliderActiveColor(status, slider)
    }

    @Suppress("DEPRECATION")
    fun updateSliderActiveColor(status: Boolean, slider: RangeSlider) {
        slider.apply {
            if (status) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.trackActiveTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_72pr, null))
                    this.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_72pr, null))
                }
                else {
                    this.trackActiveTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_72pr))
                    this.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_72pr))
                }

            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.trackActiveTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_85pr, null))
                    this.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_85pr, null))
                }
                else {
                    this.trackActiveTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_85pr))
                    this.thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_85pr))
                }
            }
        }
    }

    fun updateMaterialTextViewDeviceStatus(status: Boolean, materialTextView: MaterialTextView) {
        materialTextView.apply {
            val text = resources.getString(R.string.status_device)
            this.text = StringModifier.getColoredText(if (status) "$text ON" else "$text OFF",
                resources)
        }
    }

    fun displayToastSaveMessage(context: Context?, message: String) {
        context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
    }

    fun displayCancellationDialog(activity: MainActivity, fragmentManager: FragmentManager) {
        DialogCancellation {
            activity.removeFragmentFromBackStack()
        }.show(fragmentManager, AppConstants.TAG_DIALOG_CANCELLATION)
    }
}