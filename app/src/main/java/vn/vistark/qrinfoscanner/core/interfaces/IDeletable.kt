package vn.vistark.qrinfoscanner.core.interfaces

interface IDeletable<T> {
    var onDelete: ((T) -> Unit)?
}