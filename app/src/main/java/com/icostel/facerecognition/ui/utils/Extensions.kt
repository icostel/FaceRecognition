package com.icostel.facerecognition.ui.utils

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

fun <T : View> Activity.bind(@IdRes idRes: Int): T {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }.value
}

fun Activity.enableImmersive() {
    this.window?.decorView?.systemUiVisibility = IMMERSIVE_MODE_ON_FLAGS
}

@MainThread
fun <T> LiveData<T>.observe(owner: LifecycleOwner, block: (t: T?) -> Unit) {
    observe(owner, Observer<T> { t -> block(t) })
}

@MainThread
internal inline fun <reified T : ViewModel?> AppCompatActivity.getViewModel(
    factory: ViewModelProvider.Factory,
    viewModelClass: Class<T>
): T {
    return ViewModelProviders.of(this, factory).get(T::class.java)
}