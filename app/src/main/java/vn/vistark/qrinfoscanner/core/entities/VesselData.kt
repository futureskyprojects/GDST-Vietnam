package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity

class VesselData(
    override var Id: Int,
    var vesselName: String,
    var vesselOwner: String,
    var vesselRegistration: String,
    var uniqueVesselIdentification: String,
    var publicVesselRegistryHyperlink: String,
    var vesselFlag: String,
    var availabilityOfCatchCoordinates: String,
    var satelliteVesselTrackingAuthority: String,
    var transshipmentVesselName: String,
    var transshipmentVesselUniqueVesselID: Int,
    var transshipmentVesselRegistration: String,
    var transshipmentVesselFlag: String
) : BaseEntity()