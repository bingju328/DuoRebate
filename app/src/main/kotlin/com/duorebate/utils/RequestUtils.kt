package com.duorebate.utils

import android.webkit.MimeTypeMap
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

/**
 * Created by bingj on 2017/8/7.
 */

/**
 * 将String数据转化为RequestBody形式
 * @param value 文本对象
 * *
 * @return
 */
fun parseRequestBody_String(value: String?): RequestBody {
    if (value != null) {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    } else {
        return RequestBody.create(MediaType.parse("text/plain"), "")
    }

}

/**
 * 将File数据转化为RequestBody形式
 * @param file 文件对象
 * *
 * @return
 */
fun parseRequestBody_File(file: File): RequestBody {
    return RequestBody.create(MediaType.parse("image/*"), file)
}

fun parseImageMapKey(key: String, fileName: String): String {
    return key + "\"; filename=\"" + fileName
}

fun get_file_mimetype(file: File): String {
    val mimeType = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(file.path))
    return mimeType
}