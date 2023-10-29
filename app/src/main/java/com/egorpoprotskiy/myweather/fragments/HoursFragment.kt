package com.egorpoprotskiy.myweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorpoprotskiy.myweather.R
import com.egorpoprotskiy.myweather.adapters.WeatherAdapter
import com.egorpoprotskiy.myweather.databinding.FragmentHoursBinding
import com.egorpoprotskiy.myweather.model.WeatherModel

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
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

    }
    //10 Привязка адаптера к RecyclerView
    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        //Данный список составлен временно для проверки отбражения данных в RecyclerView
        val list = listOf(
            WeatherModel(
                "",
                "12:00",
                "Sunny",
                "25*C",
                "",
                "",
                "",
                ""),
            WeatherModel(
                "",
                "13:00",
                "Sunny",
                "25*C",
                "",
                "",
                "",
                ""),
            WeatherModel(
                "",
                "14:00",
                "Sunny",
                "35*C",
                "",
                "",
                "",
                "")
        )
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}