package com.example.coderbytechallange.ui.state

import com.example.coderbytechallange.data.UserData

sealed interface UserDataStateHandler {
    object Default: UserDataStateHandler
    class Updated(val userData: UserData): UserDataStateHandler
}