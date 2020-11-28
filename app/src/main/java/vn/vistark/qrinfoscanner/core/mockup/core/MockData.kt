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

        fun <T> getObjects(key: String): ArrayList<T> {
            val sJson = mockDataSP?.getString(key, "")
            val type: Type = object : TypeToken<ArrayList<T>>() {}.type
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                println("Đã lấy dữ liệu: $sJson (${type.typeName})")
            }
            return Gson().fromJson(sJson, type)
        }

//        fun <T> getObjects(key: String): MockupcCollection<T> {
//            val sJson = mockDataSP?.getString(key, "")
//            val type: Type = object : TypeToken<MockupcCollection<T>>() {}.type
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                println("Đã lấy tập dữ liệu: $sJson (${type.typeName})")
//            }
//            return Gson().fromJson(sJson, type)
//        }
    }
}