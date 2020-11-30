package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.Entity
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import java.util.*

data class RawMaterialBatch(
    override var Id: Int = -1,
    var ShipmentId: Int = -1,
    var Name: String = "",
    var EnterpriseId: Int = RuntimeStorage.CurrentEnterprise?.Id ?: -1,
    override var Creationtime: Date = Date(),
    override var ModificationTime: Date = Date()
) : Entity()