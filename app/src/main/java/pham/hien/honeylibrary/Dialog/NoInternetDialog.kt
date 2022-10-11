package pham.yingming.honeylibrary.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import pham.hien.honeylibrary.R

class NoInternetDialog(context: Context) : Dialog(context), View.OnClickListener {

    private lateinit var txv_dialog_no_internet__ok: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_no_internet)
        window!!.decorView.setBackgroundResource(R.color.transparent)
        window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window!!.attributes = wlp
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(true)
        initView()
    }

    private fun initView() {
        txv_dialog_no_internet__ok = findViewById(R.id.tv_ok_dialog_no_internet)
        txv_dialog_no_internet__ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txv_dialog_no_internet__ok -> dismiss()
        }
    }
}