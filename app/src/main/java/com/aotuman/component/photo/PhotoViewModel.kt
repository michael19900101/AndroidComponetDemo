package com.aotuman.component.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aotuman.component.Constant

class PhotoViewModel : ViewModel() {
    var fileName: String? = null
    val fileDir: String = Constant.appPath +  "/watermark/"
    var result = MutableLiveData<Boolean>()
}