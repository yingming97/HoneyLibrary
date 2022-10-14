package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.annotation.SuppressLint
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.moneyFormatter
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListUser
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
import pham.yingming.honeylibrary.Dialog.ThemSachMuonDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imv_close: ImageView
    private lateinit var tool_bar: RelativeLayout
    private lateinit var ed_sdt_email: EditText

    private lateinit var layout_info_phieu: RelativeLayout
    private lateinit var imv_avatar: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_tong_sach: TextView
    private lateinit var tv_thanh_tien: TextView
    private lateinit var tv_ngay_muon: TextView
    private lateinit var tv_han_tra: TextView
    private lateinit var tv_them_phieu: TextView

    private lateinit var list_doc_gia: RecyclerView
    private lateinit var ncv_doc_gia: NestedScrollView
    private lateinit var list_sach_thue: RecyclerView
    private lateinit var imv_add_sach_thue: ImageView

    private var mTongSach: Int = 0
    private var mThanhTien: Int = 0
    private var mNgayMuon: Long = 0
    private var mHanTra: Long = 0
    private val formatter = SimpleDateFormat("EEE, d MMM y", Locale("vi"))

    private lateinit var mUserAdapter: AdapterListUser
    private var mListSach = ArrayList<Sach>()
    private var mListSachThue = ArrayList<SachThue>()
    private lateinit var mListUser: ArrayList<UserModel>
//    private var mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
//    private var mListUserLiveData = MutableLiveData<ArrayList<UserModel>>()

    private lateinit var mPhieuMuonViewModel: PhieuMuonViewModel


    override fun getLayout(): Int {
        return R.layout.acitvity_add_phieu_muon
    }

    override fun initView() {
        imv_close = findViewById(R.id.imv_close)
        tool_bar = findViewById(R.id.tool_bar)
        ed_sdt_email = findViewById(R.id.ed_sdt_email)

        layout_info_phieu = findViewById(R.id.layout_info_phieu)
        imv_avatar = findViewById(R.id.imv_avatar)
        tv_name = findViewById(R.id.tv_name)
        tv_tong_sach = findViewById(R.id.tv_tong_sach)
        tv_thanh_tien = findViewById(R.id.tv_thanh_tien)
        tv_ngay_muon = findViewById(R.id.tv_ngay_muon)
        tv_han_tra = findViewById(R.id.tv_han_tra)
        tv_them_phieu = findViewById(R.id.tv_them_phieu)

        list_doc_gia = findViewById(R.id.list_doc_gia)
        ncv_doc_gia = findViewById(R.id.ncv_doc_gia)
        list_sach_thue = findViewById(R.id.list_sach_thue)
        imv_add_sach_thue = findViewById(R.id.imv_add_sach_thue)

        ScreenUtils().setMarginStatusBar(this, tool_bar)
    }

    override fun initListener() {
        imv_close.setOnClickListener(this)
        imv_add_sach_thue.setOnClickListener(this)
        tv_them_phieu.setOnClickListener(this)
        initRecycleView()

        ed_sdt_email.addTextChangedListener(object : TextWatcher {
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
                Log.d(TAG, "onTextChanged: $str")
                val listFilter = ArrayList<UserModel>()
                for (user in mListUser) {
                    if (user.name.contains(str) || user.sdt.contains(str) || user.email.contains(str)) {
                        listFilter.add(user)
                    }
                }
                if (s.isEmpty()) {
                    ncv_doc_gia.visibility = View.GONE
                    mUserAdapter.setListUser(mListUser)
                } else {
                    mUserAdapter.setListUser(listFilter)
                    ncv_doc_gia.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun initViewModel() {
        mPhieuMuonViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
        mPhieuMuonViewModel.getListDocGia()
        mPhieuMuonViewModel.getListSach()
    }

    override fun initObserver() {
        mPhieuMuonViewModel.mListSachLiveData.observe(this) {
            if (it.isNotEmpty()) {
                mListSach = it
            }
        }
        mPhieuMuonViewModel.mListDocGiaLiveData.observe(this) {
//            for (user in it) {
//                if (user.type == 0) {
//                    mListUser.add(user)
//                }
////                mUserAdapter.setListUser(mListUser)
//            }
        }
    }

    override fun initDataDefault() {
        mListUser = UserDAO().getListUser()
        setHanTraSach()
        loadView(null)
        Handler().postDelayed({
            mUserAdapter.setListUser(mListUser)
        }, 2000)
    }

    override fun onClick(view: View?) {
        when (view) {
            imv_close -> finish()
            imv_add_sach_thue -> {
                ThemSachMuonDialog(this, 5 - mTongSach, mListSach) {
                    loadPhieuMuon(it)
                    mListSachThue.add(it)
                }.show()
            }
            tv_them_phieu -> {

            }
        }
    }

    private fun loadView(user: UserModel?) {
        if (user == null) {
            layout_info_phieu.visibility = View.GONE
            imv_add_sach_thue.visibility = View.GONE
            tv_them_phieu.visibility = View.GONE
        } else {
            layout_info_phieu.visibility = View.VISIBLE
            imv_add_sach_thue.visibility = View.VISIBLE
            tv_them_phieu.visibility = View.VISIBLE
            ncv_doc_gia.visibility = View.GONE
            tv_name.text = user.name
            Glide.with(this).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
                .into(imv_avatar)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPhieuMuon(sachThue: SachThue) {
        mTongSach += sachThue.soLuong
        mThanhTien += (sachThue.soLuong * sachThue.giaThue)
        tv_tong_sach.text = "Tống số sách: $mTongSach"
        tv_thanh_tien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
    }

    @SuppressLint("SetTextI18n")
    private fun setHanTraSach() {
        val calendar = Calendar.getInstance()
        mNgayMuon = calendar.timeInMillis
        calendar.timeInMillis =
            Calendar.getInstance().timeInMillis + 7 * 24 * 60 * 60 * 1000
        mHanTra = calendar.timeInMillis
        tv_ngay_muon.text = "Ngày mượn: ${formatter.format(mNgayMuon)}"
        tv_han_tra.text = "Hạn trả: ${formatter.format(mHanTra)}"
    }

    private fun initRecycleView() {
        mListUser = ArrayList()
        mUserAdapter = AdapterListUser(this, mListUser) {
            ed_sdt_email.setText(it.name)
            loadView(it)
            KeyBoardUtils.hideKeyboard(this)
        }
        list_doc_gia.layoutManager = LinearLayoutManager(this)
        list_doc_gia.setHasFixedSize(false)
        list_doc_gia.isNestedScrollingEnabled = false
        list_doc_gia.adapter = mUserAdapter
    }
}