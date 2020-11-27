package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity

class CertificationAndLicense(
    override var Id: Int,
    var fishingAuthorization: String,
    var harvestCertification: String,
    var harvestCertificationChainOfCustody: String,
    var transshipmentAuthorization: String,
    var landingAuthorization: String,
    var existenceOfHumanWelfarePolicy: Boolean,
    var humanWelfarePolicyStandards: Boolean
) : BaseEntity()