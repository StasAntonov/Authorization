package com.example.authorization.util

import android.util.Patterns
import androidx.annotation.StringRes
import com.example.authorization.R

object UserValidator {


    enum class PasswordState(@StringRes val message: Int? = null) {
        CORRECT,
        SIZE_ERROR(R.string.password_size_error),
        LOWER_CASE_ERROR(R.string.password_lower_case_error),
        UPPER_CASE_ERROR(R.string.password_upper_case_error),
        DIGIT_ERROR(R.string.password_digit_error),
        SPECIAL_CHARACTER_ERROR(R.string.password_special_character_error)
    }

    enum class PhoneState(@StringRes val message: Int? = null) {
        CORRECT,
        SIZE_ERROR(R.string.phone_size_error),
        FORMAT_ERROR(R.string.phone_format_error)
    }

    enum class EmailState(@StringRes val message: Int? = null) {
        CORRECT,
        FORMAT_ERROR(R.string.email_format_error)
    }

    enum class NameState(@StringRes val message: Int? = null) {
        CORRECT,
        FORMAT_ERROR(R.string.name_format_error)
    }

    fun validatePassword(
        password: String,
        minSize: Int = 0,
        maxSize: Int = Int.MAX_VALUE
    ): PasswordState {
        return if (password.length !in minSize..maxSize) {
            PasswordState.SIZE_ERROR
        } else if (!password.any { it.isLowerCase() }) {
            PasswordState.LOWER_CASE_ERROR
        } else if (!password.any { it.isUpperCase() }) {
            PasswordState.UPPER_CASE_ERROR
        } else if (!password.any { it.isDigit() }) {
            PasswordState.DIGIT_ERROR
        } else if (!password.any { "!@\\\$%*?&^#()_+={}:;,.<>\\\\[\\\\]~|".contains(it) }) {
            PasswordState.SPECIAL_CHARACTER_ERROR
        } else {
            PasswordState.CORRECT
        }
    }

    fun validatePhone(phone: String, minSize: Int, maxSize: Int): PhoneState {

        val phoneRegex = Regex("^\\+?[0-9\\- ]{7,13}$")

        return if (phone.length < minSize || phone.length > maxSize) {
            PhoneState.SIZE_ERROR
        } else if (!phoneRegex.matches(phone)) {
            PhoneState.FORMAT_ERROR
        } else {
            PhoneState.CORRECT
        }
    }

    fun validateEmail(email: String): EmailState {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailState.CORRECT
        } else {
            EmailState.FORMAT_ERROR
        }
    }

    fun validateName(name: String): NameState {
        val nameRegex = "^[A-Za-zА-Яа-яЁё]{2,}$".toRegex()
        return if (nameRegex.matches(name)) {
            NameState.CORRECT
        } else {
            NameState.FORMAT_ERROR
        }
    }
}