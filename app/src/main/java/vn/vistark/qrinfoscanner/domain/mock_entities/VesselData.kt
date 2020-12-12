package vn.vistark.qrinfoscanner.domain.mock_entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage

data class VesselData(
    override var Id: Int = -1,
    var EnterpriseId: Int = -1,
    var vesselName: String = "",
    var vesselOwner: String = "",
    var vesselRegistration: String = "",
    var uniqueVesselIdentification: String = "",
    var publicVesselRegistryHyperlink: String = "",
    var vesselFlag: String = "",
    var availabilityOfCatchCoordinates: String = "",
    var satelliteVesselTrackingAuthority: String = ""
) : BaseEntity()