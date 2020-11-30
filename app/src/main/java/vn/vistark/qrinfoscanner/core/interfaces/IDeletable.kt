package vn.vistark.qrinfoscanner.core.interfaces

import vn.vistark.qrinfoscanner.core.entities.Shipment

interface IDeletable<T> {
    var onDelete: ((T) -> Unit)?
}