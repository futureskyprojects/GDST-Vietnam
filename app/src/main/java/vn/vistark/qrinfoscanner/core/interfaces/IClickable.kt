package vn.vistark.qrinfoscanner.core.interfaces

import vn.vistark.qrinfoscanner.core.entities.Shipment

interface IClickable<T> {
    var onClick: ((T) -> Unit)?
}