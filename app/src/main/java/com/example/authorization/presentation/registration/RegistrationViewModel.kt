package com.example.authorization.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authorization.util.UserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : ViewModel() {

    private val _firstNameError = MutableLiveData<UserValidator.NameState>()
    val firstNameError: LiveData<UserValidator.NameState> get() = _firstNameError


    private val _lastNameError = MutableLiveData<UserValidator.NameState>()
    val lastNameError: LiveData<UserValidator.NameState> get() = _lastNameError


    private val _emailError = MutableLiveData<UserValidator.EmailState>()
    val emailError: LiveData<UserValidator.EmailState> get() = _emailError


    private val _phoneError = MutableLiveData<UserValidator.PhoneState>()
    val phoneError: LiveData<UserValidator.PhoneState> get() = _phoneError

    private val _passwordError = MutableLiveData<UserValidator.PasswordState>()
    val passwordError: LiveData<UserValidator.PasswordState> get() = _passwordError


    fun validateFirstName(name: String) {
        _firstNameError.postValue(UserValidator.validateName(name))
    }

    fun validateLastName(name: String) {
        _lastNameError.postValue(UserValidator.validateName(name))
    }

    fun validateEmail(email: String) {
        _emailError.postValue(UserValidator.validateEmail(email))
    }

    fun validatePhone(phone: String, minSize: Int, maxSize: Int) {
        _phoneError.postValue(UserValidator.validatePhone(phone, minSize, maxSize))
    }

    fun validatePassword(password: String, minSize: Int, maxSize: Int) {
        _passwordError.postValue(UserValidator.validatePassword(password, minSize, maxSize))
    }

    fun register() {
    }

}