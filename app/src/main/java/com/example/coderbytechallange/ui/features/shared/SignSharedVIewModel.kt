package com.example.coderbytechallange.ui.features.shared

import com.example.coderbytechallange.ui.common.BaseViewModel
import com.example.coderbytechallange.ui.state.UserDataStateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignSharedVIewModel: BaseViewModel() {
    private val _userDataFlow = MutableStateFlow<UserDataStateHandler>(UserDataStateHandler.Default)
    val userDataFlow: StateFlow<UserDataStateHandler> = _userDataFlow

    fun updateUserDataState(userDataStateHandler: UserDataStateHandler) {
        _userDataFlow.value = userDataStateHandler
    }
}