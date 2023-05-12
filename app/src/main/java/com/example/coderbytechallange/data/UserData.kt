package com.example.coderbytechallange.data

import android.graphics.Bitmap

data class UserData(
    val name: String = "",
    val webSite: String = "",
    val email: String = "",
    val avatar: Bitmap? = null
)
