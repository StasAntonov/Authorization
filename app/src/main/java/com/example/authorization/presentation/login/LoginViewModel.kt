package com.example.authorization.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.data.base.ApiResponse
import com.example.authorization.domain.model.LoginUser
import com.example.authorization.domain.repository.LoginRepository
import com.example.authorization.util.UserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    sealed class UiLoginState {
        data class Success(val message: String?) : UiLoginState()
        data class Error(val error: String?) : UiLoginState()
    }

    sealed class LoginValidationState {
        data class Success(val email: String? = null, val phone: String? = null) :
            LoginValidationState()

        data object Error : LoginValidationState()
    }

    private val _loginResult = MutableLiveData<UiLoginState>()
    val loginResult: LiveData<UiLoginState> get() = _loginResult

    private val _loginState = MutableLiveData<LoginValidationState>()
    val loginState: LiveData<LoginValidationState> get() = _loginState

    fun validateLogin(login: String, minSize: Int, maxSize: Int) {
        val emailValidationState = UserValidator.validateEmail(login)
        val phoneValidationState = UserValidator.validatePhone(login, minSize, maxSize)

        val loginState = if (emailValidationState == UserValidator.EmailState.CORRECT) {
            LoginValidationState.Success(email = login)
        } else if (phoneValidationState == UserValidator.PhoneState.CORRECT) {
            LoginValidationState.Success(phone = login)
        } else {
            LoginValidationState.Error
        }
        _loginState.postValue(loginState)
    }

    fun login(user: LoginUser) {
        viewModelScope.launch {
            when (val result = repository.login(user)) {

                is ApiResponse.Error -> _loginResult.postValue(
                    UiLoginState.Error(
                        result.throwable.message
                    )
                )

                is ApiResponse.Success -> _loginResult.postValue(UiLoginState.Success(result.data))
            }
        }
    }
}