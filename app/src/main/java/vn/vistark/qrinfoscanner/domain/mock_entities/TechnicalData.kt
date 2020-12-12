package vn.vistark.qrinfoscanner.domain.mock_entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import java.util.*

class TechnicalData(
    override var Id: Int = -1,
    var materialShipId: Int = -1,
    var eventId: Int = -1,
    var eventDate: Date = Date(),
    var timeZone: String = TimeZone.getDefault().id,
    var eventReadPoint: String = "",
    var productOwnerShip: String = "",
    var informationProvider: String = ""
) : BaseEntity()