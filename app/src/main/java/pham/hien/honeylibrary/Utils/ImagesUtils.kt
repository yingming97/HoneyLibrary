package pham.hien.honeylibrary.Utils

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.provider.MediaStore
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import pham.hien.honeylibrary.R
import java.io.IOException

class ImagesUtils {
    fun checkPermissionChonAnh(context: Context, imv: ImageView) {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                openImagesPicker(context, imv)
            }

            override fun onPermissionDenied(deniedPermissions: List<String?>) {

            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("Bạn cần cấp quyền để chọn ảnh/ chụp ảnh từ thiết bị.\n\nHãy cấp quyền cho ứng dụng [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
            .check();
    }

    private fun openImagesPicker(context: Context, imv: ImageView) {
        TedImagePicker.with(context)
            .start { uri ->
                try {
                    val pathFile = URIPathHelper().getPath(context, uri)
                    val bitmap1 = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

                    val bitmap: Bitmap? = pathFile?.let { compressImageFromPath(bitmap1, it) }
                    Glide.with(context).load(bitmap)
                        .placeholder(R.drawable.img_sach_add_default)
                        .into(imv)
                } catch (e: Exception) {
                }
            }
    }

    private fun compressImageFromPath(bitmap: Bitmap, filePath: String): Bitmap? {
        // kiểm tra orientation của ảnh và xoay đúng chiều cho bitmap
        var scaledBitmap: Bitmap? = null
        val exif: ExifInterface

        try {
            exif = ExifInterface(filePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            when (orientation) {
                6 -> {
                    matrix.postRotate(90f)
                }
                3 -> {
                    matrix.postRotate(180f)
                }
                8 -> {
                    matrix.postRotate(270f)
                }
            }
            scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, matrix,
                true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return scaledBitmap
    }
}