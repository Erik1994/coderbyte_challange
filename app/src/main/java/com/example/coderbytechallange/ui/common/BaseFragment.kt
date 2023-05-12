package com.example.coderbytechallange.ui.common

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coderbytechallange.R
import com.example.coderbytechallange.ui.extensions.collectLifeCycleFlow
import com.example.coderbytechallange.ui.navigation.NavigationCommand

abstract class BaseFragment : Fragment() {
    protected abstract val viewModel: BaseViewModel
    protected var onPermissionGranted: (() -> Unit)? = null

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val deniedPermissions = arrayListOf<String>()
            permissions.entries.forEach {
                if (!it.value) {
                    deniedPermissions.add(it.key)
                }
                deniedPermissions.size.takeIf { size -> size > 0 }?.let {
                    showDialog()
                } ?: onPermissionGranted?.invoke()
            }
        }

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

    private fun showDialog() {
        if (context == null) return
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.permission_required))
            .setMessage(getString(R.string.please_grant_all_permissions_to_use_this_app))
            .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _ ->
                dialog.dismiss()
                activity?.finish()
            }
            .setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface, _ ->
                dialog.dismiss()
                openSettingsScreen()

            }.show()
    }



    private fun openSettingsScreen() {
        activity?.let { safeActivity ->
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", safeActivity.packageName, null)
            }.also {
                startActivity(it)
            }
        }
    }

    protected fun isPermissionsGranted(permissionList: List<String>): Boolean = context?.let {
        permissionList.all { permission ->
            ContextCompat.checkSelfPermission(
                it,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    } ?: false

    protected fun requestPermission(permissionList: Array<String>) =
        requestPermissions.launch(
            permissionList
        )
}