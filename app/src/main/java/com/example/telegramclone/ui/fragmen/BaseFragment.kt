package com.example.telegramclone.ui.fragmen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.telegramclone.R
import com.example.telegramclone.databinding.AppBarMainBinding
import com.example.telegramclone.databinding.FragmentBaseBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    lateinit var appBarMainBinding: AppBarMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBaseBinding.inflate(inflater, container , false)
        appBarMainBinding = binding.mainAppBarId
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initDrawerLy()
        initBtn()

    }

    private fun initBtn() {
        appBarMainBinding.openFr.setOnClickListener {
            findNavController().navigate(R.id.action_baseFragment_to_chatFragment)
        }
    }

    private fun initDrawerLy() {
        appBarMainBinding.mToolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }
    }

    private fun initToolbar(){
//        setSupportActionBar(binding.mToolbar)
    }
    private fun writeDataFCM(){
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}