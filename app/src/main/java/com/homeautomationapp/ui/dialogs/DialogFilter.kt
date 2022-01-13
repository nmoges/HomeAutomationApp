package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R

/**
 * Displays a filer dialog for user.
 */
class DialogFilter(): DialogFragment() {

    var arrayDialog: BooleanArray = booleanArrayOf(true, true, true)
    lateinit var callbackDialog : () -> (Unit)

    constructor(array: BooleanArray, callback: () -> (Unit)) : this() {
        arrayDialog = array
        callbackDialog = callback
    }

    private var backUpArray: BooleanArray = booleanArrayOf(true, true, true)

    init {
        arrayDialog.forEachIndexed { index, value ->
            backUpArray[index] = value
        }
    }

    private fun restorePreviousValues() {
        backUpArray.forEachIndexed { index, value ->
            arrayDialog[index] = value
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_filter))
                .setMultiChoiceItems(R.array.devices, arrayDialog) { _, _, _ -> }
                .setPositiveButton(resources.getString(R.string.btn_confirm_dialog)) { _, _ -> callbackDialog() }
                .setNegativeButton(resources.getString(R.string.btn_cancel_dialog)) { _, _ -> restorePreviousValues() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}