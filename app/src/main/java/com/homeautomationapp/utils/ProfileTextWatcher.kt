package com.homeautomationapp.utils

import android.text.Editable
import android.text.TextWatcher
import com.homeautomationapp.ui.fragments.UserProfileFragment

/**
 * Custom TextWatcher for [UserProfileFragment] TextInputEditText views.
 */
class ProfileTextWatcher(val type: String, val callback: (String) -> Unit): TextWatcher {
    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Not used
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Not used
    }

    override fun afterTextChanged(text: Editable?) {
        callback(type)
    }
}