package pham.yingming.honeylibrary.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import pham.hien.honeylibrary.R

class SuccessDialog(context: Context, private val title: String, private val content: String) :
    Dialog(context),
    View.OnClickListener {

    private lateinit var tvClose: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var imvClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_fail)
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
        tvClose = findViewById(R.id.tv_close)
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
        imvClose = findViewById(R.id.imv_close)

        tvClose.setOnClickListener(this)
        imvClose.setOnClickListener(this)

        tvTitle.text = title
        tvContent.text = content
    }

    override fun onClick(v: View?) {
        when (v) {
            tvClose, imvClose -> dismiss()
        }
    }
}