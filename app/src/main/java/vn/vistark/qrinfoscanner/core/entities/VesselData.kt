package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage

data class VesselData(
    override var Id: Int = -1,
    var EnterpriseId: Int = RuntimeStorage.CurrentEnterprise?.Id ?: -1,
    var vesselName: String = "",
    var vesselOwner: String = "",
    var vesselRegistration: String = "",
    var uniqueVesselIdentification: String = "",
    var publicVesselRegistryHyperlink: String = "",
    var vesselFlag: String = "",
    var availabilityOfCatchCoordinates: String = "",
    var satelliteVesselTrackingAuthority: String = ""
) : BaseEntity()