package com.example.telegramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telegramclone.App
import com.example.telegramclone.databinding.MyMessageItemLyBinding
import com.example.telegramclone.databinding.OtherMessageItemLyBinding
import com.example.telegramclone.models.MessageModel

class MessageAdapter: RecyclerView.Adapter<MessageViewHolder>() {

    companion object{
        private const val VIEW_TYPE_MY_MESSAGE = 1
        private const val VIEW_TYPE_OTHER_MESSAGE = 2
    }
    private val messagesList: ArrayList<MessageModel> = ArrayList()

    fun addMessage(message: MessageModel){
        messagesList.add(message)
        notifyDataSetChanged()
    }
    inner class MyMessageViewHolder (b: MyMessageItemLyBinding) : MessageViewHolder(b.root) {
        private var messageText: TextView = b.txtMyMessage
        private var timeText: TextView = b.txtMyMessageTime

        override fun messageBind(message: MessageModel) {
            messageText.text = message.message
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }
    inner class OtherMessageViewHolder (b: OtherMessageItemLyBinding) : MessageViewHolder(b.root) {
        private var messageText: TextView = b.txtOtherMessage
        private var userText: TextView = b.txtOtherUser
        private var timeText: TextView = b.txtOtherMessageTime

        override fun messageBind(message: MessageModel) {
            messageText.text = message.message
            userText.text = message.user
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(MyMessageItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            OtherMessageViewHolder(OtherMessageItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val itemData = messagesList[position]
        holder.messageBind(itemData)
    }

    override fun getItemCount(): Int  = messagesList.size

    override fun getItemViewType(position: Int): Int {
        val message = messagesList[position]

        return if(App.user1 == message.user) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

}
open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun messageBind(message:MessageModel) {}
}