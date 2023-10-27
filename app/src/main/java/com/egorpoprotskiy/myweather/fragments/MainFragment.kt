package com.egorpoprotskiy.myweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egorpoprotskiy.myweather.R
import com.egorpoprotskiy.myweather.databinding.FragmentMainBinding

//2 Создание фрагмента
class MainFragment : Fragment() {
    //2
    private lateinit var binding: FragmentMainBinding
//    private val _binding get() = binding

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}