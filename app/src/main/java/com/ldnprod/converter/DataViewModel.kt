package com.ldnprod.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    val textFrom: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val textTo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}