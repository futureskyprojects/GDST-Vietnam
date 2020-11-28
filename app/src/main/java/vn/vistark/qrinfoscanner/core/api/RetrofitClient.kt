package vn.vistark.qrinfoscanner.core.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        const val BASE_URL = "https://host.vistark.me/sharing-data/1/"

        val client: Retrofit
            get() {
                return Retrofit.Builder().apply {
                    baseUrl(BASE_URL)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
            }

    }
}