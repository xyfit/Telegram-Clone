package com.example.telegramclone.ui.fragmen.register

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.telegramclone.PrefUtils
import com.example.telegramclone.R
import com.example.telegramclone.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    val database = Firebase.database
    val myRef = database.getReference("users")

    private var auth: FirebaseAuth = Firebase.auth
    private var selectImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getUserName = PrefUtils.firstRegister

        if (getUserName.isNotEmpty()){
            val action = LogInFragmentDirections.actionLogInFragmentToBaseFragment2(/*user name*/getUserName)
            findNavController().navigate(action)
        }

        binding.startTelegram.setOnClickListener {
            val name = binding.userName.text.toString()
            val phone = binding.inputPhoneNumber.text.toString()

            if(name.isNotEmpty()){
                if (phone.isNotEmpty()){
                    myRef.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val uid = auth.uid
                            val uid = name
                           if (snapshot.hasChild("$name")){
                               Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
                           }else{
                               myRef.child("$uid").child("name").setValue(name)
                               myRef.child("$uid").child("phone").setValue(phone)
                           }
                            PrefUtils.firstRegister = name
                            val action = LogInFragmentDirections.actionLogInFragmentToBaseFragment2(/*user name*/name)
                            findNavController().navigate(action)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }else{
                    Toast.makeText(requireContext(), "Enter a number", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Enter a your name", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
//        updateUI(currentUser)

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
//            val action = LogInFragmentDirections.actionLogInFragmentToBaseFragment2(/*user name*/getUserName)
            findNavController().navigate(R.id.action_logInFragment_to_baseFragment2)
        }
    }
}