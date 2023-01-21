package com.example.telegramclone

import android.app.Application
import com.orhanobut.hawk.Hawk

class App : Application() {
    companion object {
        var user1: String? = "Sardor"
        var user2: String? = "Axmad"
        // true bo'lsa sizni xabariz folse bo'lsa kelgan habar , bu galni almawtiriw uchun
        var userPosition: Boolean = true
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build();
    }
}