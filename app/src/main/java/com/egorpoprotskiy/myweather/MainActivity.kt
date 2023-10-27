package com.egorpoprotskiy.myweather

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.egorpoprotskiy.myweather.databinding.ActivityMainBinding
import com.egorpoprotskiy.myweather.fragments.MainFragment
import org.json.JSONObject

const val API_KEY = "e9eec69e30f7493683820453232710"

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    //2 Открывает фрагмент MainFragment в активити.
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, MainFragment.newInstance())
            .commit()
    }
}