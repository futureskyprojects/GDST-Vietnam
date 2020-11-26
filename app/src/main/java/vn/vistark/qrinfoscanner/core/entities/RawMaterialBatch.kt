package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.Entity
import java.util.*

class RawMaterialBatch(
    override var Id: Int,
    var ShipmentId: Int,
    var Name: String,
    override var Creationtime: Date,
    override var ModificationTime: Date
) : Entity()