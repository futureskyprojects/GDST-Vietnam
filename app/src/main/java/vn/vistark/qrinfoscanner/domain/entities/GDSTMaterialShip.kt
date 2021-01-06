package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.core.extensions.StringExtension.Companion.ToYMDDate
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTMaterialShipCreateDTO
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTMaterialShipUpdateDTO

data class GDSTMaterialShip(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("material_id")
    var materialId: Int = 0,
    @SerializedName("fipcode_id")
    var fipcodeId: Int = 0,
    @SerializedName("gear_id")
    var gearId: Int = 0,
    @SerializedName("date_go")
    var dateGo: String = "",
    @SerializedName("prodct_method")
    var prodctMethod: Int = 0,
    @SerializedName("up_fishing")
    var upFishing: Int = 0,
    @SerializedName("date_up_fishing")
    var dateUpFishing: String = "",
    @SerializedName("ship_id")
    var shipId: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
) {
    constructor(
        id: Int,
        dto: GDSTMaterialShipCreateDTO,
        createdAt: String,
        updatedAt: String
    ) : this(
        id,
        dto.materialId,
        dto.fipcodeId,
        dto.gearId,
        dto.dateGo,
        dto.prodctMethod,
        dto.upFishing,
        dto.dateUpFishing,
        dto.shipId,
        createdAt,
        updatedAt
    )

    fun DTOofCreate(): GDSTMaterialShipCreateDTO {
        return GDSTMaterialShipCreateDTO(
            materialId,
            fipcodeId,
            gearId,
            dateGo,
            prodctMethod,
            upFishing,
            dateUpFishing,
            shipId
        )
    }

    fun DTOofUpdate(): GDSTMaterialShipUpdateDTO {
        return GDSTMaterialShipUpdateDTO(
            id,
            materialId,
            fipcodeId,
            gearId,
            dateGo.ToYMDDate()?.Format() ?: dateGo.split(" ").first(),
            prodctMethod,
            upFishing,
            dateUpFishing.ToYMDDate()?.Format() ?: dateUpFishing.split(" ").first(),
            shipId
        )
    }
}