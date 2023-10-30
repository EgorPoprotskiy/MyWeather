package com.egorpoprotskiy.myweather.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.egorpoprotskiy.myweather.adapters.VpAdapter
import com.egorpoprotskiy.myweather.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

const val API_KEY = "e9eec69e30f7493683820453232710"
//2 Создание фрагмента
class MainFragment : Fragment() {
    //2
    private lateinit var binding: FragmentMainBinding
    //5 Лаунчер для отображения диалогового окна с вопросом
    private lateinit var pLayncher: ActivityResultLauncher<String>
    //7 Список для ViewPager2, в него складывает фрагменты(Hours и Days)
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        requestWeatherData("London")
    }
    //7 Привязка адаптера к ViewPager2
    private fun init() = with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp) {
            tab, pos -> tab.text = tList[pos]
        }.attach()
    }

    //5 Фиксирует результат проверки разрешения на геолокацию(callback)
    private fun permissionListener() {
        pLayncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }
    //5 Проверяет, есть ли разрешение на геолокацию или нет
    private fun checkPermission() {
        // если разрешения нет, то
        if (!isPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            //5 Запуск диалогового окна, если разрешения на геолокацию НЕТ
            pLayncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    //11 Получение JSON
    private fun requestWeatherData(city: String) {
        //11.1 Url - создается на сайте WeatherAPI.com
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                //вместо "London" мы вписали переменную city
                city  +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"
        //11.2 Создание очереди
        val queue = Volley.newRequestQueue(context)
        //11.3 Создание запроса для оредачи в очередь
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                //11.3.1 В эту переменную будет сохранен результат(Response Body)(из него будем в дальнейшем брать данные)
                result -> Log.d("MyLog", "Result: $result")
            },
            {
                //11.3.2 Если, по какой-то причине, не получим результат, то будет ошибка.
                error -> Log.d("MyLog", "Errot: $error")
            })
        //11.4 Передача запроса в созданную очередь
        queue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}