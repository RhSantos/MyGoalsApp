package com.rh.mygoalsapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.rh.mygoalsapp.R
import java.io.ByteArrayOutputStream

object ImageFiles {

    fun decodeToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
    }

    fun uriToBitmap(uri: Uri,context: Context) : Bitmap {

        if (uri == Uri.EMPTY) {
            val photo = ContextCompat.getDrawable(context,R.drawable.user)
            return photo!!.toBitmap()
        }

        val input = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(input,null,null)!!
    }

    fun encodeToByteArray(bitmap: Bitmap) : ByteArray {
        var byteOutStream  = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.JPEG,100,byteOutStream)
        return byteOutStream.toByteArray()
    }
}