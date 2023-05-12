package com.example.coderbytechallange.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coderbytechallange.ui.extensions.BaseViewModel
import com.example.coderbytechallange.ui.extensions.collectLifeCycleFlow
import com.example.coderbytechallange.ui.navigation.NavigationCommand

abstract class BaseFragment : Fragment() {
    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectLifeCycleFlow(viewModel.navigationFlow) { command ->
            when (command) {
                is NavigationCommand.Back -> findNavController().navigateUp()
                is NavigationCommand.To -> findNavController().navigate(command.direction)
            }
        }
    }
}