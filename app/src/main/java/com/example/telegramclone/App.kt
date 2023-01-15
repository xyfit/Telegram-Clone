package com.example.telegramclone

import android.app.Application

class App : Application() {
    companion object {
        var user1: String? = null
        var user2: String? = null
        // true bo'lsa sizni xabariz folse bo'lsa kelgan habar , bu galni almawtiriw uchun
        var userPosition: Boolean = true
    }
}