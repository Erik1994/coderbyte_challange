package com.example.coderbytechallange.ui.features.confirmation

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.example.coderbytechallange.R
import com.example.coderbytechallange.data.UserData
import com.example.coderbytechallange.databinding.FragmentConfirmationBinding
import com.example.coderbytechallange.ui.common.BaseFragment
import com.example.coderbytechallange.ui.extensions.collectLifeCycleFlow
import com.example.coderbytechallange.ui.features.shared.SignSharedVIewModel
import com.example.coderbytechallange.ui.state.UserDataStateHandler
import java.util.regex.Pattern


class ConfirmationFragment : BaseFragment() {
    private var binding: FragmentConfirmationBinding? = null
    override val viewModel: SignSharedVIewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

    }

    private fun observeData() {
        collectLifeCycleFlow(viewModel.userDataFlow) { state ->
            when (state) {
                is UserDataStateHandler.Updated -> setUiData(state.userData)
                is UserDataStateHandler.Default -> {}
            }
        }
    }

    private fun setUiData(userData: UserData) {
        binding?.apply {
            val url = userData.webSite
            val text = "<a href=\"$url\">$url</a>"
            titleTv.text = getString(R.string.hello, userData.name)
            nameEt.text = userData.name
            webEt.text = Html.fromHtml(text)
            webEt.movementMethod = LinkMovementMethod.getInstance()
            emailEt.text = userData.email
            userData.avatar?.let { avatarIv.setImageBitmap(it) }
        }
    }
}