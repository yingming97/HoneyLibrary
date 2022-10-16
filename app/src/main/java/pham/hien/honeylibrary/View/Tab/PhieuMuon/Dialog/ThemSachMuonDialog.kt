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
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSach
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.lang.Exception

class ThemSachMuonDialog(
    activity: Activity,
    private val tongSach: Int,
    private val listSach: ArrayList<Sach>,
    private val callback: ((SachThue) -> Unit)? = null,
) :
    Dialog(activity),
    View.OnClickListener {

    private val TAG = "YingMing"
    private val mActivity = activity

    private lateinit var imvClose: ImageView
    private lateinit var tvThemSachThue: TextView
    private lateinit var edMaSach: EditText
    private lateinit var nsvListSach: NestedScrollView
    private lateinit var rcvListSach: RecyclerView
    private lateinit var layoutSachChon: RelativeLayout
    private lateinit var tvSachThue: TextView
    private lateinit var imvBook: ImageView
    private lateinit var imvMinus: ImageView
    private lateinit var imvPlus: ImageView
    private lateinit var tvSoLuong: TextView
    private lateinit var tvNoData: TextView

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
        tvThemSachThue = findViewById(R.id.tv_them_sach_thue)
        edMaSach = findViewById(R.id.ed_ma_sach)
        nsvListSach = findViewById(R.id.nsv_list_sach)
        rcvListSach = findViewById(R.id.rcv_list_sach)
        layoutSachChon = findViewById(R.id.layout_sach_chon)
        tvSachThue = findViewById(R.id.tv_sach_thue)
        imvBook = findViewById(R.id.imv_book)
        imvMinus = findViewById(R.id.imv_minus)
        imvPlus = findViewById(R.id.imv_plus)
        tvSoLuong = findViewById(R.id.tv_so_luong)
        tvNoData = findViewById(R.id.tv_no_data)

        imvClose.setOnClickListener(this)
        tvThemSachThue.setOnClickListener(this)
        imvMinus.setOnClickListener(this)
        imvPlus.setOnClickListener(this)

        initRecycleView()
    }

    private fun initData() {
        tvSoLuong.text = mSoLuong.toString()
    }

    override fun onClick(v: View?) {
        when (v) {
            imvClose -> dismiss()
            tvThemSachThue -> {
                callback?.invoke(
                    SachThue(
                        mSach.maSach,
                        mSach.tenSach,
                        mSach.anhBia,
                        mSoLuong,
                        mSach.giaThue,
                        mSach.giaSach,
                    )
                )
                dismiss()
                KeyBoardUtils.hideKeyboard(mActivity)
            }
            imvMinus -> {
                if (mSoLuong > 1) {
                    mSoLuong -= 1
                    tvSoLuong.text = mSoLuong.toString()
                } else if (mSoLuong == 1) {
                    FailDialog(
                        mActivity,
                        mActivity.getString(R.string.loi),
                        mActivity.getString(R.string.da_dat_toi_so_luong_nho_nhat)
                    ).show()
                }
            }
            imvPlus -> {
                if (mSoLuong < tongSach) {
                    mSoLuong += 1
                    tvSoLuong.text = mSoLuong.toString()
                } else if (mSoLuong == tongSach) {
                    FailDialog(
                        mActivity,
                        mActivity.getString(R.string.loi),
                        mActivity.getString(R.string.da_dat_toi_so_luong_lon_nhat_cua_1_phieu_muon_)
                    ).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initRecycleView() {
        mSachAdapter = AdapterListSach(mActivity, listSach) {
            layoutSachChon.visibility = View.VISIBLE
            Glide.with(mActivity).load(it.anhBia).placeholder(R.drawable.ic_book_default)
                .into(imvBook)
            tvSachThue.text = "${it.maSach} - ${it.tenSach}"
            mSach = it
            nsvListSach.visibility = View.GONE
            tvThemSachThue.visibility = View.VISIBLE
            tvThemSachThue.visibility = View.VISIBLE
            KeyBoardUtils.hideKeyboard(mActivity)
        }
        rcvListSach.layoutManager = LinearLayoutManager(mActivity)
        rcvListSach.setHasFixedSize(false)
        rcvListSach.isNestedScrollingEnabled = false
        rcvListSach.adapter = mSachAdapter
    }

    private fun setNhapThongTin() {
        edMaSach.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                val str = s.toString()
                val maSach = try {
                    Integer.parseInt(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val listFilter = ArrayList<Sach>()
                for (sach in listSach) {
                    if (sach.tenSach.contains(str, true) || sach.maSach == maSach) {
                        listFilter.add(sach)
                    }
                }
                if (s.isEmpty()) {
                    nsvListSach.visibility = View.GONE
                    mSachAdapter.setListSach(listSach)
                    tvNoData.visibility = View.GONE
                } else {
                    nsvListSach.visibility = View.VISIBLE
                    mSachAdapter.setListSach(listFilter)
                    if (listFilter.isNotEmpty()) {
                        tvNoData.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}

