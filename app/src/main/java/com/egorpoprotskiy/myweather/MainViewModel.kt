package com.egorpoprotskiy.myweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//3 Создание MainViewModel
class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<String>()
    val liveDataList = MutableLiveData<List<String>>()
}