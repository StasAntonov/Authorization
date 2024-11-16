package com.example.authorization.presentation.registration

import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.authorization.R
import com.example.authorization.databinding.FragmentRegistrationBinding
import com.example.authorization.presentation.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    private val requiredFields: List<TextInputLayout> by lazy {
        with(binding) {
            listOf(etFirstName, etLastName, etPhone, etEmail, etPassword)
        }
    }

    override fun initListeners() = with(binding) {
        super.initListeners()

        imgBack.setOnClickListener { goBack() }

        requiredFields
            .forEach { layout ->
                layout.editText?.doAfterTextChanged {
                    if (layout.isErrorEnabled) {
                        layout.setErrorFieldByRes(null)
                    }
                }

                layout.editText?.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        val text = layout.editText?.text.toString()
                        if (text.isBlank() || text.isEmpty()) return@setOnFocusChangeListener
                        layout.validateField()
                    }
                }
            }

        btnRegister.setOnClickListener {
            fun tryRegister() {
                if (requiredFields.all { !it.isErrorEnabled }) {
                    viewModel.register()
                }
            }

            requiredFields
                .filter { it.editText?.text.isNullOrEmpty() && it.editText?.text.isNullOrBlank() }
                .takeIf { it.isNotEmpty() }
                ?.forEach { layout ->
                    layout.setErrorFieldByRes(R.string.required)
                }

            requiredFields.find { it.hasFocus() }?.let { layout ->
                layout.validateField()
                layout.post(::tryRegister)
            } ?: tryRegister()
        }
    }

    override fun initObservers() = with(binding) {
        super.initObservers()

        viewModel.firstNameError.observe(viewLifecycleOwner) {
            etFirstName.setErrorFieldByRes(it.message)
        }

        viewModel.lastNameError.observe(viewLifecycleOwner) {
            etLastName.setErrorFieldByRes(it.message)
        }

        viewModel.phoneError.observe(viewLifecycleOwner) {
            etPhone.setErrorFieldByRes(it.message)
        }

        viewModel.emailError.observe(viewLifecycleOwner) {
            etEmail.setErrorFieldByRes(it.message)
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {
            etPassword.setErrorFieldByRes(it.message)
        }
    }

    private fun TextInputLayout.setErrorFieldByRes(@StringRes message: Int?) {
        this.error = message?.let(::getString)
        this.isErrorEnabled = message != null
    }

    private fun TextInputLayout.validateField() {
        val text = this.editText?.text.toString()

        when (this.id) {
            R.id.et_first_name -> viewModel.validateFirstName(text)
            R.id.et_last_name -> viewModel.validateLastName(text)

            R.id.et_phone -> viewModel.validatePhone(
                text,
                resources.getInteger(R.integer.min_phone_length),
                resources.getInteger(R.integer.max_phone_length)
            )

            R.id.et_email -> viewModel.validateEmail(text)

            R.id.et_password -> viewModel.validatePassword(
                text,
                resources.getInteger(R.integer.min_password_length),
                resources.getInteger(R.integer.max_password_length)
            )
        }
    }
}