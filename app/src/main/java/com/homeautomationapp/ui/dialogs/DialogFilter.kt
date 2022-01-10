package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R

class DialogFilter: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>()
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_filter))
                .setMultiChoiceItems(R.array.devices, null
                ) { dialog, which, isChecked ->

                }
                .setPositiveButton(resources.getString(R.string.btn_confirm_dialog)) { dialog, id ->

                }
                .setNegativeButton(resources.getString(R.string.btn_cancel_dialog)) { dialog, id ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}