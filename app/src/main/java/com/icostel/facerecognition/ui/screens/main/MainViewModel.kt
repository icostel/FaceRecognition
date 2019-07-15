package com.icostel.facerecognition.ui.screens.main


import android.graphics.Bitmap
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.icostel.facerecognition.domain.FaceDetectionUseCase
import com.icostel.facerecognition.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
class MainViewModel
@Inject constructor(
    private val faceDetectionUseCase: FaceDetectionUseCase
) : BaseViewModel() {

    internal lateinit var faceEvent: MediatorLiveData<MutableList<FirebaseVisionFace>?>

    init {
        faceEvent.addSource(faceDetectionUseCase.facesResultEvent) {
            faceEvent.value = it
        }
    }

    internal fun detect(sourceBitmap: Bitmap, width: Int, height: Int) {
        disposable.add(faceDetectionUseCase.detect(sourceBitmap, width, height)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.d("$TAG detect() started")
                }, {
                    Timber.d("$TAG detect() failed, err: ${it.localizedMessage}")
                })
        )
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}