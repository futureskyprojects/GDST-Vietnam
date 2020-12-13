package vn.vistark.qrinfoscanner.domain.api.responses.material_batch


import com.google.gson.annotations.SerializedName

data class MaterialBatchCreateSuccessfulResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("id-batch")
    var idBatch: Int = 0
)