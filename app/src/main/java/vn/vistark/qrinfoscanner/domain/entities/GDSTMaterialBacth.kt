package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTMaterialBacth(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("shipment_id")
    var shipmentId: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
)