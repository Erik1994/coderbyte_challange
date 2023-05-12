package com.example.coderbytechallange.ui.features.signup

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.coderbytechallange.data.UserData
import com.example.coderbytechallange.databinding.FragmentSignUpBinding
import com.example.coderbytechallange.ui.common.BaseFragment
import com.example.coderbytechallange.ui.constant.USER_DEFAULT_NAME
import com.example.coderbytechallange.ui.extensions.clicks
import com.example.coderbytechallange.ui.extensions.collectLifeCycleFlow
import com.example.coderbytechallange.ui.extensions.orDefault
import com.example.coderbytechallange.ui.extensions.validate
import com.example.coderbytechallange.ui.features.shared.SignSharedVIewModel
import com.example.coderbytechallange.ui.navigation.NavigationCommand
import com.example.coderbytechallange.ui.state.UserDataStateHandler


class SignUpFragment : BaseFragment() {
    private var binding: FragmentSignUpBinding? = null
    private val resultContract = ActivityResultContracts.TakePicturePreview()
    override val viewModel: SignSharedVIewModel by activityViewModels()
    private var userData: UserData = UserData()
    private val takePhoto = registerForActivityResult(resultContract) { bitmap ->
        bitmap?.let {
            handleAvatarImageVisibility(true)
            userData = userData.copy(avatar = it)
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
                    viewModel.updateUserDataState(UserDataStateHandler.Updated(getUserDate()))
                    viewModel.navigate(NavigationCommand.To(SignUpFragmentDirections.actionSignUpFragmentToConfirmationFragment()))
                }
            }
        }
    }

    private fun getUserDate() = binding?.run {
        val email = emailEt.text?.toString().orEmpty()
        val name = nameEt.text?.toString().orDefault(USER_DEFAULT_NAME)
        val website = webEt.text?.toString().orEmpty()
        userData.copy(email = email, name = name, webSite = website)
    }.orDefault(userData)


    private fun handleAvatarImageVisibility(visible: Boolean) {
        binding?.apply {
            cardDescriptionTv.isVisible = !visible
            avatarIv.isVisible = visible
        }
    }
}