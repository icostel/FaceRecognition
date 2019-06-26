package com.icostel.facerecognition.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val faceDetector: FirebaseVisionFaceDetector
) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val disposable = CompositeDisposable()
    internal val detectLiveData = MutableLiveData<MutableList<FirebaseVisionFace>?>()

    // todo maybe move this to a detector use case in a domain layer
    internal fun detect(sourceBitmap: Bitmap, width: Int, height: Int) {
        disposable.add(Completable.fromCallable {
            val bitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, false)
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            faceDetector.detectInImage(image)
                .addOnSuccessListener { faces ->
                    Timber.d("$TAG detect() nr. of images detected ${faces.size}")
                    detectLiveData.postValue(faces)

                }.addOnFailureListener {
                    Timber.d("$TAG detect() could not detect image, err: ${it.message}")
                    detectLiveData.postValue(null)
                }
        }.subscribe({
            Timber.d("$TAG detect() started")
        }, {
            Timber.d("$TAG detect() failed, err: ${it.localizedMessage}")
        }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}