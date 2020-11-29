package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity

data class CertificationAndLicense(
    override var Id: Int = -1,
    var fishingAuthorization: String = "",
    var harvestCertification: String = "",
    var harvestCertificationChainOfCustody: String = "",
    var transshipmentAuthorization: String = "",
    var landingAuthorization: String = "",
    var existenceOfHumanWelfarePolicy: Boolean = false,
    var humanWelfarePolicyStandards: Boolean = false
) : BaseEntity()