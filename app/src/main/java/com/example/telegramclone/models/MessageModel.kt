package com.example.telegramclone.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    var jonatuvchiUid: String = "",
    var user: String = "",
    val img: String? = null,
    var message: String = "",
    var time: Long = 0,
    val newMsg: Int = 0
) : Parcelable