package vn.vistark.qrinfoscanner.domain.api.responses.material_ship


import com.google.gson.annotations.SerializedName

data class CreateMaterialShipSuccessfulResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("id-ship")
    var idShip: Int = 0
)