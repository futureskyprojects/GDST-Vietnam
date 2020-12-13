package vn.vistark.qrinfoscanner.domain.api.responses.shipment


import com.google.gson.annotations.SerializedName

data class ShipmentCreateSuccessfulResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("id-shipment")
    var idShipment: Int = 0
)