package com.icostel.facerecognition.domain

import android.graphics.Bitmap
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.icostel.facerecognition.ui.utils.SingleLiveEvent
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class FaceDetectionUseCase
@Inject constructor(private val faceDetector: FirebaseVisionFaceDetector) {

    val facesResultEvent = SingleLiveEvent<MutableList<FirebaseVisionFace>?>()

    companion object {
        private val TAG = FaceDetectionUseCase::class.java.simpleName
    }

    fun detect(sourceBitmap: Bitmap, width: Int, height: Int): Completable {
        return Completable.fromCallable {
            val bitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, false)
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            faceDetector.detectInImage(image)
                .addOnSuccessListener { faces ->
                    Timber.d("$$TAG detect() nr. of images detected ${faces.size}")
                    facesResultEvent.postValue(faces)

                }.addOnFailureListener {
                    Timber.d("$TAG detect() could not detect image, err: ${it.message}")
                    facesResultEvent.postValue(null)
                }
        }
    }
}