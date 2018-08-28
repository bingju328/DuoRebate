package com.spcenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import java.io.*

/**
 * Created by bingju on 2017/6/27.
 */
class PreferenceFile(private var mName: String, private var mMode: Int) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmField var mContext: Context? = null
        @JvmField var init = { context: Context ->
            mContext = context
            }
        @JvmField var isInit = { mContext != null }
    }

    fun open(): SharedPreferences {
        if (mContext == null) {
            throw IllegalArgumentException(
                    "please init() before open()")
        }
        return mContext!!.getSharedPreferences(mName,mMode)
    }
    @SuppressLint("ObsoleteSdkInt")
    fun commit(editor: SharedPreferences.Editor): Boolean {
        return if (Build.VERSION.SDK_INT < 9) {
            editor.commit()
        } else {
            editor.apply()
            return true
        }
    }
    fun value(key: String,defaultValue: Boolean): SharedPreference<Boolean>{
        return object :SharedPreference<Boolean>(this,key,defaultValue){
            override fun read(sp: SharedPreferences): Boolean {
                return if (sp.contains(mKey)) sp.getBoolean(mKey,false) else mDefaultValue
            }

            override fun write(editor: SharedPreferences.Editor, value: Boolean) {
                editor.putBoolean(mKey,value)
            }

        }
    }
    fun value(key: String,defaultValue: Int): SharedPreference<Int>{
        return object :SharedPreference<Int>(this,key,defaultValue){
            override fun read(sp: SharedPreferences): Int {
                return if (sp.contains(mKey)) sp.getInt(mKey,-1) else mDefaultValue
            }

            override fun write(editor: SharedPreferences.Editor, value: Int) {
                editor.putInt(mKey,value)
            }

        }
    }
    fun value(key: String,defaultValue: Long): SharedPreference<Long>{
        return object :SharedPreference<Long>(this,key,defaultValue){
            override fun read(sp: SharedPreferences): Long {
                return if (sp.contains(mKey)) sp.getLong(mKey,-1) else mDefaultValue
            }

            override fun write(editor: SharedPreferences.Editor, value: Long) {
                editor.putLong(mKey,value)
            }

        }
    }
    fun value(key: String,defaultValue: String): SharedPreference<String>{
        return object :SharedPreference<String>(this,key,defaultValue){
            override fun read(sp: SharedPreferences): String {
                return if (sp.contains(mKey)) sp.getString(mKey,"") else mDefaultValue
            }

            override fun write(editor: SharedPreferences.Editor, value: String) {
                editor.putString(mKey,value)
            }

        }
    }
    /**
     * 将对象进行base64编码在SharePref中存取
     *
     */
    fun value(key: String, defaultValue: Any): SharedPreference<Any> {
        return object :SharedPreference<Any>(this,key,defaultValue){
            override fun read(sp: SharedPreferences): Any? {
                if (sp.contains(mKey)) {
                    val objectVal = sp.getString(mKey,null)
                    if (TextUtils.isEmpty(objectVal))
                        return  null
                    val buffer = Base64.decode(objectVal,Base64.DEFAULT)
                    val bais = ByteArrayInputStream(buffer)
                    var ois: ObjectInputStream? = null
                    val obj: Any?
                    try {
                        ois = ObjectInputStream(bais)
                        obj = ois.readObject()
                        return obj
                    } catch (e: StreamCorruptedException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        try {
                                bais.close()
                                ois?.close()
                        } catch (e:IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                return null
            }

            override fun write(editor: SharedPreferences.Editor, value: Any) {
                val baos = ByteArrayOutputStream()
                var out: ObjectOutputStream? = null
                try {
                    out = ObjectOutputStream(baos)
                    out.writeObject(value)
                    val objectVal = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
                    editor.putString(mKey,objectVal)
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    try {
                            baos.close()
                            out?.close()
                    } catch (e:IOException) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }
}