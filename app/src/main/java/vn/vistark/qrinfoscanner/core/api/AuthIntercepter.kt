package vn.vistark.qrinfoscanner.core.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AuthIntercepter {
    companion object {
        fun Retrofit.Builder.addToken(token: String, name: String = "Authorization") {
            client(OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader(
                        name,
                        "Bearer $token"
                    ).build()
                    chain.proceed(request)
                }
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
            }.build())
        }
    }
}