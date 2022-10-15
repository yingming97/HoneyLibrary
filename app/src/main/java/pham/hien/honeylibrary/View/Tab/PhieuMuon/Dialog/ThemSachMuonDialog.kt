package pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSach
import pham.yingming.honeylibrary.Dialog.FailDialog

class ThemSachMuonDialog(
    context: Context,
    private val tongSach: Int,
    private val listSach: ArrayList<Sach>,
    private val callback: ((SachThue) -> Unit)? = null
) :
    Dialog(context),
    View.OnClickListener {

    private val TAG = "YingMing"
    private val mContext = context

    private lateinit var imvClose: ImageView
    private lateinit var tv_them_sach_thue: TextView
    private lateinit var ed_ma_sach: EditText
    private lateinit var nsv_list_sach: NestedScrollView
    private lateinit var rcv_list_sach: RecyclerView
    private lateinit var layout_sach_chon: RelativeLayout
    private lateinit var tv_sach_thue: TextView
    private lateinit var imv_book: ImageView
    private lateinit var imv_minus: ImageView
    private lateinit var imv_plus: ImageView
    private lateinit var tv_so_luong: TextView

    private var mSoLuong = 1

    private lateinit var mSach: Sach
    private lateinit var mSachAdapter: AdapterListSach

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
        setNhapThongTin()
    }

    private fun initView() {
        imvClose = findViewById(R.id.imv_close)
        tv_them_sach_thue = findViewById(R.id.tv_them_sach_thue)
        ed_ma_sach = findViewById(R.id.ed_ma_sach)
        nsv_list_sach = findViewById(R.id.nsv_list_sach)
        rcv_list_sach = findViewById(R.id.rcv_list_sach)
        layout_sach_chon = findViewById(R.id.layout_sach_chon)
        tv_sach_thue = findViewById(R.id.tv_sach_thue)
        imv_book = findViewById(R.id.imv_book)
        imv_minus = findViewById(R.id.imv_minus)
        imv_plus = findViewById(R.id.imv_plus)
        tv_so_luong = findViewById(R.id.tv_so_luong)

        imvClose.setOnClickListener(this)
        tv_them_sach_thue.setOnClickListener(this)
        imv_minus.setOnClickListener(this)
        imv_plus.setOnClickListener(this)

        initRecycleView()
    }

    private fun initData() {
        tv_so_luong.text = mSoLuong.toString()
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tv_them_sach_thue -> {
                callback?.invoke(
                    SachThue(
                        mSach.maSach,
                        mSach.tenSach,
                        mSach.anhBia,
                        mSoLuong,
                        mSach.giaThue
                    )
                )
                dismiss()
                KeyBoardUtils.hideKeyboard(mContext as Activity)
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

    @SuppressLint("SetTextI18n")
    private fun initRecycleView() {
        Log.d(TAG, "initRecycleView: ${listSach.size}")
        mSachAdapter = AdapterListSach(mContext, listSach) {
            layout_sach_chon.visibility = View.VISIBLE
            Glide.with(mContext).load(it.anhBia).placeholder(R.drawable.ic_book_default)
                .into(imv_book)
            tv_sach_thue.text = "${it.maSach} - ${it.tenSach}"
            mSach = it
            nsv_list_sach.visibility = View.GONE
            tv_them_sach_thue.visibility = View.VISIBLE
            tv_them_sach_thue.visibility = View.VISIBLE
            KeyBoardUtils.hideKeyboard(mContext as Activity)
        }
        rcv_list_sach.layoutManager = LinearLayoutManager(mContext)
        rcv_list_sach.setHasFixedSize(false)
        rcv_list_sach.isNestedScrollingEnabled = false
        rcv_list_sach.adapter = mSachAdapter
    }

    private fun setNhapThongTin() {
        ed_ma_sach.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val str = s.toString()
                val listFilter = ArrayList<Sach>()
                for (sach in listSach) {
                    if (sach.tenSach.contains(str, true) || sach.maSach == str?.toInt()) {
                        listFilter.add(sach)
                    }
                }
                if (s.isEmpty()) {
                    nsv_list_sach.visibility = View.GONE
                    mSachAdapter.setListSach(listSach)
                } else {
                    nsv_list_sach.visibility = View.VISIBLE
                    mSachAdapter.setListSach(listFilter)
                }
            }
        })
    }
}

