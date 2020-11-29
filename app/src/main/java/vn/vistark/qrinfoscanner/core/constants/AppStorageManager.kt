package vn.vistark.qrinfoscanner.core.constants

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AppStorageManager {
    companion object {
        var storageSP: SharedPreferences? = null
        fun initialize(context: Context) {
            storageSP = context.getSharedPreferences("Storage", Context.MODE_PRIVATE)
        }

        fun <T> update(key: String, data: T): Boolean {
            val sJson = Gson().toJson(data)
            println("Đã lưu dữ liệu: $sJson")
            return storageSP?.edit()?.putString(key, sJson)?.commit() ?: false
        }

        inline fun <reified T> getObject(key: String): T {
            val sJson = storageSP?.getString(key, "")
            println("Đã lấy dữ liệu: $sJson (${T::class.java.simpleName})")
            return Gson().fromJson(sJson, T::class.java)
        }

    }
}