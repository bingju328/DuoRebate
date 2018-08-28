package com.duorebate.utils

/**
 * Created by bingj on 2017/6/27.
 */

const val successCode = "0000"
const val failCode = "9999"

class REGISTER{
    companion object  {
        const val LOGIN_AMOUNT = 1  //用户登录
        const val REGISTER_AMOUNT = 2  //用户注册
        const val FORGET_PASSWORD = 3  //忘记密码
        const val CHANGE_PASSWORD = 4  //更改密码
    }
}
enum class SkipEvent(val type: String){
    RegisterType("register_type")
}
const val ALBUM_MULT_COUNT = 5
class IMAGEUTIL{
    companion object {
        const val REQUEST_CODE_FROM_CAMERA = 1 shl 10 //拍照
        const val REQUEST_CODE_FROM_ALBUM =1 shl 12  //相册
        const val REQUEST_CODE_CROP_IMAGE = 1 shl 14 //裁剪
        const val REQUEST_CODE_FROM_ALBUM_MULT = 1 shl 16//相册多选
        const val IMAGE_URL_HOME = "https://47.92.52.10:8001/upload/"
    }
}

/**
 * 客户文件
 */
class CLIENT_FILE{
    companion object {
        const val BORROWER_IDENTITY = 1001 //1001：借款人身份证件；borrower_identity
        const val BORROWER_REGISTER = 1002 //1002：借款人户口簿；register
        //内部员工
        const val BORROWER_PLEDGE = 1027 //抵/质押物价格证明；pledge
        const val BORROWER_PLEDGE_AGREE = 1028 //1028：抵/质押物有权处分人同意抵（质）证明；pledge_agree
        const val BORROWER_PLEDGE_REPORT = 1029 //1029：抵押物/质押物的估价报告（原件 pledge_report
        //普通用户
        const val CLIENT_ASSET_INFO = 1047 //1047：客户资产明细
        const val SPOUSE_IDENTITY = 1050 //1050：配偶身份证
        const val MARRY_IDENTITY = 1051//1051: 结婚证
    }
}
