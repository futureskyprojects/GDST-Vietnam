package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTPort(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("symbol")
    var symbol: String = "",
    @SerializedName("province")
    var province: String = "",
    @SerializedName("country")
    var country: String = "",
    @SerializedName("status")
    var status: Int = 0
)