package com.homeautomationapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.homeautomationapp.R
import java.lang.IllegalStateException

class DialogDelete : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.title_dialog_delete))
                .setMessage(resources.getString(R.string.message_dialog_delete))
                .setPositiveButton(resources.getString(R.string.btn_confirm_dialog)) { _, _ -> }
                .setNegativeButton(resources.getString(R.string.btn_cancel_dialog)) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}