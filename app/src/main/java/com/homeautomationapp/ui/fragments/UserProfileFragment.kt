package com.homeautomationapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentUserProfileBinding
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogCancellation
import com.homeautomationapp.ui.dialogs.DialogReset
import com.homeautomationapp.utils.ProfileTextWatcher

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbarForFragment()
        handleTextInputEditListeners()
        handleConfirmationButton()
    }

    private fun initializeToolbarForFragment() {
        (activity as MainActivity)
            .setToolbarProperties(resources.getString(R.string.name_toolbar_frg_user_profile),
                     true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { displayCancellationDialog() }
            R.id.clear -> { displayResetDialog() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleConfirmationButton() {
        binding.button.setOnClickListener {
            if (!detectIfEmptyFields()) {
                Toast.makeText(context, R.string.toast_confirmation, Toast.LENGTH_SHORT).show()
                (activity as MainActivity).removeFragmentFromBackStack()
            }
            else displayErrorToastMessage()
        }
    }

    fun displayCancellationDialog() {
        DialogCancellation {
            (activity as MainActivity).removeFragmentFromBackStack()
        }.show(parentFragmentManager, AppConstants.TAG_DIALOG_CANCELLATION)
    }

    private fun displayResetDialog() {
       DialogReset {
           clearTextInputEdits()
           clearTextInputLayouts()
       }.show(parentFragmentManager, AppConstants.TAG_DIALOG_RESET)
    }

    private fun clearTextInputEdits() {
        binding.let {
            it.textInputEditFirstName.text?.clear()
            it.textInputEditLastName.text?.clear()
            it.textInputEditAddress.text?.clear()
            it.textInputEditEmail.text?.clear()
            if (it.textInputEditFirstName.isFocused) it.textInputEditFirstName.clearFocus()
            if (it.textInputEditLastName.isFocused) it.textInputEditLastName.clearFocus()
            if (it.textInputEditAddress.isFocused) it.textInputEditAddress.clearFocus()
            if (it.textInputEditEmail.isFocused) it.textInputEditEmail.clearFocus()
        }
    }

    private fun detectIfEmptyFields(): Boolean {
        var error = false
        binding.let {
            val firstName: String = it.textInputEditFirstName.text.toString()
            val lastName: String = it.textInputEditLastName.text.toString()
            val email: String = it.textInputEditEmail.text.toString()
            val address: String = it.textInputEditAddress.text.toString()

            if (firstName.isEmpty()) {
                it.textInputLayoutFirstName.isErrorEnabled = true
                it.textInputLayoutFirstName.error = resources.getString(R.string.error_text_input_empty)
                error = true
            }
            if (lastName.isEmpty()) {
                it.textInputLayoutLastName.isErrorEnabled = true
                it.textInputLayoutLastName.error = resources.getString(R.string.error_text_input_empty)
                error = true
            }
            if (email.isEmpty() || !isEmailFormatIsValid(email)) {
                it.textInputLayoutEmail.isErrorEnabled = true
                it.textInputLayoutEmail.error =
                    if (email.isEmpty()) resources.getString(R.string.error_text_input_empty)
                    else resources.getString(R.string.error_text_input_invalid_format)
                error = true
            }
            if (address.isEmpty()) {
                it.textInputLayoutAddress.isErrorEnabled = true
                it.textInputLayoutAddress.error = resources.getString(R.string.error_text_input_empty)
                error = true
            }
        }
        return error
    }

    private fun isEmailFormatIsValid(email: String): Boolean = email.contains("@")

    private fun handleTextInputEditListeners() {
        binding.let {
            it.textInputEditFirstName.addTextChangedListener(ProfileTextWatcher("first_name") { type ->
                clearTextInputEditErrorStatus(type)
            })
            it.textInputEditLastName.addTextChangedListener(ProfileTextWatcher("last_name") { type ->
                clearTextInputEditErrorStatus(type)
            })
            it.textInputEditEmail.addTextChangedListener(ProfileTextWatcher("email") { type ->
                clearTextInputEditErrorStatus(type)
            })
            it.textInputEditAddress.addTextChangedListener(ProfileTextWatcher("address") { type ->
                clearTextInputEditErrorStatus(type)
            })
        }
    }

    private fun clearTextInputEditErrorStatus(type: String) {
        binding.let {
            when(type) {
                "first_name" -> { it.textInputLayoutFirstName.isErrorEnabled = false }
                "last_name" -> { it.textInputLayoutLastName.isErrorEnabled = false }
                "email" -> { it.textInputLayoutEmail.isErrorEnabled = false }
                "address" -> { it.textInputLayoutAddress.isErrorEnabled = false }
            }
        }
    }

    private fun clearTextInputLayouts() {
        clearTextInputEditErrorStatus("first_name")
        clearTextInputEditErrorStatus("last_name")
        clearTextInputEditErrorStatus("email")
        clearTextInputEditErrorStatus("address")
    }

    private fun displayErrorToastMessage() {
        Toast.makeText(context,
                       resources.getString(R.string.error_toast_message),
                       Toast.LENGTH_SHORT).show()
    }
}