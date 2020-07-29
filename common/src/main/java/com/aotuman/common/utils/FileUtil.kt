package com.aotuman.common.utils

import java.io.File

object FileUtil {
    /**
     * 创建文件方法，有旧文件，立即删除重新创建
     *
     * @param dir      所在目录 如 /store/0/sdcard/package
     * @param filename 文件名 如 "xxxx.mp3"
     * 完整路径： /store/0/sdcard/package/xxxx.mp3
     */
    @Throws(Exception::class)
    fun createFile(dir: String?, filename: String?): File {
        val file = File(dir, filename)
        if (file.exists()) {
            file.delete()
        }
        val directory = File(dir)
        if (directory.exists()) {
            if (directory.isDirectory) {
//                file.createNewFile();
            } else {
                //不是目录，得删掉重建
                directory.delete()
                if (!directory.mkdirs()) {
                    throw Exception("创建文件目录失败！")
                }
            }
        } else {
            if (!directory.mkdirs()) {
                throw Exception("创建文件目录失败！")
            }
        }
        file.createNewFile()
        return file
    }

}