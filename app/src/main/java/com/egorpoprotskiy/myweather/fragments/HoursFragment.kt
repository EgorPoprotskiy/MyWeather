package com.egorpoprotskiy.myweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorpoprotskiy.myweather.MainViewModel
import com.egorpoprotskiy.myweather.adapters.WeatherAdapter
import com.egorpoprotskiy.myweather.databinding.FragmentHoursBinding
import com.egorpoprotskiy.myweather.model.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    //15.1 Создание объекта ViewModel для HoursFragment
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getHoursList(it))
        }

    }
    //10 Привязка адаптера к RecyclerView
    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter(null)
        recyclerView.adapter = adapter
        //Данный список составлен временно для проверки отбражения данных в RecyclerView
//        val list = listOf(
//            WeatherModel(
//                "",
//                "12:00",
//                "Sunny",
//                "25*C",
//                "",
//                "",
//                "",
//                ""),
//            WeatherModel(
//                "",
//                "13:00",
//                "Sunny",
//                "25*C",
//                "",
//                "",
//                "",
//                ""),
//            WeatherModel(
//                "",
//                "14:00",
//                "Sunny",
//                "35*C",
//                "",
//                "",
//                "",
//                "")
//        )
//        adapter.submitList(list)
    }

    //15.2 Функция получения данных по часам
    private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
        //Сохраняем JSON-массив в переменную
        val hoursArray = JSONArray(wItem.hours)
        //Создаем массив для сохранения данных из hoursArray
        val list = ArrayList<WeatherModel>()
        //Создаем цикл для сохранения каждого часа
        for (i in 0 until hoursArray.length()) {
            val item = WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}