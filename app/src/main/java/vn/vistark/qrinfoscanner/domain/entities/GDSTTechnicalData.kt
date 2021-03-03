package vn.vistark.qrinfoscanner.domain.entities

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataDTO
import java.lang.Exception

class GDSTTechnicalData(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("date_transshipment")
    var dateTransshipment: String? = "",
    @SerializedName("event_date")
    var eventDate: String = "",
    @SerializedName("event_id")
    var eventId: Int = 0,
    @SerializedName("geolocation_id")
    var geolocationId: Int = 0,
    @SerializedName("is_trasshipment")
    var isTrasshipment: Int = 0,
    @SerializedName("KDE")
    var KDE: String? = "",
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
        get() = try {
            if (informationFishingUp.isNullOrEmpty())
                ArrayList()
            else
                ArrayList(
                    Gson().fromJson(
                        informationFishingUp ?: "",
                        Array<GDSTInfomationFishUp>::class.java
                    ).toList()
                )
        } catch (e: Exception) {
            ArrayList()
        }

    companion object {
        fun From(dto: GDSTTechnicalDataDTO): GDSTTechnicalData {
            val obj = GDSTTechnicalData()
            obj.dateTransshipment = dto.dateTransshipment
            obj.eventDate = dto.eventDate
            obj.eventId = dto.eventId
            obj.geolocationId = dto.geolocationId
            obj.isTrasshipment = dto.isTrasshipment
            obj.KDE = dto.KDE
            obj.loactionTransshipmentId = dto.loactionTransshipmentId
            obj.materialShipId = dto.materialShipId
            obj.productFormId = dto.productFormId
            obj.informationFishingUp = dto.informationFishingUp
            obj.eventQuantification = dto.eventQuantification
            return obj
        }
    }
}