package com.example.telegramclone.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    var user: String,
    var message: String,
    var time: Long,
    val newMsg: Int
) : Parcelable