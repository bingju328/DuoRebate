package com.network.pslistener

/**
 * Created by bingj on 2017/6/19.
 */
interface ProgressListener {
    /**
     * @param progres 下载或者上传的进度
     *
     * @param total   资源的总大小
     *
     * @param done    是否下载或上传完毕
     * */
    fun onProgress(progres: Long,total: Long,done: Boolean)
}