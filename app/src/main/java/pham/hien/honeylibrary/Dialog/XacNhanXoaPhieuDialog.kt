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

class XacNhanXoaPhieuDialog(
    context: Context,
    private val taoSai: (() -> Unit)? = null,
    private val khac: (() -> Unit)? = null,
) :
    Dialog(context),
    View.OnClickListener {

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvXoa: TextView
    private lateinit var tvHuy: TextView
    private lateinit var imvClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_xac_nhan_xoa_phieu)
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
        tvXoa = findViewById(R.id.tv_xoa)
        tvHuy = findViewById(R.id.tv_huy)
        imvClose = findViewById(R.id.imv_close)

        imvClose.setOnClickListener(this)
        tvXoa.setOnClickListener(this)
        tvHuy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tvXoa -> {
                taoSai?.invoke()
                dismiss()
            }
            tvHuy -> {
                khac?.invoke()
                dismiss()
            }
        }
    }
}

