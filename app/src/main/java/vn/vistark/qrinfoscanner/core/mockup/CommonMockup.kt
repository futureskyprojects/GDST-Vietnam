package vn.vistark.qrinfoscanner.core.mockup

import com.google.gson.Gson
import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.core.mockup.core.MockData

class CommonMockup {
    companion object {
        inline fun <reified T : BaseEntity> MockupData(): Array<T> {
            return MockData.getObject(T::class.java.simpleName) ?: emptyArray()
        }

        inline fun <reified T : BaseEntity> MockupMaxId(): Int {
            return MockupData<T>().maxBy { x -> x.Id }?.Id ?: 0
        }

        inline fun <reified T : BaseEntity> MockupGet(id: Int): T? {
            return MockupData<T>().first { x -> x.Id == id }
        }

        inline fun <reified T : BaseEntity> MockupCreate(
            obj: T,
            check: ((T) -> Boolean)
        ): Boolean {
            return if (MockupData<T>().any { x -> check.invoke(x) })
                false
            else {
                obj.Id = MockupMaxId<T>() + 1
                println("Nhận được dữ liệu ${T::class.java.simpleName}: ${Gson().toJson(obj)}")
                val temp = MockupData<T>().plus(obj)
                println("Tập dữ liệu mới: ${Gson().toJson(temp)}")
                MockData.update(T::class.java.simpleName, temp)
            }
        }

        inline fun <reified T : BaseEntity> MockupUpdate(
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

        inline fun <reified T : BaseEntity> MockupDelete(obj: T): Boolean {
            if (!MockupData<T>().any { x -> x.Id == obj.Id }) {
                return true
            }

            val temp = MockupData<T>().filterNot { x -> x.Id == obj.Id }
            return MockData.update(T::class.java.simpleName, temp)
        }
    }
}