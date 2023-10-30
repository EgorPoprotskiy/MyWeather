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
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.egorpoprotskiy.myweather.MainViewModel
import com.egorpoprotskiy.myweather.adapters.VpAdapter
import com.egorpoprotskiy.myweather.adapters.WeatherAdapter
import com.egorpoprotskiy.myweather.databinding.FragmentMainBinding
import com.egorpoprotskiy.myweather.model.WeatherModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

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
    //14.1 Создание объекта ViewModel
    private val model: MainViewModel by activityViewModels()

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        //updateCurrentCard() должна вызываться перед requestWeatherData("London")
        updateCurrentCard()
        requestWeatherData("Berlin")

    }
    //7 Привязка адаптера к ViewPager2
    private fun init() = with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp) {
                tab, pos -> tab.text = tList[pos]
        }.attach()
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
                result -> parseWeatherData(result)

            },
            {
                //11.3.2 Если, по какой-то причине, не получим результат, то будет ошибка.
                error -> Log.d("MyLog", "Error: $error")
            })
        //11.4 Передача запроса в созданную очередь
        queue.add(request)
    }

    //12 Итоговое получение данных из JSON-формата
    private fun parseWeatherData(result: String) {
        //12.1 Создание объекта из JSON-формата(хранит в себе все строки(а именно "Response Body")
        val mainObject = JSONObject(result)
        //13
        val list = parseDays(mainObject)
        //12.2 Вызов функции в которой происходит частичное получение данных
        parseCurrentData(mainObject, list[0])
    }

    //12 1-я функция по получению данных из JSON-формата(из объектов)(для сегодняшнего дня)
    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel) {
        //12.3 Вызов конструктора модели данных. Добавление строк в конструктор данных из JSON объекта
        val item = WeatherModel(
            //getJSONObject("location") - получение JSON-объекта с именем "location". getString("name") - получение строки "name" из объекта "location"
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
        //14.2 Передаем полученную текущую погоду в liveDataCurrent
        model.liveDataCurrent.value = item
        //12 Проверка, что данные получениы верно
        Log.d("MyLog", "City: ${item.maxTemp}")
        Log.d("MyLog", "Time: ${item.minTemp}")
        Log.d("MyLog", "Time: ${item.hours}")
    }

    //13 2-я функция по получению данных из JSON-формата(из массивов)(для 5-и дней)
    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        // 13.1 Создание объекта типа ArrayList(массив)
        val list = ArrayList<WeatherModel>()
        //13.2 Получение массива с именем "forecastday" из объекта JSON
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        //13.3 Создаем цикл по количеству дней(кол-во дней - это длина массива)
        for (i in 0 until daysArray.length()) {
            //Следовательно, в каждый из дней будет добавлена ифнформация о погоде из массива "forecastday"
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return list
    }

    //14.3 Обсервер для viewModel, который следит за изменениями данных + заполнение всех view
    private fun updateCurrentCard() = with(binding){
        //Данные уже получены и сохранены в liveDataCurrent(смотри метод parseCurrentData)
        model.liveDataCurrent.observe(viewLifecycleOwner) {
                val maxMinTemp = "${it.maxTemp}C / ${it.minTemp}C"
            tvData.text = it.time
            tvCity.text = it.city
            tvCurrentTemp.text = it.currentTemp
            tvCondition.text = it.condition
            tvMaxMin.text = maxMinTemp
            //Для получения картинки используем библиотеку Picasso
            Picasso.get().load("https:" + it.imageUrl).into(imWeather)

        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}