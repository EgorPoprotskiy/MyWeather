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
import com.egorpoprotskiy.myweather.databinding.FragmentDaysBinding
import com.egorpoprotskiy.myweather.model.WeatherModel
//17.3 Добавить интерфейс Listener
class DaysFragment : Fragment(), WeatherAdapter.Listener {
    private lateinit var binding: FragmentDaysBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner) {
            //subList - указывает диапазон выводимых элементов в список
            adapter.submitList(it.subList(1, it.size))
        }
    }
    //16 Адаптер для RecyclerView
    private fun init() = with(binding){
        //17.4 В скобках указать фрагмент(после того, как добавили интерфейс Listener в адаптер)
        adapter = WeatherAdapter(this@DaysFragment)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
    }



    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }
}