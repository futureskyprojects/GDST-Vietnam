package vn.vistark.qrinfoscanner.domain.api.requests.material_batch

import com.google.gson.annotations.SerializedName

class GetMaterialBatchBody (
    @SerializedName("shipment_id")
    var shipmentId: Int = 0
)