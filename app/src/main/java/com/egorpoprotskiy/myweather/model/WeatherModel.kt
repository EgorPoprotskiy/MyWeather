package com.egorpoprotskiy.myweather.model
//8 Модель для одного дня(дубликат DayItem). DayItem можно удалить
data class WeatherModel (
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String
)