package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.annotations.SerializedName

data class GDSTMaterialBacthCreateDTO(
    @SerializedName("shipment_id")
    var shipmentId: Int = 0
)