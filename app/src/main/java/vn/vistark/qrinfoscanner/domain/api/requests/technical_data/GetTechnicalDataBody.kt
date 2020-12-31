package vn.vistark.qrinfoscanner.domain.api.requests.technical_data

import com.google.gson.annotations.SerializedName

class GetTechnicalDataBody(
    @SerializedName("material_ship_id")
    var materialShipId: Int = 0
)