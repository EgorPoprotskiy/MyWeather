package com.egorpoprotskiy.myweather.fragments

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
//5 Проверка разрешения на геолокацию(данная проверка проходит перед показом диалогового окна)
fun Fragment.isPermissionGranted(p: String): Boolean {
    //5 данная функция возвращает число 0 или -1, поэтому ее сравнивают с PackageManager.PERMISSION_GRANTED, чтобы получить true или false
    return ContextCompat.checkSelfPermission(activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED
}