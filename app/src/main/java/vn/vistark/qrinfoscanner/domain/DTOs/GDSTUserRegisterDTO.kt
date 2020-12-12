package vn.vistark.qrinfoscanner.domain.DTOs

import com.google.gson.annotations.SerializedName

class GDSTUserRegisterDTO(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("company_id")
    var company_id: Int = 0,
    @SerializedName("password")
    var password: String = "123456",
    @SerializedName("fullname")
    var fullname: String = ""
)