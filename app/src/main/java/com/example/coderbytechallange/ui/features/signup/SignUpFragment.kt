package com.example.coderbytechallange.ui.features.signup

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.coderbytechallange.databinding.FragmentSignUpBinding
import com.example.coderbytechallange.ui.common.BaseFragment
import com.example.coderbytechallange.ui.extensions.clicks
import com.example.coderbytechallange.ui.extensions.collectLifeCycleFlow
import com.example.coderbytechallange.ui.extensions.validate
import com.example.coderbytechallange.ui.features.shared.SignSharedVIewModel
import com.example.coderbytechallange.ui.navigation.NavigationCommand


class SignUpFragment : BaseFragment() {
    private var binding: FragmentSignUpBinding? = null
    private val resultContract = ActivityResultContracts.TakePicturePreview()
    override val viewModel: SignSharedVIewModel by activityViewModels()
    private val takePhoto = registerForActivityResult(resultContract) { bitmap ->
        bitmap?.let {
            handleAvatarImageVisibility(true)
            binding?.avatarIv?.setImageBitmap(it)
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        onPermissionGranted = {
            takePhoto.launch(null)
        }

        binding?.apply {
            collectLifeCycleFlow(avatarCv.clicks()) {
                if (!isPermissionsGranted(listOf(Manifest.permission.CAMERA))) {
                    requestPermission(arrayOf(Manifest.permission.CAMERA))
                } else takePhoto.launch(null)
            }

            collectLifeCycleFlow(submitBtn.clicks()) {
                if (emailEt.validate() && passwordEt.validate()) {
                    viewModel.navigate(NavigationCommand.To(SignUpFragmentDirections.actionSignUpFragmentToConfirmationFragment()))
                }
            }
        }
    }

    private fun handleAvatarImageVisibility(visible: Boolean) {
        binding?.apply {
            cardDescriptionTv.isVisible = !visible
            avatarIv.isVisible = visible
        }
    }
}