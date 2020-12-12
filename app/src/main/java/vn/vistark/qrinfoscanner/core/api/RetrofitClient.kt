package vn.vistark.qrinfoscanner.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.vistark.qrinfoscanner.core.api.AuthIntercepter.Companion.addToken
import vn.vistark.qrinfoscanner.domain.api.IApiService.Companion.BASE_URL

class RetrofitClient {
    companion object {
        val client: Retrofit
            get() {
                return Retrofit.Builder().apply {
                    baseUrl(BASE_URL)
                    addToken()
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
            }

    }
}