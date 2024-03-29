package com.icostel.facerecognition.ui.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.icostel.facerecognition.R
import com.icostel.facerecognition.ui.BaseActivity
import com.icostel.facerecognition.ui.utils.bind
import com.icostel.facerecognition.ui.utils.observe
import com.icostel.facerecognition.ui.utils.getViewModel
import com.icostel.facerecognition.ui.views.GraphicOverlay
import com.icostel.facerecognition.ui.views.FaceOverlay
import com.wonderkiln.camerakit.CameraView
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    companion object {
        private const val RESUME_CAMERA_DELAY = 5000L
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var detectBtn: Button
    private lateinit var cameraView: CameraView
    private lateinit var progressBar: ProgressBar
    private lateinit var graphicOverlay: GraphicOverlay
    private lateinit var dimView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG onCreate()")

        mainViewModel = getViewModel(viewModelFactory, MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        bindUi()
    }

    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        super.onPause()
        cameraView.stop()
    }

    private fun bindUi() {
        detectBtn = bind(R.id.btn_detect)
        cameraView = bind(R.id.camera_view)
        graphicOverlay = bind(R.id.graphic_overlay)
        progressBar = bind(R.id.progress_bar)
        dimView = bind(R.id.dim_view)

        detectBtn.setOnClickListener {
            onDetectionStart()
            cameraView.captureImage { cameraImage ->
                mainViewModel.detect(cameraImage.bitmap, cameraView.width, cameraView.height)
                cameraView.stop()
                graphicOverlay.clear()
            }
        }

        mainViewModel.faceEvent.observe(this) { faces ->
            Timber.d("$TAG found faces: ${faces != null && faces.size != 0}")
            onDetectionEnd()
            val res = if (faces != null && faces.size != 0) {
                processFaceResult(faces)
                getString(R.string.faces_found)
            } else {
                getString(R.string.no_faces_found)
            }
            Toast.makeText(this@MainActivity, res, Toast.LENGTH_LONG).show()
        }
    }

    private fun onDetectionStart() {
        progressBar.visibility = View.VISIBLE
        detectBtn.isEnabled = false
        detectBtn.text = getString(R.string.detecting)
        dimView.visibility = View.VISIBLE
    }

    private fun onDetectionEnd() {
        progressBar.visibility = View.GONE
        detectBtn.text = getString(R.string.detect)
        dimView.visibility = View.GONE
        // resume camera preview after processing
        AndroidSchedulers.mainThread().scheduleDirect({
            cameraView.start()
            graphicOverlay.clear()
            detectBtn.isEnabled = true
        }, RESUME_CAMERA_DELAY, TimeUnit.MILLISECONDS)
    }

    private fun processFaceResult(faces: MutableList<FirebaseVisionFace>) {
        faces.forEach { face ->
            val bounds = face.boundingBox
            val rectOverLay = FaceOverlay(this@MainActivity, graphicOverlay, bounds)
            graphicOverlay.add(rectOverLay)
        }
    }
}
