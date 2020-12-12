package vn.vistark.qrinfoscanner.domain.mock_entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.mock_models.fish_data.response.FishData

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