package vn.vistark.qrinfoscanner.domain.DTOs


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData
import java.lang.Exception

data class GDSTTechnicalDataUpdateDTO(
    @SerializedName("id")
    var id: Int = 0,
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
        fun mapFrom(obj: GDSTTechnicalData): GDSTTechnicalDataUpdateDTO {
            val temp = GDSTTechnicalDataUpdateDTO(
                obj.id,
                obj.dateTransshipment ?: "",
                obj.eventDate,
                obj.eventId,
                obj.geolocationId,
                obj.isTrasshipment,
                obj.KDE,
                obj.loactionTransshipmentId,
                obj.materialShipId,
                obj.productFormId,
                obj.informationFishingUp,
                obj.eventQuantification
            )
            try {
                temp.dateTransshipment = temp.dateTransshipment.split(" ").first()
                temp.eventDate = temp.eventDate.split(" ").first()
            } catch (e: Exception) {

            }
            return temp
        }
    }
}