package com.ldnprod.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    val passingInput: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val passingFunction: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val blockedButtons: MutableLiveData<ArrayList<Pair<String, String>>> by lazy {
        MutableLiveData<ArrayList<Pair<String, String>>>()
    }
}