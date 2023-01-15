package com.example.telegramclone.models

data class Message(
    var user: String,
    var message: String,
    var time: Long
)