package com.example.telegramclone.ui.fragmen.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.telegramclone.R
import com.example.telegramclone.databinding.FragmentEnterSmsPasswordBinding

class EnterSmsPasswordFragment : Fragment() {

    private var _binding: FragmentEnterSmsPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterSmsPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val smsCode = binding.inputSmsCode.text.toString()
        binding.enterSmsBtn.setOnClickListener {
            if (smsCode.isNotEmpty()) {

                findNavController().navigate(R.id.action_enterSmsPasswordFragment_to_baseFragment)
            }
        }
    }

}