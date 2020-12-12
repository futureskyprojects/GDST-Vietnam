package vn.vistark.qrinfoscanner.domain.mock_entities

import vn.vistark.qrinfoscanner.core.abstracts.BaseEntity
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage

data class CertificationAndLicense(
    override var Id: Int = -1,
    var EnterpriseId: Int = -1,
    var fishingAuthorization: String = "",
    var harvestCertification: String = "",
    var harvestCertificationChainOfCustody: String = "",
    var transshipmentAuthorization: String = "",
    var landingAuthorization: String = "",
    var existenceOfHumanWelfarePolicy: Boolean = false,
    var humanWelfarePolicyStandards: Boolean = false
) : BaseEntity()