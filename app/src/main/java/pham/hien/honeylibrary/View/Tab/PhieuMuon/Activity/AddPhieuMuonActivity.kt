package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.Model.*
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.*
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachPhieuMuon
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListUser
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.ThemSachMuonDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout
    private lateinit var edSdtEmail: EditText
    private lateinit var imv_search: ImageView
    private lateinit var imv_empty: ImageView

    private lateinit var layoutInfoPhieu: RelativeLayout
    private lateinit var imvAvatar: ImageView
    private lateinit var tvNoData: TextView
    private lateinit var tvName: TextView
    private lateinit var tvTongSach: TextView
    private lateinit var tvThanhTien: TextView
    private lateinit var tvNgayMuon: TextView
    private lateinit var tvHanTra: TextView
    private lateinit var tvKhongSachMuon: TextView
    private lateinit var layout_add_sach_thue: RelativeLayout
    private lateinit var layout_add_phieu: LinearLayout

    private lateinit var rcvListDocGia: RecyclerView
    private lateinit var ncvDocGia: NestedScrollView
    private lateinit var rcvListSachThue: RecyclerView

    private var mTongSach: Int = 0
    private var mThanhTien: Int = 0
    private var mNgayMuon: Long = 0
    private var mHanTra: Long = 0
    private val formatter = SimpleDateFormat("EEE, d MMM y", Locale("vi"))

    private lateinit var mDocGia: UserModel
    private lateinit var mNhanVien: UserModel
    private lateinit var mPhieuMuon: PhieuMuon
    private lateinit var mUserAdapter: AdapterListUser
    private lateinit var mSachThueAdapter: AdapterListSachPhieuMuon
    private var mListSach = ArrayList<Sach>()
    private var mListSachThue = ArrayList<SachThue>()
    private lateinit var mListUser: ArrayList<UserModel>

    private lateinit var mPhieuMuonViewModel: PhieuMuonViewModel


    override fun getLayout(): Int {
        return R.layout.acitvity_add_phieu_muon
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)
        edSdtEmail = findViewById(R.id.ed_sdt_email)
        imv_search = findViewById(R.id.imv_search)
        imv_empty = findViewById(R.id.imv_empty)

        layoutInfoPhieu = findViewById(R.id.layout_info_phieu)
        imvAvatar = findViewById(R.id.imv_avatar)
        tvNoData = findViewById(R.id.tv_no_data)
        tvName = findViewById(R.id.tv_name)
        tvTongSach = findViewById(R.id.tv_tong_sach)
        tvThanhTien = findViewById(R.id.tv_thanh_tien)
        tvNgayMuon = findViewById(R.id.tv_ngay_muon)
        tvHanTra = findViewById(R.id.tv_han_tra)
        tvKhongSachMuon = findViewById(R.id.tv_khong_sach_muon)
        layout_add_sach_thue = findViewById(R.id.layout_add_sach_thue)
        layout_add_phieu = findViewById(R.id.layout_add_phieu)

        rcvListDocGia = findViewById(R.id.list_doc_gia)
        ncvDocGia = findViewById(R.id.ncv_doc_gia)
        rcvListSachThue = findViewById(R.id.list_sach_thue)

        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        layout_add_sach_thue.setOnClickListener(this)
        layout_add_phieu.setOnClickListener(this)
        imv_search.setOnClickListener(this)
        imv_empty.setOnClickListener(this)
        initRecycleViewDocGia()
        initRecycleViewSachThue()
        setNhapThongTin()

    }

    override fun initViewModel() {
        mPhieuMuonViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
    }

    override fun initObserver() {
        mPhieuMuonViewModel.mListSachLiveData.observe(this) {
            mListSach = it
        }
        mPhieuMuonViewModel.mListDocGiaLiveData.observe(this) {
            mUserAdapter.setListUser(it)
            mListUser = it
        }
    }

    override fun initDataDefault() {
        mNhanVien = SharedPrefUtils.getUserData(this)!!
        mPhieuMuonViewModel.getListDocGia()
        mPhieuMuonViewModel.getListSach()
        setHanTraSach()
        loadView(null)
        mPhieuMuon = PhieuMuon()
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> finish()
            layout_add_sach_thue -> {
                ThemSachMuonDialog(this, 5 - mTongSach, mListSach) {
                    loadPhieuMuonThem(it)
                    checkSachThue(it)
                }.show()
            }
            layout_add_phieu -> {
                themPhieuMuon()
                loadView(null)
                reset()
            }
            imv_empty -> {
                edSdtEmail.setText("")
            }
        }
    }

    private fun reset() {
        setHanTraSach()
        mTongSach = 0
        mListSachThue = ArrayList()
        mThanhTien = 0
        tvTongSach.text = "Tổng số sách : 0"
        tvThanhTien.text = "Thành tiền : 0đ"
    }

    private fun loadView(user: UserModel?) {
        if (user == null) {
            layoutInfoPhieu.visibility = View.GONE
            layout_add_sach_thue.visibility = View.GONE
            layout_add_phieu.visibility = View.GONE
            mSachThueAdapter.setListSachThue(ArrayList())
            tvKhongSachMuon.visibility = View.GONE
            edSdtEmail.setText("")
        } else {
            layoutInfoPhieu.visibility = View.VISIBLE
            layout_add_sach_thue.visibility = View.VISIBLE
            ncvDocGia.visibility = View.GONE
            tvName.text = user.name
            Glide.with(this).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
                .into(imvAvatar)
            if (mTongSach == 0) {
                tvKhongSachMuon.visibility = View.VISIBLE
            } else {
                tvKhongSachMuon.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPhieuMuonThem(sachThue: SachThue) {
        mTongSach += sachThue.soLuong
        mThanhTien += (sachThue.soLuong * sachThue.giaThue)
        tvTongSach.text = "Tống số sách: $mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_add_phieu.visibility = View.GONE
            tvKhongSachMuon.visibility = View.VISIBLE
        }
        if (mTongSach == 5) {
            layout_add_sach_thue.visibility = View.GONE
        }
        if (mTongSach != 0) {
            layout_add_phieu.visibility = View.VISIBLE
            tvKhongSachMuon.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPhieuMuonTru(sachThue: SachThue) {
        mTongSach -= sachThue.soLuong
        mThanhTien -= (sachThue.soLuong * sachThue.giaThue)
        tvTongSach.text = "Tống số sách: $mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_add_phieu.visibility = View.GONE
            tvKhongSachMuon.visibility = View.VISIBLE
            layout_add_sach_thue.visibility = View.VISIBLE
        } else if (mTongSach < 5) {
            layout_add_sach_thue.visibility = View.VISIBLE
        } else {
            layout_add_phieu.visibility = View.VISIBLE
            tvKhongSachMuon.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setHanTraSach() {
        val calendar = Calendar.getInstance()
        mNgayMuon = calendar.timeInMillis
        calendar.timeInMillis =
            Calendar.getInstance().timeInMillis + 7 * 24 * 60 * 60 * 1000
        mHanTra = calendar.timeInMillis
        tvNgayMuon.text = "Ngày mượn: ${formatter.format(mNgayMuon)}"
        tvHanTra.text = "Hạn trả: ${formatter.format(mHanTra)}"
    }

    private fun initRecycleViewDocGia() {
        mListUser = ArrayList()
        mUserAdapter = AdapterListUser(this, mListUser) {
            mDocGia = it
            edSdtEmail.setText(it.name)
            loadView(it)
            KeyBoardUtils.hideKeyboard(this)
        }
        rcvListDocGia.layoutManager = LinearLayoutManager(this)
        rcvListDocGia.setHasFixedSize(false)
        rcvListDocGia.isNestedScrollingEnabled = false
        rcvListDocGia.adapter = mUserAdapter
    }

    private fun initRecycleViewSachThue() {
        mSachThueAdapter = AdapterListSachPhieuMuon(this, mListSachThue) {
            loadPhieuMuonTru(it)
            mListSachThue.remove(it)
            mSachThueAdapter.setListSachThue(mListSachThue)
        }
        rcvListSachThue.layoutManager = LinearLayoutManager(this)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = mSachThueAdapter
    }

    private fun setNhapThongTin() {
        edSdtEmail.addTextChangedListener(object : TextWatcher {
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
                val listFilter = ArrayList<UserModel>()
                for (user in mListUser) {
                    if (user.name.contains(str, true)
                        || user.sdt.contains(str, true)
                        || user.email.contains(str, true)
                    ) {
                        listFilter.add(user)
                    }
                }
                if (s.isEmpty()) {
                    mUserAdapter.setListUser(mListUser)
                    ncvDocGia.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                    imv_search.visibility = View.VISIBLE
                    imv_empty.visibility = View.GONE
                } else {
                    imv_search.visibility = View.GONE
                    imv_empty.visibility = View.VISIBLE
                    mUserAdapter.setListUser(listFilter)
                    ncvDocGia.visibility = View.VISIBLE
                    if (listFilter.isEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                    } else {
                        tvNoData.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun checkSachThue(sachThue: SachThue) {
        if (mListSachThue.isNotEmpty()) {
            var check = false
            for (i in 0 until mListSachThue.size) {
                if (sachThue.maSach == mListSachThue[i].maSach) {
                    sachThue.soLuong += mListSachThue[i].soLuong
                    mListSachThue[i] = sachThue
                    check = true
                }
            }
            if (!check) {
                mListSachThue.add(sachThue)
            }
        } else {
            mListSachThue.add(sachThue)
        }
        mSachThueAdapter.setListSachThue(mListSachThue)
    }

    private fun themPhieuMuon() {
        mPhieuMuon.maPhieuMuon = mDocGia.firebaseId + "_" + mNhanVien.firebaseId + "_" + mNgayMuon
        mPhieuMuon.maDocGia = mDocGia.firebaseId
        mPhieuMuon.tenDocGia = mDocGia.name
        mPhieuMuon.photoDocGia = mDocGia.avatar
        mPhieuMuon.maNhanVien = mNhanVien.firebaseId
        mPhieuMuon.soLuong = mTongSach
        mPhieuMuon.tongTien = mThanhTien
        mPhieuMuon.ngayThue = mNgayMuon
        mPhieuMuon.hanTra = mHanTra
        mPhieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.DANG_MUON
        mPhieuMuon.listSachThue = convertListSachThueToString(mListSachThue)
        PhieuMuonDAO().addPhieuMuon(this, mPhieuMuon)
        DoanhThuDAO().addDoanhThu(DoanhThu(mThanhTien, mNgayMuon))
        for (sachThue in mListSachThue) {
            val sachUpdate = getSachUpdate(sachThue)
            sachUpdate?.let { PhieuMuonDAO().updateSoLuongSachConLai(sachUpdate, sachThue) }
        }
    }

    private fun getSachUpdate(sachThue: SachThue): Sach? {
        for (sach in mListSach) {
            if (sachThue.maSach == sach.maSach) {
                return sach
            }
        }
        return null
    }
}