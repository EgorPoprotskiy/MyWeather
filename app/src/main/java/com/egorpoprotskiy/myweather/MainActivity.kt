package com.egorpoprotskiy.myweather

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.egorpoprotskiy.myweather.databinding.ActivityMainBinding
import org.json.JSONObject

const val API_KEY = "e9eec69e30f7493683820453232710"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btGet.setOnClickListener {
            getResult("Lindon")
        }
    }
    // 1 Функция реализации запроса
    private fun getResult(name: String) {
        //1 Переменная name будет передавать название города, который нам нужен (данный API получаем на сайте WeatherApi.com)
        val url =
            "https://api.weatherapi.com/v1/current.json" + "?key=$API_KEY&q=$name&aqi=no"
        // 1 Постановка запроса в очередь
        val queue = Volley.newRequestQueue(this)
        // 1 Сам запрос
        val stringRequest = StringRequest(
            com.android.volley.Request.Method.GET,
            url,
            {response ->
                //1 Получение полного JSON ответа
//                Log.d("MyLog", "Response: $response")
            // 1 Получение конкретного ответа из всего JSON ответа
                //1.1 сохранить весь ответ в переменную(преобразовав в JSON объекты)
                val obj = JSONObject(response)
                //1.2 Получить необходимую информацию о конкретном объекте(current), еще в ответе есть объект Location
                val temp = obj.getJSONObject("current")
                //1.3 Получение информации о температуре в градусах из объекта "current"
                Log.d("MyLog", "Response: ${temp.getString("temp_c")}")

            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        // 1 Добавление запроса в очередь
        queue.add(stringRequest)
    }
}