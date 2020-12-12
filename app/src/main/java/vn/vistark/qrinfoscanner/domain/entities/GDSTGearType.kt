package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTGearType(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String = ""
)