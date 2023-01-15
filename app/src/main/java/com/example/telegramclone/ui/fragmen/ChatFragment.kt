package com.example.telegramclone.ui.fragmen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telegramclone.App
import com.example.telegramclone.R
import com.example.telegramclone.adapters.MessageAdapter
import com.example.telegramclone.databinding.FragmentBaseBinding
import com.example.telegramclone.databinding.FragmentChatBinding
import com.example.telegramclone.models.Message
import com.google.android.gms.common.util.DataUtils

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val messageAdapter: MessageAdapter by lazy { MessageAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initBtn()
    }

    private fun initRecyclerView() {
        binding.chatRec.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }
    }

    private fun initBtn() {
        binding.btnSend.setOnClickListener {
            if (binding.txtMessage.text.isNotEmpty()){
                if (App.userPosition){
                    messageAdapter.addMessage(Message(App.user1!!, binding.txtMessage.text.toString(), 1223L))
                }else{
                   messageAdapter.addMessage(Message(App.user2!!, binding.txtMessage.text.toString(), 1223L))
                }
                App.userPosition = !App.userPosition
                // scroll the RecyclerView to the last added element
                binding.chatRec.scrollToPosition(messageAdapter.itemCount - 1);
                binding.txtMessage.text = null
            }else{
                Toast.makeText(requireContext(),"Message should not be empty", Toast.LENGTH_SHORT).show()
            }

        }
    }


}