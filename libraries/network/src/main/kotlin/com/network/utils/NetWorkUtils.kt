package com.network.utils

import okhttp3.OkHttpClient
import java.lang.RuntimeException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by bingju on 2017/6/19.
 */

val getUnsafeOkHttpClient =fun(): OkHttpClient.Builder{
    try {
        //Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(object: X509TrustManager{
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
        })
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory)

        builder.hostnameVerifier(object: HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }
        })
        return builder
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
