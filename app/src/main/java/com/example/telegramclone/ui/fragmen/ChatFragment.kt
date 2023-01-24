package com.example.telegramclone.ui.fragmen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telegramclone.PrefUtils
import com.example.telegramclone.adapters.MessageAdapter
import com.example.telegramclone.databinding.FragmentChatBinding
import com.example.telegramclone.models.MessageModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val messageAdapter: MessageAdapter by lazy { MessageAdapter() }

    val args: ChatFragmentArgs by navArgs()

    private val database = Firebase.database
    private val myRef = database.getReference("")

    private  var senderRoom: String? = null
    private  var receiverRoom: String? = null
    private val messageList = ArrayList<MessageModel>()

    private val senderUid: String = PrefUtils.firstRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUid()
        initRecyclerView()
        initBtn()
        initToolbar()
    }

    private fun initOnlineOrOfflineMode() {
        args.modelKey?.user?.let {
            myRef.child("presence").child(it).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){/*snapshot null bo'lmasa true yani snapshot mavjud bo'lsa*/
                        val status = snapshot.getValue(Boolean::class.java)
                        status?.let { possition ->/*tepadagi it bilan almawib ketmaslig uchun nomi o'zgardi*/
                            binding.statusText.text = if (possition)"Online" else "Offline"
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    private fun initUid() {
        senderRoom = PrefUtils.firstRegister.trim() + "_" + args.modelKey!!.user.trim()
        receiverRoom = args.modelKey!!.user.trim() + "_" + PrefUtils.firstRegister.trim()
    }

    private fun initToolbar() {
        initOnlineOrOfflineMode()
        binding.titleChat.text = args.modelKey?.user/*user name*/
        binding.materialToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initRecyclerView() {
        binding.chatRec.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }
        myRef.child("chats").child("$senderRoom").child("messages")
            .addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (oneMsg in snapshot.children){
//                    oneMsg?.let {
                        val message: MessageModel? = oneMsg.getValue(MessageModel::class.java)
                        message!!.jonatuvchiUid = oneMsg.key!!
                        messageList.add(message)
                    }
//                }
                Log.d("dssfd", messageList.toString())
                messageAdapter.addMessage(messageList)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initBtn() {
        binding.btnSend.setOnClickListener {
            val editext = binding.txtMessage.text.toString()
            if (editext.isNotEmpty()){
                val time = Date().time
                sndMessage(MessageModel("$senderUid", PrefUtils.firstRegister, binding.txtMessage.text.toString(), time,0))
                // scroll the RecyclerView to the last added element
                binding.chatRec.scrollToPosition(messageAdapter.itemCount - 1)
                binding.txtMessage.text = null
            }else{
                Toast.makeText(requireContext(),"Message should not be empty", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun sndMessage(messageModel: MessageModel){
        val randomKey = myRef.child("chats").push().key

        val lastMsg = HashMap<String, Any>()
        lastMsg["lastMsg"] = messageModel
        lastMsg["lastTime"] =  messageModel.time

        myRef.child("chats").child(senderRoom!!).updateChildren(lastMsg)
        myRef.child("chats").child(receiverRoom!!).updateChildren(lastMsg)

        myRef.child("chats").child(senderRoom!!)
            .child("messages").child(randomKey!!)
            .setValue(messageModel)
            .addOnSuccessListener {
                myRef.child("chats").child(receiverRoom!!)
                    .child("messages")
                    .child(randomKey)
                    .setValue(messageModel)
                    .addOnSuccessListener {}
            }

    }


}