package com.icostel.facerecognition.di

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface OnActivityCreateLifecycleCallback : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        handleOnCreate(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    fun handleOnCreate(activity: Activity, savedInstanceState: Bundle)
}
