package com.egorpoprotskiy.myweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.myweather.model.WeatherModel

//3 Создание MainViewModel
class MainViewModel: ViewModel() {
    //MutableLiveData следит за жизненным циклом активити. При ней данный из WeatherModel становятся доступными, если доступны все необходимые view.
    val liveDataCurrent = MutableLiveData<WeatherModel>()
    val liveDataList = MutableLiveData<List<WeatherModel>>()
}