package vn.vistark.qrinfoscanner.core.mockup

import com.google.gson.Gson
import vn.vistark.qrinfoscanner.core.mockup.core.MockData

class CommonMockup {
    companion object {
        inline fun <reified T> MockupData(): Array<T> {
            return MockData.getObject(T::class.java.simpleName) ?: emptyArray()
        }

        inline fun <reified T> MockupCreate(
            obj: T,
            check: ((T) -> Boolean)
        ): Boolean {
            if (MockupData<T>().any { x -> check.invoke(x) })
                return false
            else {
                println("Nhận được dữ liệu ${T::class.java.simpleName}: ${Gson().toJson(obj)}")
                val temp = MockupData<T>().plus(obj)
                println("Tập dữ liệu mới: ${Gson().toJson(temp)}")
                return MockData.update(T::class.java.simpleName, temp)
            }
        }

        inline fun <reified T> MockupUpdate(
            obj: T,
            check: ((T) -> Boolean)
        ): Boolean {
            if (!MockupData<T>().any { x -> check(x) }) {
                return MockupCreate(obj, check)
            } else {
                val temp = MockupData<T>()
                val index = temp.indexOfFirst { x -> check(x) }
                temp[index] = obj
                return MockData.update(T::class.java.simpleName, temp)
            }
        }

        inline fun <reified T> MockupDelete(
            check: ((T) -> Boolean)
        ): Boolean {
            if (!MockupData<T>().any { x -> check(x) }) {
                return true
            }

            val temp = MockupData<T>().filterNot { x -> check(x) }
            return MockData.update(T::class.java.simpleName, temp)
        }
    }
}