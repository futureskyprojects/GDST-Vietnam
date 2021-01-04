package vn.vistark.qrinfoscanner.core.interfaces

interface IDeletable<T> {
    var onEdit: ((T) -> Unit)?
}