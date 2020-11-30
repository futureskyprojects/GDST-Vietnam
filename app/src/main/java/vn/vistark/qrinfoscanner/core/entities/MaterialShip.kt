package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import java.util.*

data class MaterialShip(
    override var Id: Int = -1,
    var RawMaterialBatchId: Int = -1,
    var VesselDataId: Int = -1,
    var CertificationAndLicenseId: Int = -1,
    var CatchArea: String = "",
    var FIP: String = "",
    var TripDate: Date = Date(),
    var GearType: String = "",
    var ProductMethod: String = "",
    var LandingLocation: String = "",
    var DatesOfLanding: Date = Date()
) : BaseEntity()