package com.example.mybaseapp.cameraX

import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class CameraXIntent {
    data class Scan(val imageProxy: ImageProxy): CameraXIntent()
}
sealed class CameraXState {
    data class DisplayMessage(val msg: String): CameraXState()
}

class CameraXViewModel: ViewModel() {

    private val _state = MutableLiveData<CameraXState>()
    val state: LiveData<CameraXState>
        get() = _state
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_CODE_128)
        .build()
    private val scanner = BarcodeScanning.getClient(options)
    private var latestMessage = ""


    fun handle(intent: CameraXIntent) {
        when(intent) {
            is CameraXIntent.Scan -> analyzeBarcode(intent.imageProxy)
        }
    }

    private fun analyzeBarcode(imageProxy: ImageProxy) = CoroutineScope(Dispatchers.IO).launch {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val rawValue = barcodes.firstOrNull()?.rawValue
                    Log.d("barcode", "$rawValue")
                    if(rawValue!= latestMessage)
                        showMessage(rawValue)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                    Log.d("barcode", "${it.localizedMessage}")
                }
                .addOnCompleteListener{
                    Log.d("barcode", "closing")
                    imageProxy.close()
                }
            // Pass image to an ML Kit Vision API
            // ...
        }
    }

    private fun showMessage(rawValue: String?) = this.viewModelScope.launch {
        rawValue?.let {
            latestMessage = it
            _state.value = CameraXState.DisplayMessage(it)
        }
    }
}