package com.icostel.facerecognition.ui.screens

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.icostel.facerecognition.R
import com.icostel.facerecognition.ui.utils.observe
import com.icostel.facerecognition.ui.utils.bind
import com.icostel.facerecognition.ui.utils.setImmersive
import com.icostel.facerecognition.ui.viewmodels.MainViewModel
import com.icostel.facerecognition.ui.views.GraphicOverlay
import com.icostel.facerecognition.ui.views.RectOverlay
import timber.log.Timber

class MainActivity : BaseActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var detectBtn: Button
    private lateinit var cameraKitView: CameraKitView
    private lateinit var alertDialog: AlertDialog
    private lateinit var graphicOverlay: GraphicOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG onCreate()")

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        bindUi()

        alertDialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.please_wait))
            .setCancelable(false)
            .create()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        super.onPause()
        cameraKitView.onPause()
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onStop() {
        super.onStop()
        cameraKitView.onStop()
    }

    private fun bindUi() {
        detectBtn = bind(R.id.btn_detect)
        cameraKitView = bind(R.id.camera_view)
        graphicOverlay = bind(R.id.graphic_overlay)
        cameraKitView.flash = CameraKit.FLASH_OFF

        detectBtn.setOnClickListener {
            cameraKitView.captureImage { cameraKitView, byteArray ->
                cameraKitView.onStop()
                alertDialog.show()
                setImmersive()
                mainViewModel.detect(byteArray, cameraKitView?.width ?: 0, cameraKitView?.height ?: 0)
            }
            graphicOverlay.clear()
        }

        mainViewModel.detectLiveData.observe(this) { faces ->
            if (faces != null) {
                processFaceResult(faces)
            }
        }
    }

    private fun processFaceResult(faces: MutableList<FirebaseVisionFace>) {
        faces.forEach { face ->
            val bounds = face.boundingBox
            val rectOverLay = RectOverlay(graphicOverlay, bounds)
            graphicOverlay.add(rectOverLay)
        }
        alertDialog.dismiss()
    }

    // the permission mechanism is already integrated into camera view
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
