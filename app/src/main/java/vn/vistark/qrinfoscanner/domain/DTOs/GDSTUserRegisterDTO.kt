package vn.vistark.qrinfoscanner.domain.DTOs

import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.constants.Config

class GDSTUserRegisterDTO(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("company_id")
    var company_id: Int = 0,
    @SerializedName("password")
    var password: String = Config.defaultPassword,
    @SerializedName("fullname")
    var fullname: String = ""
)