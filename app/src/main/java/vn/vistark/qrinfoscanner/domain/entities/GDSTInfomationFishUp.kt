package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTInfomationFishUp(
    @SerializedName("quantification")
    var quantification: Int = 0,
    @SerializedName("spice_id")
    var spiceId: Int = 0,
    @SerializedName("unit")
    var unit: String = ""
)