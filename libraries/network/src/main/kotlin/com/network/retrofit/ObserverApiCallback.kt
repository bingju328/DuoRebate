package com.network.retrofit

import retrofit2.HttpException
import rx.Subscriber

/**
 * Created by bingj on 2017/6/19.
 */
abstract class ObserverApiCallback<M>: Subscriber<M>() {
    protected abstract fun onSuccess(model: M)
    protected abstract fun onFailure(msg: String?)
    protected open fun onFinish() {}
    protected open fun onlogin() {}

    override fun onError(e: Throwable?) {
        e!!.printStackTrace()
        if (e is HttpException) {
            val httpException: HttpException = e
            val code = httpException.code()
            var msg = httpException.message
            when(code) {
                504 -> msg = "网络不给力"
                403 -> onlogin() //用户的token失效重新登录
                in arrayOf(502,404,500) -> {
                    msg = "服务器异常，请稍后再试"
                }
            }
            onFailure(msg)
        } else {
            onFailure(e.message)
        }
        onFinish()
    }

    override fun onNext(t: M) {
        if (t == null) {
            onFailure("解析错误")
        } else {
            onSuccess(t)
        }
    }
    override fun onCompleted() {
        onFinish()
    }

}