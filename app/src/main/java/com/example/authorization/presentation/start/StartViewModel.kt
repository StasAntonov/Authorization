package com.example.authorization.presentation.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.common.livedata.SingleLiveEvent
import com.example.authorization.data.base.ApiResponse
import com.example.authorization.domain.model.QrCode
import com.example.authorization.domain.repository.QrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repository: QrRepository
) : ViewModel() {

    private val _qrResult = SingleLiveEvent<String>()
    val qrResult: LiveData<String> get() = _qrResult

    fun qrCode(code: QrCode) {
        viewModelScope.launch {
            when (val result = repository.qrCode(code)) {

                is ApiResponse.Error -> _qrResult.postValue(result.throwable.message)

                is ApiResponse.Success -> _qrResult.postValue(result.data)
            }
        }
    }

}