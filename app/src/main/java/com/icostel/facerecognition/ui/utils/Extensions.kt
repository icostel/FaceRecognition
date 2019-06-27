package com.icostel.facerecognition.ui.utils

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.icostel.facerecognition.ui.screens.BaseActivity

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
internal inline fun <reified T : ViewModel?> BaseActivity.provideViewModel(viewModelClass: Class<T>): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}