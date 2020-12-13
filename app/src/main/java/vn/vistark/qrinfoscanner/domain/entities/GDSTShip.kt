package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTShip(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("vesselName")
    var vesselName: String = "",
    @SerializedName("vesselID")
    var vesselID: String = "",
    @SerializedName("vesselPublicRegistry")
    var vesselPublicRegistry: String = "",
    @SerializedName("vessel_flag")
    var vesselFlag: Int = 0,
    @SerializedName("satellite_tracking")
    var satelliteTracking: String = "",
    @SerializedName("fishing_authorization")
    var fishingAuthorization: String = "",
    @SerializedName("harvest_certification")
    var harvestCertification: String = "",
    @SerializedName("is_transshipment")
    var isTransshipment: Int = 0,
    @SerializedName("transshipment_authorization")
    var transshipmentAuthorization: String = "",
    @SerializedName("human_welfare_policy")
    var humanWelfarePolicy: Int = 0,
    @SerializedName("welfare_policy_standards")
    var welfarePolicyStandards: Int = 0,
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
)