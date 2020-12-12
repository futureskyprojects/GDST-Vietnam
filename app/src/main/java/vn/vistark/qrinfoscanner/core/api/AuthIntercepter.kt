package vn.vistark.qrinfoscanner.core.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.domain.mock_entities.Enterprise
import java.util.concurrent.TimeUnit

class AuthIntercepter {
    companion object {
        var CurrentToken: String = ""
            get() = AppStorageManager.get("CURRENT_TOKEN") ?: field
            set(value) {
                AppStorageManager.update("CURRENT_TOKEN", value)
                field = value
            }

        var CurrentTokenType: String = "Bearer"
            get() = AppStorageManager.get("CURRENT_TOKEN_TYPE") ?: field
            set(value) {
                AppStorageManager.update("CURRENT_TOKEN_TYPE", value)
                field = value
            }

        var AuthorizationKey: String = "Authorization"
            get() = AppStorageManager.get("AUTHORIZTION_KEY") ?: field
            set(value) {
                AppStorageManager.update("AUTHORIZTION_KEY", value)
                field = value
            }


        fun Retrofit.Builder.addToken() {
            if (CurrentToken.isEmpty())
                return

            client(OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader(
                        AuthorizationKey,
                        "$CurrentTokenType $CurrentToken"
                    ).build()
                    chain.proceed(request)
                }
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
            }.build())
        }
    }
}