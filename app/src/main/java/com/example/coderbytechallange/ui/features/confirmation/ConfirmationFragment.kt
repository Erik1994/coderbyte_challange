package com.example.coderbytechallange.ui.features.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coderbytechallange.databinding.FragmentConfirmationBinding
import com.example.coderbytechallange.ui.common.BaseFragment


class ConfirmationFragment : BaseFragment() {
    private var binding: FragmentConfirmationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentConfirmationBinding.inflate(layoutInflater, container, false)
        }
        return binding?.root
    }
}