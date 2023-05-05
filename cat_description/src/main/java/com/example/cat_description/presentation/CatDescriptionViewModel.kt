package com.example.cat_description.presentation

import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class CatDescriptionViewModel : ViewModel() {
    private val _isPictureDownloaded = MutableLiveData<Boolean>()
    val isPictureDownloaded : LiveData<Boolean> = _isPictureDownloaded

    private val scope = CoroutineScope(Dispatchers.IO)
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
        _isPictureDownloaded.postValue(false)
    }

    fun download(
        getBitmapCallback: () -> Bitmap
    ) {

        scope.launch(handler) {
            val fos = FileOutputStream(getFileForSavingPicture())
            val picture: Bitmap = getBitmapCallback.invoke()
            picture.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            _isPictureDownloaded.postValue(true)
        }
    }

    private fun getFileForSavingPicture(): File {
        val imageIndex = Random(RANDOM_SEED_VALUE)
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filename = "image_${System.currentTimeMillis()}_$imageIndex.jpg"
        return File(downloadDir, filename)
    }

    private companion object {
        private const val RANDOM_SEED_VALUE = 1000
    }
}