package com.icostel.facerecognition.ui.utils

import android.app.Activity
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : View> Activity.bind(@IdRes idRes: Int): T {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }.value
}

fun <T : View> View.bind(@IdRes idRes: Int): T {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }.value
}

fun Activity.setImmersive() {
    this.window?.decorView?.systemUiVisibility = IMMERSIVE_MODE_ON_FLAGS
}

@MainThread
fun <T> LiveData<T>.observe(owner: LifecycleOwner, block: (t: T?) -> Unit) {
    observe(owner, Observer<T> { t -> block(t) })
}

inline fun <E> SparseArray<E>.forEach(block: (position: Int, E) -> Unit) {
    val size = this.size()
    for (i in 0 until size) {
        if (size != this.size()) throw ConcurrentModificationException()
        block(i, this.valueAt(i))
    }
}

inline fun Boolean.ifElse(actionIf: () -> Unit, actionElse: () -> Unit) {
    if (this) {
        actionIf.invoke()
    } else {
        actionElse.invoke()
    }
}