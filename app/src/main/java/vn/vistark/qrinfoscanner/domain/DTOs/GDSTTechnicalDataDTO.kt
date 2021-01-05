package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData

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
    var productFormId: Int = 0,
    // Danh sách các loài đi kèm khối lượng
    @SerializedName("information_fishing_up")
    var informationFishingUp: String? = "",
    // Tổng sản lượng từ các loài
    @SerializedName("event_quantification")
    var eventQuantification: Float = 0F
) {
    val informationFishingUpObject: ArrayList<GDSTInfomationFishUp>
        get() = ArrayList(
            Gson().fromJson(
                informationFishingUp,
                Array<GDSTInfomationFishUp>::class.java
            ).toList()
        )

    companion object {
        fun From(obj: GDSTTechnicalData): GDSTTechnicalDataDTO {
            val dto = GDSTTechnicalDataDTO()
            dto.dateTransshipment = obj.dateTransshipment
            dto.eventDate = obj.eventDate
            dto.eventId = obj.eventId
            dto.geolocationId = obj.geolocationId
            dto.isTrasshipment = obj.isTrasshipment
            dto.KDE = obj.KDE
            dto.loactionTransshipmentId = obj.loactionTransshipmentId
            dto.materialShipId = obj.materialShipId
            dto.productFormId = obj.productFormId
            dto.informationFishingUp = obj.informationFishingUp
            dto.eventQuantification = obj.eventQuantification
            return dto
        }
    }

}