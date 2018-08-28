package com.duorebate.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.duorebate.R
import java.util.*

/**
 * Created by bingj on 2017/7/19.
 */
/**
 * 新的权限机制更好的保护了用户的隐私，Google将权限分为两类，一类是Normal Permissions，
 * 这类权限一般不涉及用户隐私，是不需要用户进行授权的，比如手机震动、访问网络等；另一类
 * 是Dangerous Permission，一般是涉及到用户隐私的，需要用户进行授权，比如读取sdcard、访问通讯录等。
 *
 * 如果app运行在android 6.x的机器上，对于授权机制是这样的。如果你申请某个危险的权限，假设你的app
 * 早已被用户授权了同一组的某个危险权限，那么系统会立即授权，而不需要用户去点击授权。比如你的app
 * 对READ_CONTACTS已经授权了，当你的app申请WRITE_CONTACTS时，系统会直接授权通过。此外，对于申请
 * 时弹出的dialog上面的文本说明也是对整个权限组的说明，而不是单个权限（ps:这个dialog是不能进行定制的）。
 *
 * 不过需要注意的是，不要对权限组过多的依赖，尽可能对每个危险权限都进行正常流程的申请，
 * 因为在后期的版本中这个权限组可能会产生变化。
 * */

/**
 * 使用麦克风的权限
 */
val CODE_RECORD_AUDIO = 0
val CODE_GET_ACCOUNTS = 1
val CODE_READ_PHONE_STATE = 2
/**
 * 拨打电话的权限
 */
val CODE_CALL_PHONE = 3
/**
 * 相机权限
 */
val CODE_CAMERA = 4
/**
 * 透过GPSs取得位置,定位权限
 */
val CODE_ACCESS_FINE_LOCATION = 5
val CODE_ACCESS_COARSE_LOCATION = 6
val CODE_READ_EXTERNAL_STORAGE = 7
val CODE_WRITE_EXTERNAL_STORAGE = 8
val CODE_MULTI_PERMISSION = 100

val PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
val PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS
val PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
val PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE
val PERMISSION_CAMERA = Manifest.permission.CAMERA
val PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
val PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
val PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
/**
 * 挂载sd卡权限
 */
val PERMISSION_MOUNT_UNMOUNT_FILESYSTEMS = Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS

private val requestPermissions = arrayOf(PERMISSION_RECORD_AUDIO,
        PERMISSION_GET_ACCOUNTS, PERMISSION_READ_PHONE_STATE,
        PERMISSION_CALL_PHONE, PERMISSION_CAMERA,
        PERMISSION_ACCESS_FINE_LOCATION, PERMISSION_ACCESS_COARSE_LOCATION,
        PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE)

/**
 * Requests permission.
 *
 * @param activity
 * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
 */
fun requestPermission(activity: Activity,requestCode: Int,permissionGrant: (requestCode: Int)-> Unit) {
    if (activity == null) return
    if (requestCode < 0 || requestCode >= requestPermissions.size) {
        return
    }
    val requestPermission = requestPermissions[requestCode]
    //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
    // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
    // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);
    if (Build.VERSION.SDK_INT < 23) {
        permissionGrant(requestCode)
        return
    }
    val checkSelfPermission: Int
    try {
        checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission)
    } catch (e: RuntimeException) {
        return
    }

    if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {//权限没有被授予


        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
            //展示给用户需要这个权限的具体原因
            //                shouldShowRationale(activity, requestCode, requestPermission);
            //显示请求权限的Snake
            ActivityCompat.requestPermissions(activity,
                    arrayOf(requestPermission),
                    requestCode)

        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(requestPermission), requestCode)
        }

    } else {//权限已被授予
        //            Toast.makeText(activity, "opened:" + requestPermissions[requestCode], Toast.LENGTH_SHORT).show();
        permissionGrant(requestCode)
    }

}

