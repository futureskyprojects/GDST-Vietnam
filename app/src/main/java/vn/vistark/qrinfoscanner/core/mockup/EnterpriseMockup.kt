package vn.vistark.qrinfoscanner.core.mockup

import android.os.Build
import com.google.gson.Gson
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage.Companion.CurrentEnterprise
import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.core.mockup.core.MockData
import vn.vistark.qrinfoscanner.core.mockup.core.MockupcCollection

class EnterpriseMockup {
    companion object {
        private val key = "Enterprise";
        private val enterprises: Array<Enterprise>
            get() = MockData.getObject(key) ?: emptyArray()

        fun create(enterprise: Enterprise): String {
            if (enterprises.any { x -> x.identity == enterprise.identity })
                return "Doanh nghiệp đã tồn tại"
            else {
                println("Nhận được dữ liệu doanh nghiệp: ${Gson().toJson(enterprise)}")
                val temp = enterprises.plus(enterprise)
                println("Tập dữ liệu mới: ${Gson().toJson(temp)}")
                MockData.update(key, temp)
                return ""
            }
        }

        fun login(identity: String, password: String): String {
            if (!enterprises.any { x -> x.identity == identity && x.hashPassword == password }) {
                return "Tài khoản hoặc mật khẩu không đúng"
            } else {
                CurrentEnterprise = enterprises.first { x -> x.identity == identity }
                return ""
            }
        }

        fun update(enterprise: Enterprise): String {
            if (!enterprises.any { x -> x.identity == enterprise.identity }) {
                return create(enterprise)
            } else {
                val temp = enterprises
                val index = temp.indexOfFirst { x -> x.Id == enterprise.Id }
                temp[index] = enterprise
                MockData.update(key, temp)
                return ""
            }
        }

        fun delete(enterprise: Enterprise): String {
            if (!enterprises.any { x -> x.identity == enterprise.identity }) {
                return ""
            } else {
                val temp = enterprises.filterNot { x -> x.Id == enterprise.Id }
                MockData.update(key, temp)
                return ""
            }
        }
    }
}