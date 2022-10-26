package pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog

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

class XacNhanTraSachDialog(
    context: Context,
    private val traDu: (() -> Unit),
    private val traThieu: (() -> Unit),
    private val huy: () -> Unit,
) :
    Dialog(context),
    View.OnClickListener {

    private lateinit var tvTraThieu: TextView
    private lateinit var tvTraDu: TextView
    private lateinit var imvClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_xac_nhan_tra_sach)
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
        tvTraThieu = findViewById(R.id.tv_tra_thieu)
        tvTraDu = findViewById(R.id.tv_tra_du)
        imvClose = findViewById(R.id.imv_close)

        imvClose.setOnClickListener(this)
        tvTraThieu.setOnClickListener(this)
        tvTraDu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> {
                huy()
                dismiss()
            }
            tvTraThieu -> {
                traThieu()
                dismiss()
            }
            tvTraDu -> {
                traDu()
                dismiss()
            }
        }
    }
}

