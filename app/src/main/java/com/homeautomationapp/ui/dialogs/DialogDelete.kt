package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R
import java.lang.IllegalStateException

/**
 * Displays a delete message for user.
 */
class DialogDelete() : DialogFragment() {

    var indexDialog : Int = 0
    lateinit var callbackDialog: (Int) -> (Unit)
    constructor(index: Int, callback: (Int) -> Unit): this() {
        indexDialog = index
        callbackDialog = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_delete))
                .setMessage(resources.getString(R.string.message_dialog_delete))
                .setPositiveButton(resources.getString(R.string.btn_confirm_dialog)) { _, _ ->
                    callbackDialog(indexDialog)
                }
                .setNegativeButton(resources.getString(R.string.btn_cancel_dialog)) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}