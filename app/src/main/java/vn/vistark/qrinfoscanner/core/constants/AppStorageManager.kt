package vn.vistark.qrinfoscanner.core.constants

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vn.vistark.qrinfoscanner.core.mockup.core.MockupcCollection
import java.lang.reflect.Type

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

        fun <T> getObject(key: String): T {
            val sJson = storageSP?.getString(key, "")
            val type: Type = object : TypeToken<T>() {}.type
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                println("Đã lấy dữ liệu: $sJson (${type.typeName})")
            }
            return Gson().fromJson(sJson, type)
        }
    }
}