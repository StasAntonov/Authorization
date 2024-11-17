package com.example.authorization.common.ext

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorFieldByRes(context: Context, @StringRes message: Int?) {
    this.error = message?.let(context::getString)
    this.isErrorEnabled = message != null
}