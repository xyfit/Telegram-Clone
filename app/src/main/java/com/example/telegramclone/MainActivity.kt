package com.example.telegramclone

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.telegramclone.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navContainer:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navContainer  = Navigation.findNavController(this, R.id.fragmentContainerView)
        navContainer.run {}
//   initWindow()

    }

    companion object{
        var statusBarHeight = 0
    }
    private fun initWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        statusBarHeight = getStatusBarHeight()
        binding.root.fitsSystemWindows = true;
    }
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    private val database = Firebase.database
    private val myRef = database.getReference("")
    private val user = PrefUtils.firstRegister
    override fun onResume() {
        super.onResume()
        if (user.isNotBlank()){
        myRef.child("presence").child(user).setValue(true)//Online
        }
    }

    override fun onPause() {
        super.onPause()
        if (user.isNotBlank()){
        myRef.child("presence").child(user).setValue(false)//Offline
        }
    }

}