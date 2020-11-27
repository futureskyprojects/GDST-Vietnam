package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.Entity
import java.util.*

class Shipment(
    override var Id: Int,
    var Name: String,
    var EnterpriseId: Int,
    override var Creationtime: Date,
    override var ModificationTime: Date
) : Entity()