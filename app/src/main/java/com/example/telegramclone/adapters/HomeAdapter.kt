package com.example.telegramclone.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.telegramclone.R
import com.example.telegramclone.databinding.BaseItemLyBinding
import com.example.telegramclone.models.MessageModel

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ItemHolder>(){

    inner class ItemHolder(val b: BaseItemLyBinding): RecyclerView.ViewHolder(b.root){
        fun bind(itemData: MessageModel){
            b.shortcutWidgetItemText.text = itemData.user
            b.shortcutWidgetItemTime.text = DateUtils.fromMillisToTimeString(itemData.time)
            b.shortcutWidgetItemMessage.text = itemData.message
            if (itemData.newMsg == 0){
                b.shortcutWidgetItemBadge.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF8B8D8F"))
                b.shortcutWidgetItemBadge.text = "0"
            }else{
                b.shortcutWidgetItemBadge.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.purple_200))
                b.shortcutWidgetItemBadge.text = itemData.newMsg.toString()
            }

        }
    }

    var baseList = emptyList<MessageModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClickListener: ((MessageModel) -> Unit)? = null
    fun setOnItemClickListener(listener: ((MessageModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       return ItemHolder(BaseItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]
        holder.bind(itemData)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }
    }

    override fun getItemCount(): Int = baseList.size
}