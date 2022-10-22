package pham.hien.honeylibrary.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import pham.hien.honeylibrary.R

class ProgressBarLoading(var context: Context) {
    private lateinit var dialog: Dialog
    fun showLoading() {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val img = dialog.findViewById<ImageView>(R.id.img_logo)
        val animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
        img.startAnimation(animation)
        dialog.show()
    }

    fun hideLoading() {
        dialog.dismiss()
    }
}