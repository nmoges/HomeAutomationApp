package com.homeautomationapp.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.FragmentUserProfileBinding
import com.homeautomationapp.model.User
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.dialogs.DialogReset
import com.homeautomationapp.utils.DateFormatter
import com.homeautomationapp.utils.ProfileTextWatcher
import java.util.*

class UserProfileFragment : Fragment(), FragmentUI {

    private lateinit var binding: FragmentUserProfileBinding

    private lateinit var user: User

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
        initializeTextInputEditFields()
        (activity as MainActivity).viewModel.getUser()
        initializeTextInputEditFields()
        handleTextInputEditListeners()
        handleConfirmationButton()
        binding.textInputEditBirthdate.setOnClickListener {
            displayDatePickerDialog()
        }
    }

    private fun displayDate(year: Int, month: Int, day: Int) {
        binding.textInputEditBirthdate.setText(DateFormatter.getDate(year, month, day))
    }

    private fun initializeToolbarForFragment() {
        (activity as MainActivity)
            .setToolbarProperties(resources.getString(R.string.name_toolbar_frg_user_profile),
                     true)
    }

    private fun initializeTextInputEditFields() {
        (activity as MainActivity).viewModel.userLiveData.observe(viewLifecycleOwner, {
            user = it
            binding.apply {
                textInputEditFirstName.setText(user.firstName)
                textInputEditLastName.setText(user.lastName)
                textInputEditBirthdate.setText(user.birthdate)
                textInputEditEmail.setText(user.email)
                textInputEditPhone.setText(user.phone)
                textInputEditStreetNumber.setText(user.address.streetNumber)
                textInputEditStreetName.setText(user.address.streetName)
                textInputEditPostalCode.setText(user.address.postalCode.toString())
                textInputEditCity.setText(user.address.city)
                textInputEditCountry.setText(user.address.country)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { displayCancellationDialog((activity as MainActivity),
                                                              parentFragmentManager) }
            R.id.clear -> { displayResetDialog() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleConfirmationButton() {
        binding.button.setOnClickListener {
            if (!detectIfEmptyFields()) {
                saveNewUserInformation()
                Toast.makeText(context, R.string.toast_confirmation, Toast.LENGTH_SHORT).show()
                (activity as MainActivity).removeFragmentFromBackStack()
            }
            else displayErrorToastMessage()
        }
    }

    private fun displayResetDialog() {
       DialogReset {
           clearTextInputEdits()
           clearTextInputLayouts()
       }.show(parentFragmentManager, AppConstants.TAG_DIALOG_RESET)
    }

    private fun displayDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog((activity as MainActivity),
                        R.style.DatePickerDialogTheme,
                        { _, yearDate, monthDate, dayDate ->
                                displayDate(yearDate, monthDate, dayDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun clearTextInputEdits() {
        binding.let {
            it.textInputEditFirstName.text?.clear()
            it.textInputEditLastName.text?.clear()
            it.textInputEditBirthdate.text?.clear()
            it.textInputEditEmail.text?.clear()
            it.textInputEditPhone.text?.clear()
            it.textInputEditStreetNumber.text?.clear()
            it.textInputEditStreetName.text?.clear()
            it.textInputEditPostalCode.text?.clear()
            it.textInputEditCity.text?.clear()
            if (it.textInputEditFirstName.isFocused) it.textInputEditFirstName.clearFocus()
            if (it.textInputEditLastName.isFocused) it.textInputEditLastName.clearFocus()
            if (it.textInputEditEmail.isFocused) it.textInputEditEmail.clearFocus()
            if (it.textInputEditPhone.isFocused) it.textInputEditPhone.clearFocus()
            if (it.textInputEditStreetNumber.isFocused) it.textInputEditStreetNumber.clearFocus()
            if (it.textInputEditStreetName.isFocused) it.textInputEditStreetName.clearFocus()
            if (it.textInputEditPostalCode.isFocused) it.textInputEditPostalCode.clearFocus()
            if (it.textInputEditCity.isFocused) it.textInputEditCity.clearFocus()
            if (it.textInputEditCountry.isFocused) it.textInputEditCountry.clearFocus()
        }
    }

    private fun detectIfEmptyFields(): Boolean {
        var error: Boolean
        binding.let {
            checkTextInputEditField("first_name", it.textInputEditFirstName, it.textInputLayoutFirstName)
            checkTextInputEditField( "last_name", it.textInputEditLastName, it.textInputLayoutLastName)
            checkTextInputEditField("birthdate", it.textInputEditBirthdate, it.textInputLayoutBirthdate)
            checkTextInputEditField("email", it.textInputEditEmail, it.textInputLayoutEmail)
            checkTextInputEditField("phone", it.textInputEditPhone, it.textInputLayoutPhone)
            checkTextInputEditField("street_number", it.textInputEditStreetNumber, it.textInputLayoutStreetNumber)
            checkTextInputEditField("street_name", it.textInputEditStreetName, it.textInputLayoutStreetName)
            checkTextInputEditField("postal_code", it.textInputEditPostalCode, it.textInputLayoutPostalCode)
            checkTextInputEditField("city", it.textInputEditCity, it.textInputLayoutCity)
            checkTextInputEditField("country", it.textInputEditCountry, it.textInputLayoutCountry)

            error = it.textInputLayoutFirstName.isErrorEnabled
                    || it.textInputLayoutLastName.isErrorEnabled
                    || it.textInputLayoutBirthdate.isErrorEnabled
                    || it.textInputLayoutEmail.isErrorEnabled
                    || it.textInputLayoutPhone.isErrorEnabled
                    || it.textInputLayoutStreetNumber.isErrorEnabled
                    || it.textInputLayoutStreetName.isErrorEnabled
                    || it.textInputLayoutPostalCode.isErrorEnabled
                    || it.textInputLayoutCity.isErrorEnabled
                    || it.textInputLayoutCountry.isErrorEnabled
        }
        return error
    }

    private fun checkTextInputEditField(type: String,
                                        textInputEdit: TextInputEditText,
                                        textInputLayout: TextInputLayout) {
        when(type) {
            "email" -> {
                if (textInputEdit.text.toString().isEmpty()
                    || !isEmailFormatIsValid(textInputEdit.text.toString())) {
                    textInputLayout.isErrorEnabled = true
                    textInputLayout.error =
                        if (textInputEdit.text.toString().isEmpty()) resources.getString(R.string.error_text_input_empty)
                        else resources.getString(R.string.error_text_input_invalid_format)
                }
            }
            else -> {
                if (textInputEdit.text.toString().isEmpty()) {
                    textInputLayout.isErrorEnabled = true
                    textInputLayout.error = resources.getString(R.string.error_text_input_empty)
                }
            }
        }
    }

    private fun isEmailFormatIsValid(email: String): Boolean = email.contains("@")

    private fun handleTextInputEditListeners() {
        binding.let {
            it.textInputEditFirstName.addTextChangedListener(ProfileTextWatcher("first_name")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditLastName.addTextChangedListener(ProfileTextWatcher("last_name")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditBirthdate.addTextChangedListener(ProfileTextWatcher("birthdate")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditEmail.addTextChangedListener(ProfileTextWatcher("email")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditPhone.addTextChangedListener(ProfileTextWatcher("phone")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditStreetNumber.addTextChangedListener(ProfileTextWatcher("street_number")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditStreetName.addTextChangedListener(ProfileTextWatcher("street_name")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditPostalCode.addTextChangedListener(ProfileTextWatcher("postal_code")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditCity.addTextChangedListener(ProfileTextWatcher("city")
                { type -> clearTextInputEditErrorStatus(type) })
            it.textInputEditCountry.addTextChangedListener(ProfileTextWatcher("country")
                { type -> clearTextInputEditErrorStatus(type) })
        }
    }

   private fun clearTextInputEditErrorStatus(type: String) {
        binding.let {
            when(type) {
                "first_name" -> { it.textInputLayoutFirstName.isErrorEnabled = false }
                "last_name" -> { it.textInputLayoutLastName.isErrorEnabled = false }
                "birthdate" -> { it.textInputLayoutBirthdate.isErrorEnabled = false }
                "email" -> { it.textInputLayoutEmail.isErrorEnabled = false }
                "phone" -> { it.textInputLayoutPhone.isErrorEnabled = false }
                "street_number" -> { it.textInputLayoutStreetNumber.isErrorEnabled = false }
                "street_name" -> { it.textInputLayoutStreetName.isErrorEnabled = false }
                "postal_code" -> { it.textInputLayoutPostalCode.isErrorEnabled = false }
                "city" -> { it.textInputLayoutCity.isErrorEnabled = false }
                "country" -> { it.textInputLayoutCountry.isErrorEnabled = false}
            }
        }
    }

    private fun clearTextInputLayouts() {
        clearTextInputEditErrorStatus("first_name")
        clearTextInputEditErrorStatus("last_name")
        clearTextInputEditErrorStatus("birthdate")
        clearTextInputEditErrorStatus("email")
        clearTextInputEditErrorStatus("phone")
        clearTextInputEditErrorStatus("street_number")
        clearTextInputEditErrorStatus("street_name")
        clearTextInputEditErrorStatus("postal_code")
        clearTextInputEditErrorStatus("city")
        clearTextInputEditErrorStatus("country")
    }

    private fun displayErrorToastMessage() {
        Toast.makeText(context,
                       resources.getString(R.string.error_toast_message),
                       Toast.LENGTH_SHORT).show()
    }

    private fun saveNewUserInformation() {
        binding.let {
            user.apply {
                firstName = it.textInputEditFirstName.text.toString()
                lastName = it.textInputEditLastName.text.toString()
                birthdate = it.textInputEditBirthdate.text.toString()
                email = it.textInputEditEmail.text.toString()
                phone = it.textInputEditPhone.text.toString()
                address.streetNumber = it.textInputEditStreetNumber.text.toString()
                address.streetName = it.textInputEditStreetName.text.toString()
                address.postalCode = it.textInputEditPostalCode.text.toString().toInt()
                address.city = it.textInputEditCity.text.toString()
                address.country = it.textInputEditCountry.text.toString()
            }
            (activity as MainActivity).viewModel.updateUserData(user)
        }
    }
}