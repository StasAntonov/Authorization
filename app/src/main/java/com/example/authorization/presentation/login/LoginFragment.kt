package com.example.authorization.presentation.login

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.authorization.R
import com.example.authorization.common.ext.setErrorFieldByRes
import com.example.authorization.common.ext.toast
import com.example.authorization.databinding.FragmentLoginBinding
import com.example.authorization.presentation.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    private val requiredFields: List<TextInputLayout> by lazy {
        with(binding) {
            listOf(etLogin, etPassword)
        }
    }

    override fun initListeners() = with(binding) {
        super.initListeners()

        imgBack.setOnClickListener { goBack() }

        requiredFields
            .forEach { layout ->
                layout.editText?.doAfterTextChanged {
                    if (layout.isErrorEnabled) {
                        layout.setErrorFieldByRes(requireContext(), null)
                    }
                }
            }

        btnRegister.setOnClickListener {

            requiredFields
                .filter { it.editText?.text.isNullOrEmpty() && it.editText?.text.isNullOrBlank() }
                .takeIf { it.isNotEmpty() }
                ?.forEach { layout ->
                    layout.setErrorFieldByRes(requireContext(), R.string.required)
                } ?: run {
                viewModel.login(
                    etLogin.editText?.text.toString(),
                    etPassword.editText?.text.toString()
                )
                toast("Login")
            }

        }
    }


}