private fun showMessageOKCancel(context: Activity,message: String,okListener: (dialog: DialogInterface, which: Int)->Unit) {
    AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK",okListener)
            .setNegativeButton("Cancel",null)
            .create()
            .show()
}

private fun requestMultiResult(activity: Activity?, permissions: Array<String>?, grantResults: IntArray?, permissionGrant: (requestCode: Int)->Unit) {

    if (activity == null) {
        return
    }

    //TODO
    val perms = HashMap<String, Int>()

    val notGranted = ArrayList<String>()
    for (i in permissions!!.indices) {
        perms.put(permissions[i], grantResults!![i])
        if (grantResults!![i] != PackageManager.PERMISSION_GRANTED) {
            notGranted.add(permissions[i])
        }
    }

    if (notGranted.size == 0) {
        permissionGrant(CODE_MULTI_PERMISSION)
    } else {
        openSettingActivity(activity, "请在\"设置-应用-选择应用-权限管理\"中开启SD读写权限和相机权限。")
    }

}

private fun openSettingActivity(activity: Activity, message: String) {
    showMessageOKCancel(activity,message){dialog, which ->
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }
}
/**
 * 一次申请多个权限
 */
fun requestMultiPermissions(activity: Activity, multiPermissions: Array<String>, permissionGrant: (requestCode: Int)-> Unit) {

    val permissionsList = getNoGrantedPermission(activity, multiPermissions, false)
    val shouldRationalePermissionsList = getNoGrantedPermission(activity, multiPermissions, true)

    //TODO checkSelfPermission
    if (permissionsList == null || shouldRationalePermissionsList == null) {
        return
    }

    if (permissionsList!!.size > 0) {
        ActivityCompat.requestPermissions(activity, permissionsList!!.toTypedArray(),
                CODE_MULTI_PERMISSION)

    } else if (shouldRationalePermissionsList!!.size > 0) {
        showMessageOKCancel(activity,"系统正在审请相机权限"){dialog, which ->
            ActivityCompat.requestPermissions(activity,shouldRationalePermissionsList!!.toTypedArray(), CODE_MULTI_PERMISSION)
        }
    } else {
        permissionGrant(CODE_MULTI_PERMISSION)
    }

}

/**
 * @param activity
 * *
 * @param requestCode  Need consistent with requestPermission
 * *
 * @param permissions
 * *
 * @param grantResults
 */
fun requestPermissionsResult(activity: Activity?, requestCode: Int, permissions: Array<String>?,
                             grantResults: IntArray?, permissionGrant: (requestCode: Int)->Unit) {

    if (activity == null) {
        return
    }

    if (requestCode == CODE_MULTI_PERMISSION) {
        requestMultiResult(activity, permissions, grantResults, permissionGrant)
        return
    }

    if (requestCode < 0 || requestCode >= requestPermissions.size) {
        //            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
        return
    }


    if (grantResults!!.size == 1 && grantResults!![0] == PackageManager.PERMISSION_GRANTED) {
        //TODO success, do something, can use callback
        permissionGrant(requestCode)

    } else {
        //TODO hint user this permission function
        //TODO
        val permissionsHint = activity.resources.getStringArray(R.array.permissions)
        openSettingActivity(activity, permissionsHint[requestCode])
    }

}
/**
 * @param activity
 * *
 * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
 * *
 * @return
 */
fun getNoGrantedPermission(activity: Activity, multiPermissions: Array<String>, isShouldRationale: Boolean): ArrayList<String>? {

    val permissions = ArrayList<String>()

    for (i in multiPermissions.indices) {
        val requestPermission = multiPermissions[i]


        //TODO checkSelfPermission
        var checkSelfPermission = -1
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission)
        } catch (e: RuntimeException) {
            Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                    .show()
            return null
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                if (isShouldRationale) {
                    permissions.add(requestPermission)
                }

            } else {

                if (!isShouldRationale) {
                    permissions.add(requestPermission)
                }
            }

        }
    }

    return permissions
}