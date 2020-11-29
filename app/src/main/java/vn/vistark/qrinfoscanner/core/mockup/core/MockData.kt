package vn.vistark.qrinfoscanner.core.mockup.core

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MockData {
    companion object {
        var mockDataSP: SharedPreferences? = null
        fun initialize(context: Context) {
            mockDataSP = context.getSharedPreferences("MockData", Context.MODE_PRIVATE)
        }

        fun <T> update(key: String, data: T): Boolean {
            val sJson = Gson().toJson(data)
            println("Đã lưu dữ liệu: $sJson")
            return mockDataSP?.edit()?.putString(key, sJson)?.commit() ?: false
        }

        inline fun <reified T> getObject(key: String): T {
            val sJson = mockDataSP?.getString(key, "")
            println("Đã lấy dữ liệu: $sJson (${T::class.java.simpleName})")
            return Gson().fromJson(sJson, T::class.java)
        }

    }
}