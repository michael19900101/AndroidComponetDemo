package com.aotuman.component

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aotuman.common.permission.PermissionTipsDialog
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    val message = "PermissionX需要您同意以下权限才能正常使用"
                    val dialog = PermissionTipsDialog(
                        this,
                        message,
                        deniedList
                    )
                    scope.showRequestReasonDialog(dialog)
                }
                .onForwardToSettings { scope, deniedList ->
                    val message = "您需要去设置中手动开启以下权限"
                    val dialog = PermissionTipsDialog(
                        this,
                        message,
                        deniedList
                    )
                    scope.showForwardToSettingsDialog(dialog)
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}