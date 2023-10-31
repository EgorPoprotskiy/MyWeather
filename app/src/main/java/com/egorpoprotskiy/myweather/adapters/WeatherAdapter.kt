package com.egorpoprotskiy.myweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.myweather.databinding.ListItemBinding
import com.egorpoprotskiy.myweather.model.WeatherModel
import com.squareup.picasso.Picasso

//9,10 Создание адаптера для RecyclerView(в видеоуроке очень хорошее объяснение).
//17.2 Добавление переменной для прослушивателя кликов
class WeatherAdapter(val listener: Listener?): ListAdapter<WeatherModel, WeatherAdapter.WeatherHolder>(DiffCallback()){

    //9.1 Данный класс представляет собой заполненную карточку(list_item.xml), Данные берутся из класса WeatherModel.
    class WeatherHolder(private var binding: ListItemBinding, val listener: Listener?): RecyclerView.ViewHolder(binding.root) {
        //17.5 Указываем, что переменная изначально может быть равна null
        var weatherModelTemp: WeatherModel?= null
        //17.7 Функция, которая будет выполняться в ервую очередь
        init {
            itemView.setOnClickListener {
                weatherModelTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }
        fun bind(weatherModel: WeatherModel) {
            binding.apply {
                //17.6 Указываем, что временная переменная равна переменноу из конструктора
                weatherModelTemp = weatherModel
                tvDate.text = weatherModel.time
                tvCondition.text = weatherModel.condition
                //ifEmpty - выводит другой вариант текста, если изначально строка пуста
                tvTemp.text = weatherModel.currentTemp.ifEmpty {"${weatherModel.maxTemp}C / ${weatherModel.minTemp}C"}
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

        return WeatherHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)), listener)
    }

    //9.4 Определяет озицию одной карточки
    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(getItem(position))
    }
    //17.1 Устанавливает прослушиватель в адаптер
    interface Listener{
        fun onClick(item: WeatherModel)
    }
}