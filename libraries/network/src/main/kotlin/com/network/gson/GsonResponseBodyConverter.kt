package com.network.gson

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * Created by bingj on 2017/6/19.
 */
class GsonResponseBodyConverter<T>(gson: Gson, adapter: TypeAdapter<T>): Converter<ResponseBody, T> {
    private val gson: Gson? = gson
    private val adapter: TypeAdapter<T>? = adapter
    companion object{
        private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
    override fun convert(value: ResponseBody?): T? {
//        val jsonReader: JsonReader = gson!!.newJsonReader(value!!.charStream())
        val jsonReader = JsonReader(InputStreamReader(value!!.byteStream(), "UTF-8"))
        try {
            return adapter!!.read(jsonReader)
        } catch (e: JsonParseException) {
            return null
        } finally {
            value.close()
            jsonReader.close()
        }
    }

}