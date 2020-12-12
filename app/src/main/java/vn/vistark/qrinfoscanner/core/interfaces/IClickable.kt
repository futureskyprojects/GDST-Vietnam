package vn.vistark.qrinfoscanner.core.interfaces

interface IClickable<T> {
    var onClick: ((T) -> Unit)?
}