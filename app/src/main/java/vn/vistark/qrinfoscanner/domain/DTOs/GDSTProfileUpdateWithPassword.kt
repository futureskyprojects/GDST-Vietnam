package vn.vistark.qrinfoscanner.domain.DTOs

import com.google.gson.annotations.SerializedName

class GDSTProfileUpdateWithPassword(
    @SerializedName("password")
    var password: String = "",
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("image")
    var image: String = ""
)