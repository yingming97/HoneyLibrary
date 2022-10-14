package pham.yingming.honeylibrary.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.R

class ThemSachMuonDialog(
    context: Context,
    private val tongSach: Int,
    private val listSach: ArrayList<Sach>,
    private val callback: ((SachThue) -> Unit)? = null
) :
    Dialog(context),
    View.OnClickListener {

    private val mContext = context

    private lateinit var imvClose: ImageView
    private lateinit var tv_them_sach_thue: TextView
    private lateinit var ed_ma_sach: EditText
    private lateinit var rcv_list_sach: RecyclerView
    private lateinit var layout_sach_chon: RelativeLayout
    private lateinit var tv_sach_thue: TextView
    private lateinit var imv_minus: ImageView
    private lateinit var imv_plus: ImageView
    private lateinit var tv_so_luong: TextView

    private var mSoLuong = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_them_sach_thue)
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
        initData()
    }

    private fun initView() {
        imvClose = findViewById(R.id.imv_close)
        tv_them_sach_thue = findViewById(R.id.tv_them_sach_thue)
        ed_ma_sach = findViewById(R.id.ed_ma_sach)
        rcv_list_sach = findViewById(R.id.rcv_list_sach)
        layout_sach_chon = findViewById(R.id.layout_sach_chon)
        tv_sach_thue = findViewById(R.id.tv_sach_thue)
        imv_minus = findViewById(R.id.imv_minus)
        imv_plus = findViewById(R.id.imv_plus)
        tv_so_luong = findViewById(R.id.tv_so_luong)

        imvClose.setOnClickListener(this)
        tv_them_sach_thue.setOnClickListener(this)
        imv_minus.setOnClickListener(this)
        imv_plus.setOnClickListener(this)

    }

    private fun initData() {
        tv_so_luong.text = mSoLuong.toString()
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tv_them_sach_thue -> {
                callback?.invoke(SachThue(1,"abc",2,30000))
                dismiss()
            }
            imv_minus -> {
                if (mSoLuong > 1) {
                    mSoLuong -= 1
                    tv_so_luong.text = mSoLuong.toString()
                } else if (mSoLuong == 1) {
                    FailDialog(
                        mContext,
                        mContext.getString(R.string.loi),
                        mContext.getString(R.string.da_dat_toi_so_luong_nho_nhat)
                    ).show()
                }
            }
            imv_plus -> {
                if (mSoLuong < tongSach) {
                    mSoLuong += 1
                    tv_so_luong.text = mSoLuong.toString()
                } else if (mSoLuong == tongSach) {
                    FailDialog(
                        mContext,
                        mContext.getString(R.string.loi),
                        mContext.getString(R.string.da_dat_toi_so_luong_lon_nhat_cua_1_phieu_muon_)
                    ).show()
                }
            }
        }
    }
}

