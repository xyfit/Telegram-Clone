package com.example.telegramclone.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val uid: String,
    val name: String,
    val phone: String
): Parcelable
