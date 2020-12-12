package vn.vistark.qrinfoscanner.domain.DTOs

import com.google.gson.annotations.SerializedName

class GDSTUserLoginDTO(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("password")
    var password: String = "123456"
)