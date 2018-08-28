package com.spcenter

import android.content.SharedPreferences

/**
 * Created by bingj on 2017/6/27.
 */
abstract class SharedPreference<T>(file: PreferenceFile, key: String, protected var mDefaultValue: T) {
    private var mFile: PreferenceFile = file
    protected  var mKey: String = key
    fun exists(): Boolean {
        return mFile.open().contains(mKey)
    }

    fun get(): T?{
        return read(mFile.open())
    }
    fun getKey(): String{
        return mKey
    }
    fun put(value: T) {
        val sp = mFile.open()
        val editor = sp.edit()
        write(editor,value)
        mFile.commit(editor)
    }
    fun clear(value: T) {
        val sp = mFile.open()
        val editor = sp.edit()
        editor.clear()
        mFile.commit(editor)
    }
    fun remove(value: T) {
        val sp = mFile.open()
        val editor = sp.edit()
        editor.remove(mKey)
        mFile.commit(editor)
    }
    protected abstract fun read(sp: SharedPreferences): T?
    protected abstract fun write(editor: SharedPreferences.Editor,value: T)
}