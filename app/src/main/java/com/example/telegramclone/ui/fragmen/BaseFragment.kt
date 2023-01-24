package com.example.telegramclone.ui.fragmen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telegramclone.PrefUtils
import com.example.telegramclone.R
import com.example.telegramclone.adapters.HomeAdapter
import com.example.telegramclone.databinding.AppBarMainBinding
import com.example.telegramclone.databinding.FragmentBaseBinding
import com.example.telegramclone.models.MessageModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    lateinit var appBarMainBinding: AppBarMainBinding
    private val adapter by lazy { HomeAdapter() }

    private val database = Firebase.database
    private val myRef = database.getReference("")

    private val args: BaseFragmentArgs by navArgs()

    private val usersList = ArrayList<MessageModel>()
    private lateinit var uidUser: String

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

//        appBarMainBinding.loginLabel.text = getStr
        uidUser = PrefUtils.firstRegister

        initDrawerLy()
        initBtn()
        initRec()
        initFCM()

    }

    private fun initFCM() {
        myRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for (dataSnapshot in snapshot.children){
                        if ( dataSnapshot.key!! != PrefUtils.firstRegister/*user name*/){
                            val getName = dataSnapshot.child("name").value.toString()
                            val lastMessage = ""
                            val unreadMessagesCount = 0
                            usersList.add(MessageModel("",getName, lastMessage, 123546, unreadMessagesCount))

                        }

                }
                adapter.baseList = usersList

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun initRec() {
        appBarMainBinding.homeRecycler.layoutManager = LinearLayoutManager(requireContext())
        appBarMainBinding.homeRecycler.adapter = adapter

//        adapter.baseList = list
        adapter.setOnItemClickListener {
            val action = BaseFragmentDirections.actionBaseFragmentToChatFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initBtn() {
//        appBarMainBinding.btnLogin.setOnClickListener {
//            activity?.onBackPressed()
////            if (appBarMainBinding.username1.text.isNotEmpty() && appBarMainBinding.username2.text.isNotEmpty()) {
////                val user1 = appBarMainBinding.username1.text.toString()
////                val user2 = appBarMainBinding.username2.text.toString()
////
////                App.user1 = user1
////                App.user2 = user2
////                findNavController().navigate(R.id.action_baseFragment_to_chatFragment)
////            } else {
////                Toast.makeText(requireContext(),"Username should not be empty", Toast.LENGTH_SHORT).show()
////            }
//
//        }
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
        val headerView = binding.navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.user_name_drawable).text = args.strKey

    }

    private fun writeDataFCM(){
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }

//    override fun onResume() {
//        super.onResume()
//        myRef.child("presence").child(uidUser).setValue(true)//Online
//    }
//
//    override fun onPause() {
//        super.onPause()
//        myRef.child("presence").child(uidUser).setValue(false)//Offline
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}