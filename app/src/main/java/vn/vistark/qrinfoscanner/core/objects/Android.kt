package vn.vistark.qrinfoscanner.core.objects

import vn.vistark.qrinfoscanner.core.helpers.AndroidContinuation
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

object Android : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
        AndroidContinuation(continuation)
}