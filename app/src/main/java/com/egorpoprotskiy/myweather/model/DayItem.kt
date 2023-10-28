package com.egorpoprotskiy.myweather.model
//4 Создание модели данных для одного дня
data class DayItem (
    val city: String,
    val time: String,
    val condition: String,
    val imageUrl: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
)