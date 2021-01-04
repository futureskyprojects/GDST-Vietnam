package vn.vistark.qrinfoscanner.domain.DTOs

import com.google.gson.annotations.SerializedName

class GDSTProfileUpdateWithOutPassword(
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("image")
    var image: String = ""
)