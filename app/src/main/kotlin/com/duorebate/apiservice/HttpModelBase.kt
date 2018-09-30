package com.loanapp.apiservice

import com.duorebate.utils.MoccaPreferences
import com.network.retrofit.ServiceGenerator
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by bingj on 2017/7/1.
 */
open class HttpModelBase{
    companion object {
        val apiSerivce: ApiSerivce by lazy(LazyThreadSafetyMode.NONE) {
            ServiceGenerator.createService(ApiSerivce::class.java, MoccaPreferences.token.get(),"")
        }
        val apiSerivceUserCode: ApiSerivce by lazy(LazyThreadSafetyMode.NONE) {
            ServiceGenerator.createService(ApiSerivce::class.java,"",MoccaPreferences.deviceId.get())
        }
        val apiSerivceUserCodeTest: ApiSerivce by lazy(LazyThreadSafetyMode.NONE) {
//            ServiceGenerator.base_url = "http://192.168.0.80:8080"
            ServiceGenerator.createService(ApiSerivce::class.java,"1c8bef72aaafca7212d081e13993cc83",MoccaPreferences.deviceId.get())
        }
        //无token
        val apiServiceNoToken: ApiSerivce by lazy(LazyThreadSafetyMode.NONE) {
            ServiceGenerator.createService(ApiSerivce::class.java)
        }
    }
    constructor() {
        ServiceGenerator.token = MoccaPreferences.token.get() ?:""
    }

    protected fun getMap(maps: Map<String,String>?): Map<String,String> {
        return if (maps == null) {HashMap<String,String>()} else {maps}
    }
    protected fun <T>setModel (observable: Observable<T>,subscriber: Subscriber<T> ) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }

}