package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.models.fish_data.response.FishData
import vn.vistark.qrinfoscanner.core.models.fish_data.response.FishDatas

class TraceableObjectInformation(
    override var Id: Int = -1,
    var technicalDataId: Int = -1,
    var fishData: FishData = RuntimeStorage.FishDatas?.firstOrNull() ?: FishData(),
    var productForm: String = "",
    var linkingKDE: String = "",
    var weightOrQuantity: Double = -0.0,
    var unitOfMeasure: String = ""
) : BaseEntity() {
}