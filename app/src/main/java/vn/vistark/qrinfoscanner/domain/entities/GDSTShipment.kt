package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTShipment(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("qr_url")
    var qrUrl: String = "",
    @SerializedName("account_id")
    var accountId: Int = 0,
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
)