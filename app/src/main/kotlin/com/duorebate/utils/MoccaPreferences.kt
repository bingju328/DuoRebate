package com.duorebate.utils

import android.content.Context
import com.spcenter.PreferenceFile

/**
 * Created by bingju on 2017/7/1.
 */
class MoccaPreferences {
    companion object {
        val sPrefs: PreferenceFile by lazy(LazyThreadSafetyMode.NONE){
            PreferenceFile("Loan_app", Context.MODE_PRIVATE)
        }

        /**判断用户的token值*/
        val token = sPrefs.value("token","")
        /** 是否第一次登录*/
        val is_first_login = sPrefs.value("login",true)
        /** 用户名字*/
        val realName = sPrefs.value("realName","")
        /** 用户类型*/
        val userType = sPrefs.value("userType","")
        /** 用户登录手机*/
        val loginPhone = sPrefs.value("loginPhone","")
        /** 是否是普通客户登录*/
        val is_client = sPrefs.value("client",true)
        val deviceId = sPrefs.value("deviceId","1")
        /** 工号*/
        val userCode = sPrefs.value("userCode","")
        /** 岗位*/
        val userRole = sPrefs.value("userRole","")
        /** 机构编号*/
        val userBranch = sPrefs.value("userBranch","")
        /** 法人编号*/
        val bankCode = sPrefs.value("bankCode","")

        fun clearPreference(){
            token.put("")
//            is_login.put(false)
            realName.put("")
            userType.put("")
//            loginPhone.put("")
            is_client.put(true)
            deviceId.put("1")
            userCode.put("")
            userRole.put("")
            userBranch.put("")
        }
    }
}