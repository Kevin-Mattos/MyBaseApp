package com.example.mybaseapp.cameraX

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ImageAnalyzer(val analyzeImage: (imageProxy: ImageProxy) -> Unit) : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy) {
        analyzeImage(imageProxy)
    }
}