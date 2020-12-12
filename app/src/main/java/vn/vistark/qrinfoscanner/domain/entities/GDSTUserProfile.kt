package vn.vistark.qrinfoscanner.domain.entities

import com.google.gson.annotations.SerializedName

class GDSTUserProfile(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("username")
    var username: String = "",
    @SerializedName("company_id")
    var company_id: Int = 0,
    @SerializedName("password")
    var password: String = "",
    @SerializedName("fullname")
    var fullname: String = ""
)