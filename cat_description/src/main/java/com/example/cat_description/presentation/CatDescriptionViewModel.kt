package com.example.cat_description.presentation

import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class CatDescriptionViewModel : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun download(
        getBitmapCallback: () -> Bitmap,
        isLoadedCallback: () -> Unit,
        isErrorCallback: () -> Unit
    ) {

        scope.launch {
            try {
                val fos = FileOutputStream(getFileForSavingPicture())
                val picture: Bitmap = getBitmapCallback.invoke()
                picture.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                withContext(Dispatchers.Main) {
                    isLoadedCallback.invoke()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isErrorCallback.invoke()
                }
            }
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