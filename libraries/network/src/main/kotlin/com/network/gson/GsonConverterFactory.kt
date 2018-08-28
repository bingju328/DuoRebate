package com.network.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by bingj on 2017/6/19.
 */
class GsonConverterFactory(gson: Gson): Converter.Factory(){
    var gson: Gson? = null
    init {
        this.gson = gson
    }
    companion object {
        fun create(): GsonConverterFactory {
            return create(Gson())
        }
        private fun create(gson: Gson): GsonConverterFactory {
            return GsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        val adapter = gson!!.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(gson!!, adapter)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        val adapter = gson!!.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson!!, adapter)
    }

}