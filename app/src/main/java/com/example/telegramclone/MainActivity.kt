package com.example.telegramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.telegramclone.databinding.ActivityMainBinding
import com.example.telegramclone.databinding.AppBarMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navContainer:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navContainer  = Navigation.findNavController(this, R.id.fragmentContainerView)
        navContainer.run {

        }
    }

}