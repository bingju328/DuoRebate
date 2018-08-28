package com.network.retrofit

import com.network.gson.GsonConverterFactory
import com.network.utils.getUnsafeOkHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

/**
 * Created by bingju on 2017/6/19.
 */
class ServiceGenerator {

    companion object {
        /**
         * 本地环境
         */
        private const val BASE_URL: String = "https://47.92.52.10:8001"
//        private const val BASE_URL: String = "http://192.168.1.102:8080"
        var token: String = ""
            set(value) {
                field = value
            }
        var deviceId: String = ""
            set(value) {
                field = value
            }
        var base_url : String = BASE_URL
            set(value){
                if (value == "" || value == null) {
                    field = BASE_URL
                }
            }
        var httpClient: OkHttpClient.Builder = getUnsafeOkHttpClient()
        var converterFactory: Converter.Factory? = null
            set(value) {
                field = value
            }
        var callFactory: CallAdapter.Factory? = null
            set(value) {
                field = value
            }
        var interceptor: Interceptor? = null
            set(value) {
                field = value
            }

        val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(base_url)


        fun<S> createService(serviceClass: Class<S>): S{
            return createService(serviceClass,"","")
        }
        fun<S> createService(serviceClass: Class<S>,authToken: String?,deviceId: String?): S {

//                Log.i("authToken--", authToken?)
                httpClient.addNetworkInterceptor {
                    val original: Request = it.request()
                    //获取header并添加token
                    val requestBuilder: Request.Builder = original.newBuilder()
                    requestBuilder.header("Accept", "application/json")
//                    requestBuilder.header("version",version?:"")
//                    requestBuilder.header("charset","utf8")
//                    requestBuilder.header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
//                    requestBuilder.header("Accept-Encoding", "")
                    if (authToken?.length!! >= 1) {
                        requestBuilder.header("msg", token)
//                        requestBuilder.header("token", "1c8bef72aaafca7212d081e13993cc83")
                    }
                    if (deviceId?.length!! >= 1) {
                        requestBuilder.header("deviceId", deviceId)
                    }
                    requestBuilder.method(original.method(),original.body())
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
            /**打印OKHTTP的运行日志*/
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(httpLoggingInterceptor)
            builder.addConverterFactory(converterFactory?:GsonConverterFactory.create())
            builder.addCallAdapterFactory(callFactory?: RxJavaCallAdapterFactory.create())
            val retrofit: Retrofit = builder.client(httpClient.build()).build()
            return retrofit.create(serviceClass)
        }
    }

}