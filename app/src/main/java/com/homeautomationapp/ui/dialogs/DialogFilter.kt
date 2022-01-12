package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R

class DialogFilter(private val array: BooleanArray, val callback: () -> (Unit)): DialogFragment() {

    private var backUpArray: BooleanArray = booleanArrayOf(true, true, true)

    init {
        array.forEachIndexed { index, value ->
            backUpArray[index] = value
        }
    }

    private fun restorePreviousValues() {
        backUpArray.forEachIndexed { index, value ->
            array[index] = value
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_filter))
                .setMultiChoiceItems(R.array.devices, array) { _, _, _ -> }
                .setPositiveButton(resources.getString(R.string.btn_confirm_dialog)) { _, _ -> callback() }
                .setNegativeButton(resources.getString(R.string.btn_cancel_dialog)) { _, _ -> restorePreviousValues() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}