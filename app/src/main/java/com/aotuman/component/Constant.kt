package com.aotuman.component

import android.os.Environment

object Constant {
    var appPath = (Environment.getExternalStorageDirectory().path
            + "/" + BuildConfig.APPLICATION_ID + "/")
}