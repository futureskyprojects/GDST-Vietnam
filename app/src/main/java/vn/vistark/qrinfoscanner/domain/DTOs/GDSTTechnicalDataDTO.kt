package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.annotations.SerializedName

data class GDSTTechnicalDataDTO(
    @SerializedName("date_transshipment")
    var dateTransshipment: String = "",
    @SerializedName("event_date")
    var eventDate: String = "",
    @SerializedName("event_id")
    var eventId: Int = 0,
    @SerializedName("geolocation_id")
    var geolocationId: Int = 0,
    @SerializedName("is_trasshipment")
    var isTrasshipment: Int = 0,
    @SerializedName("KDE")
    var KDE: String = "",
    @SerializedName("loaction_transshipment_id")
    var loactionTransshipmentId: Int = 0,
    @SerializedName("material_ship_id")
    var materialShipId: Int = 0,
    @SerializedName("product_form_id")
    var productFormId: Int = 0
)