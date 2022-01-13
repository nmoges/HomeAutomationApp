package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R
import java.lang.IllegalStateException

/**
 * Displays a cancellation message for user.
 */
class DialogCancellation() : DialogFragment() {

    lateinit var callbackDialog: () -> (Unit)

    constructor(callback: () -> (Unit)): this() {
        callbackDialog = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_cancellation))
                .setPositiveButton(resources.getString(R.string.btn_yes_dialog)) { _, _ ->
                    callbackDialog()
                }
                .setNegativeButton(resources.getString(R.string.btn_no_dialog)) { _, _ -> }
                builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}