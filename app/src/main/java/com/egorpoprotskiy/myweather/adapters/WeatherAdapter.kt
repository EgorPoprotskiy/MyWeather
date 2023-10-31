package com.egorpoprotskiy.myweather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.myweather.R
import com.egorpoprotskiy.myweather.databinding.ListItemBinding
import com.egorpoprotskiy.myweather.model.WeatherModel
import com.squareup.picasso.Picasso

//9,10 Создание адаптера для RecyclerView(в видеоуроке очень хорошее объяснение).
class WeatherAdapter: ListAdapter<WeatherModel, WeatherAdapter.WeatherHolder>(DiffCallback()){

    //9.1 Данный класс представляет собой заполненную карточку(list_item.xml), Данные берутся из класса WeatherModel.
    class WeatherHolder(private var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherModel: WeatherModel) {
            binding.apply {
                tvDate.text = weatherModel.time
                tvCondition.text = weatherModel.condition
                tvTemp.text = weatherModel.currentTemp
                Picasso.get().load("https:" + weatherModel.imageUrl).into(im)
            }
        }
    }

    //9.2 Класс для сравнения старых и новых данных в списке
    class DiffCallback: DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            //При исользовании БД или уникальных элементов, используется id(обычно он есть в модели данных). В таком случае сравнение будет выглдеть следующим образом
            //return oldItem.id == newItem.id
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }
    }

    //9.3 Заполняет RecyclerView созданными карточками(WeatherHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    //9.4 Определяет озицию одной карточки
    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(getItem(position))
    }
}