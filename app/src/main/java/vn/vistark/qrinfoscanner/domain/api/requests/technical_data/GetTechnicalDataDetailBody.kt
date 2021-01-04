package vn.vistark.qrinfoscanner.domain.api.requests.technical_data

import com.google.gson.annotations.SerializedName

class GetTechnicalDataDetailBody(
    @SerializedName("technical_id")
    var technicalId: Int = 0
)