package com.example.coroutineimagedownload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coroutineimagedownload.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            buttonDownload.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    progress.visibility = View.VISIBLE
                    val url = editUrl.text.toString()

                    val bitmap = withContext(Dispatchers.IO) {
                        loadImage(url)
                    }

                    imagePreview.setImageBitmap(bitmap)
                    progress.visibility = View.INVISIBLE
                }
            }
        }
    }
}

suspend fun loadImage(imageUrl: String): Bitmap {
    val url = URL(imageUrl)
    val stream = url.openStream()

    return BitmapFactory.decodeStream(stream)
}