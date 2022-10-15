package pham.hien.honeylibrary.Dialog

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

class ThemTheLoaiDialog(
    context: Context

) : Dialog(context),
    View.OnClickListener {

    private val mContext = context

    private lateinit var imvClose: ImageView
    private lateinit var ed_ten_the_loai: EditText
    private lateinit var tv_them_the_loai: TextView
    private lateinit var mTheLoai: TheLoai


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_them_the_loai)
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
        mTheLoai = TheLoai()
    }

    private fun initView() {
        imvClose = findViewById(R.id.imv_close)
        ed_ten_the_loai = findViewById(R.id.ed_ten_the_loai)
        tv_them_the_loai = findViewById(R.id.tv_them_the_loai)

        imvClose.setOnClickListener(this)
        tv_them_the_loai.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tv_them_the_loai -> {
                mTheLoai.tenTheLoai = ed_ten_the_loai.text.toString()
                mTheLoai.maTheLoai = TheLoaiDAO().getListTheLoai1().last().maTheLoai+1
                TheLoaiDAO().addNewTheLoai(mContext, mTheLoai)
//                Log.d("TAG", "onClick: " + TheLoaiDAO().getListTheLoai().size)
            }
        }
    }
}