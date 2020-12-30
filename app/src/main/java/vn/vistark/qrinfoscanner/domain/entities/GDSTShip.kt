package vn.vistark.qrinfoscanner.domain.entities

data class GDSTShip(
    val created_at: String,
    val fishing_authorization: String,
    val gps_availability_id: Int,
    val harvest_certification: String,
    val human_welfare_policy: Int,
    val id: Int,
    val is_transshipment: Int,
    val satellite_tracking: String,
    val status: Int,
    val updated_at: String,
    val vessel_flag_id: Int,
    val vessel_id: String,
    val vessel_name: String,
    val welfare_policy_standards: Int
)