package pham.hien.honeylibrary.View.Tab.TheLoai.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.FireBase.FireStore.TheLoaiDAO
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel

class SuaTheLoaiDialog(
    context: Context,
    theLoai: TheLoai
) : Dialog(context),
    View.OnClickListener {

    private val mContext = context

    private lateinit var imvClose: ImageView
    private lateinit var ed_sua_the_loai: EditText
    private lateinit var tv_sua_the_loai: TextView
    private var mTheLoai = theLoai

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_sua_the_loai)
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
        imvClose = findViewById(R.id.imv_close)
        ed_sua_the_loai = findViewById(R.id.ed_sua_the_loai)
        tv_sua_the_loai = findViewById(R.id.tv_sua_the_loai)

        imvClose.setOnClickListener(this)
        tv_sua_the_loai.setOnClickListener(this)
        ed_sua_the_loai.setText(mTheLoai.tenTheLoai)
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tv_sua_the_loai -> {
                mTheLoai.tenTheLoai = ed_sua_the_loai.text.toString()
                TheLoaiDAO().updateTheLoai(mContext, mTheLoai)
                dismiss()
            }
        }
    }
}