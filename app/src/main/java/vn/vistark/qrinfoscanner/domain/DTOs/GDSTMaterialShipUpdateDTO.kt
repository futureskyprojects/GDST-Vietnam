package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.annotations.SerializedName

data class GDSTMaterialShipUpdateDTO(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("material_id")
    var materialId: Int = 0,
    @SerializedName("fipcode_id")
    var fipcodeId: Int = 0,
    @SerializedName("gear_id")
    var gearId: Int = 0,
    @SerializedName("date_go")
    var dateGo: String = "",
    @SerializedName("prodct_method")
    var prodctMethod: Int = 0,
    @SerializedName("up_fishing")
    var upFishing: Int = 0,
    @SerializedName("date_up_fishing")
    var dateUpFishing: String = "",
    @SerializedName("ship_id")
    var shipId: Int = 0
)