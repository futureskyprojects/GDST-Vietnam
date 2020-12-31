package vn.vistark.qrinfoscanner.domain.api.requests.material_ship

import com.google.gson.annotations.SerializedName

class GetMaterialShipBody (
    @SerializedName("material_id")
    var materialId: Int = 0
)