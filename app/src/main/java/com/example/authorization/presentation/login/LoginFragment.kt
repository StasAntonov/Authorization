package com.example.authorization.presentation.login

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.authorization.R
import com.example.authorization.common.ext.setErrorFieldByRes
import com.example.authorization.common.ext.toast
import com.example.authorization.databinding.FragmentLoginBinding
import com.example.authorization.domain.model.LoginUser
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

                viewModel.validateLogin(
                    login = etLogin.editText?.text.toString(),
                    minSize = resources.getInteger(R.integer.min_phone_length),
                    maxSize = resources.getInteger(R.integer.max_phone_length)
                )
            }

        }
    }

    override fun initObservers() = with(binding) {
        super.initObservers()

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewModel.LoginValidationState.Error -> toast(getString(R.string.login_or_password_incorrect))
                is LoginViewModel.LoginValidationState.Success -> {
                    val loginUser = LoginUser(
                        email = state.email,
                        password = etPassword.editText?.text.toString(),
                        phoneNumber = state.phone
                    )

                    viewModel.login(loginUser)
                }
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { state ->
            when (state) {

                is LoginViewModel.UiLoginState.Error -> toast(
                    state.error ?: getString(R.string.unknown_error)
                )

                is LoginViewModel.UiLoginState.Success -> {
                    toast(getString(R.string.login_success))
                    goBack()
                }
            }
        }
    }


}