package com.aotuman.component.photo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aotuman.common.utils.FileUtil
import com.aotuman.component.R
import java.io.File


class CameraFragment : Fragment() {

    private var fileName: String? = null
    private var fileDir: String? = null
    private var file: File? = null

    companion object {
        private const val ActivityResult_CAMERA_WITH_DATA = 100
    }

    private lateinit var viewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PhotoViewModel::class.java)
        fileDir = viewModel.fileDir
        fileName = viewModel.fileName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        openCamera()
        return inflater.inflate(R.layout.fragment_take_photo, container, false)
    }

    private fun openCamera() {
        file = try {
            FileUtil.createFile(fileDir, fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            Handler().post {
                Toast.makeText(context, e.message + ",无法进行拍照", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            return
        }
        //提前设定好图片路径
        val photoUri: Uri = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Uri.fromFile(file)
        } else {
            /*
              7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             */
            FileProvider.getUriForFile(context!!, context!!.packageName + ".fileProvider", file!!)

        }
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        // 4.0以上
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0) // 低质量
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        val info = activity!!.packageManager.queryIntentActivities(
            cameraIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        if (info.size != 0) {
            startActivityForResult(cameraIntent,
                ActivityResult_CAMERA_WITH_DATA
            )
        } else {
            val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
            toast.setText(R.string.cant_find_camera_hint)
            toast.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ActivityResult_CAMERA_WITH_DATA -> if (resultCode == Activity.RESULT_OK) {

                // Android 相机应用会对返回 Intent（作为 extra 中的小型 Bitmap 传递给 onActivityResult()，使用键 "data"）中的照片进行编码。
//                val thumbBitmap = data?.extras?.get("data") as? Bitmap
//                viewModel.thumbBitmap.postValue(thumbBitmap)

                viewModel.result.postValue(true)
                handlePicture()
            }
        }
        findNavController().popBackStack()
    }

    private fun handlePicture() {

    }
}