package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.annotations.SerializedName

data class GDSTShipmentCreateDTO(
    @SerializedName("qr_url")
    var qrUrl: String = ""
)