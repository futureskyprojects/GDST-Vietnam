package vn.vistark.qrinfoscanner.core.extensions

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import vn.vistark.qrinfoscanner.core.interfaces.RequestCallback
import vn.vistark.qrinfoscanner.domain.constants.Config.Companion.showLog
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Retrofit2Extension {
    companion object {
        suspend fun <T> Call<T>.await(): T? {
            showLog(">>>> Thực hiện truy vấn đến: [${this.request().url().uri()}]")
            return suspendCoroutine { continuation ->
                enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>?, t: Throwable) {
                        continuation.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>?, response: Response<T>) {
//                        showLog(Gson().toJson(response))
                        if (response.isSuccessful && response.body() != null) {
                            continuation.resume(response.body())
                        } else {
                            continuation.resumeWithException(HttpException(response))
                        }
                    }
                })
            }
        }
    }
}