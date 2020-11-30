package vn.vistark.qrinfoscanner.core.models.port.response


import com.google.gson.annotations.SerializedName

data class SeaPort(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("province")
    var province: String = "",
    @SerializedName("country")
    var country: String = ""
)