package com.network.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset

/**
 * Created by bingj on 2017/6/19.
 */
class GsonRequestBodyConverter<T>(gson: Gson,adapter: TypeAdapter<T>): Converter<T, RequestBody> {
    private val gson: Gson? = gson
    private val adapter: TypeAdapter<T>? = adapter
    companion object{
        private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson!!.newJsonWriter(writer)
        adapter!!.write(jsonWriter,value)
        jsonWriter.close()
    return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

}