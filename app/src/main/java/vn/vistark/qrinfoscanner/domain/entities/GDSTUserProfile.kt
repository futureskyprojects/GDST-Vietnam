package vn.vistark.qrinfoscanner.domain.entities

import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.constants.Config

class GDSTUserProfile(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("username")
    var username: String = "",
    @SerializedName("company_id")
    var company_id: Int = 0,
    @SerializedName("password")
    var password: String = Config.defaultPassword,
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("image")
    var image: String = ""
) {
    fun getDisplayName(): String {
        return fullname.trim().split(" ").last()
    }
}