package com.example.coderbytechallange.ui.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coderbytechallange.databinding.FragmentSignUpBinding
import com.example.coderbytechallange.ui.common.BaseFragment


class SignUpFragment : BaseFragment() {
    private var binding: FragmentSignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        }
        return binding?.root
    }
}