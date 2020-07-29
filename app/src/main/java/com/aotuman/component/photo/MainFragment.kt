package com.aotuman.component.photo

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aotuman.common.permission.PermissionTipsDialog
import com.aotuman.component.R
import com.bumptech.glide.Glide
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*


class MainFragment : Fragment() {

    private lateinit var viewModel: PhotoViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PhotoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireView())
        button.setOnClickListener {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    val message = "PermissionX需要您同意以下权限才能正常使用"
                    val dialog = PermissionTipsDialog(
                        context!!,
                        message,
                        deniedList
                    )
                    scope.showRequestReasonDialog(dialog)
                }
                .onForwardToSettings { scope, deniedList ->
                    val message = "您需要去设置中手动开启以下权限"
                    val dialog = PermissionTipsDialog(
                        context!!,
                        message,
                        deniedList
                    )
                    scope.showForwardToSettingsDialog(dialog)
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        takePhoto()
//                        Toast.makeText(activity, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        viewModel.result.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewModel.result.value == true) {
                Glide.with(imageView).load(viewModel.fileDir+viewModel.fileName).into(imageView)
            }
        })
    }


    private fun takePhoto() {
        val fileName = UUID.randomUUID().toString() + ".png"
        viewModel.fileName = fileName
        navController.navigate(R.id.action_mainFragment_to_cameraFragment)
    }
}