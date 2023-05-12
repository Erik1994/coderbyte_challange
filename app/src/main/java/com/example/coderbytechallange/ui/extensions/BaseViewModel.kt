package com.example.coderbytechallange.ui.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coderbytechallange.ui.navigation.NavigationCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    private val _navigationFlow = MutableSharedFlow<NavigationCommand>()
    val navigationFlow: SharedFlow<NavigationCommand> = _navigationFlow

    fun navigate(navigationCommand: NavigationCommand) {
        viewModelScope.launch {
            _navigationFlow.emit(navigationCommand)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationCommand.Back)
        }
    }
}