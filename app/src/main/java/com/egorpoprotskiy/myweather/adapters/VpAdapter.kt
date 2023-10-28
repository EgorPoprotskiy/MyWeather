package com.egorpoprotskiy.myweather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//7 Адаптер для ViewPager 2
class VpAdapter(fa: FragmentActivity, private val list: List<Fragment>): FragmentStateAdapter(fa) {
    //функция возвращает кол-во элементов в списке(их 2(0 и 1), т.к. в TabLayout мы добавили вкладки HOURS и DAYS)
    override fun getItemCount(): Int {
        return list.size
    }
    //функция запускает нужный фрагмент в зависимости от позиции элемента в списке(0 или 1)
    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}