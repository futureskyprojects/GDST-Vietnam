package vn.vistark.qrinfoscanner.domain.entities

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class GDSTTechnicalData(
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

}