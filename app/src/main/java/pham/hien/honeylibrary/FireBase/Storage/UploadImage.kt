package pham.hien.honeylibrary.FireBase.Storage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class UploadImage {

    private val storage = Firebase.storage

    fun uploadImage(imv: ImageView, tableName: String, id: Int): String {

        val storageRef = storage.reference
        val mountainsRef = storageRef.child("$tableName/$id")
        var downloadUri = ""
        imv.isDrawingCacheEnabled = true
        imv.buildDrawingCache()
        val bitmap = (imv.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {

        }
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            mountainsRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result.toString()
            } else {

            }
        }
        return downloadUri
    }
}