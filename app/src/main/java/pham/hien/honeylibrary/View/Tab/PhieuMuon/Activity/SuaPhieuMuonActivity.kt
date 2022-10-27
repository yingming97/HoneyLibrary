package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yomaster.yogaforbeginner.View.Extention.CheckTimeUtils
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.*
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachThueSua
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.ThemSachMuonDialog
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.XacNhanTraSachDialog
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.XacNhanXoaPhieuDialog
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.XacNhanDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class SuaPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout

    private lateinit var layoutInfoPhieu: RelativeLayout
    private lateinit var layout_add_sach_thue: RelativeLayout
    private lateinit var layout_luu: LinearLayout
    private lateinit var imvAvatar: ImageView
    private lateinit var imv_edit_phieu: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvTongSach: TextView
    private lateinit var tvThanhTien: TextView
    private lateinit var tvNgayMuon: TextView
    private lateinit var tvHanTra: TextView
    private lateinit var imv_delete_phieu: ImageView

    private lateinit var rcvListSachThue: RecyclerView

    private var mTongSach: Int = 0
    private var mThanhTien: Int = 0
    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("vi"))
    private lateinit var mPhieuMuon: PhieuMuon
    private var mListSach = ArrayList<Sach>()
    private var mListSachThue = ArrayList<SachThue>()
    private lateinit var mSachThueAdapter: AdapterListSachThueSua

    private lateinit var mPhieuMuonViewModel: PhieuMuonViewModel

    override fun getLayout(): Int {
        return R.layout.acitvity_sua_phieu_muon
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)
        layout_add_sach_thue = findViewById(R.id.layout_add_sach_thue)
        imv_delete_phieu = findViewById(R.id.imv_delete_phieu)
        layout_luu = findViewById(R.id.layout_luu)

        layoutInfoPhieu = findViewById(R.id.layout_info_phieu)
        imvAvatar = findViewById(R.id.imv_avatar)
        imv_edit_phieu = findViewById(R.id.imv_edit_phieu)
        tvName = findViewById(R.id.tv_name)
        tvTongSach = findViewById(R.id.tv_tong_sach)
        tvThanhTien = findViewById(R.id.tv_thanh_tien)
        tvNgayMuon = findViewById(R.id.tv_ngay_muon)
        tvHanTra = findViewById(R.id.tv_han_tra)

        rcvListSachThue = findViewById(R.id.list_sach_thue)

        ScreenUtils().setMarginStatusBar(this, toolBar)

    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        imv_delete_phieu.setOnClickListener(this)
        layout_luu.setOnClickListener(this)
        layout_add_sach_thue.setOnClickListener(this)

    }

    override fun initViewModel() {
        mPhieuMuonViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
    }

    override fun initObserver() {
        mPhieuMuonViewModel.mListSachLiveData.observe(this) {
            mListSach = it
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault() {
        mPhieuMuonViewModel.getListSach()
        mPhieuMuon = intent.getSerializableExtra(Constant.PHIEUMUON.PHIEUMUON) as PhieuMuon
        mTongSach = mPhieuMuon.soLuong
        mThanhTien = mPhieuMuon.tongTien
        val calender = Calendar.getInstance().timeInMillis
        Glide.with(this).load(mPhieuMuon.photoDocGia).placeholder(R.drawable.ic_avatar_default)
            .into(imvAvatar)
        tvName.text = mPhieuMuon.tenDocGia
        tvTongSach.text = "Số lượng: \n0${mPhieuMuon.soLuong}"
        tvThanhTien.text = "Thành tiền: ${moneyFormatter(mPhieuMuon.tongTien)}"
        tvNgayMuon.text = "Ngày mượn:\n${formatter.format(mPhieuMuon.ngayThue)}"
        tvHanTra.text = "Hạn trả:\n${formatter.format(mPhieuMuon.hanTra)}"
        if (!CheckTimeUtils.isToday(mPhieuMuon.hanTra) && mPhieuMuon.hanTra < calender && mPhieuMuon.trangThai != Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
            mPhieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.QUA_HAN
            PhieuMuonDAO().updateTrangThaiPhieuMuon(mPhieuMuon, {}, {})
        }
        mListSachThue = convertStringToListSachThue(mPhieuMuon.listSachThue)
        initRecycleViewSachThue()
        mSachThueAdapter.setListSachThue(mListSachThue)
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> {
                onBackPressed()
            }
            imv_delete_phieu -> {
                XacNhanXoaPhieuDialog(this,
                    taoSai = {
                        PhieuMuonDAO().deletePhieuMuon(this, mPhieuMuon)
                        DoanhThuDAO().deleteDoanhThu(mPhieuMuon.ngayThue)
                    },
                    khac = {
                        if (mPhieuMuon.trangThai != Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
                            FailDialog(this,
                                getString(R.string.loi),
                                getString(R.string.chi_co_the_xoa_phieu_muon_da_tra)).show()
                        } else {
                            PhieuMuonDAO().deletePhieuMuon(this, mPhieuMuon)
                        }
                    }
                ).show()
            }
            layout_add_sach_thue -> {
                ThemSachMuonDialog(this, 5 - mTongSach, mListSach) {
                    loadThemSach(it)
                    checkSachThue(it)
                }.show()
            }
            layout_luu -> {
                savePhieuMuon()
            }
        }
    }

    private fun initRecycleViewSachThue() {
        mSachThueAdapter = AdapterListSachThueSua(this, mListSachThue, mTongSach,
            themSoluong = { sachThue ->
                loadPhieuMuonThem(sachThue)
            },
            giamSoLuong = { sachThue ->
                loadPhieuMuonTru(sachThue)
            },
            remove = { sachThue ->
                loadRemoveSach(sachThue)
            }
        )
        rcvListSachThue.layoutManager = LinearLayoutManager(this)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = mSachThueAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun loadThemSach(sachThue: SachThue) {
        mTongSach += sachThue.soLuong
        mSachThueAdapter.setTongSoLuong(mTongSach)
        mThanhTien += (sachThue.soLuong * sachThue.giaThue)
        tvTongSach.text = "Số lượng:\n0$mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_luu.visibility = View.GONE
        }
        if (mTongSach == 5) {
            layout_add_sach_thue.visibility = View.GONE
        }
        if (mTongSach != 0) {
            layout_luu.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPhieuMuonThem(sachThue: SachThue) {
        mTongSach += 1
        mThanhTien += sachThue.giaThue
        tvTongSach.text = "Số lượng:\n0$mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_luu.visibility = View.GONE
        }
        if (mTongSach == 5) {
            layout_add_sach_thue.visibility = View.GONE
        }
        if (mTongSach != 0) {
            layout_luu.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadRemoveSach(sachThue: SachThue) {
        mTongSach -= sachThue.soLuong
        mSachThueAdapter.setTongSoLuong(mTongSach)
        mThanhTien -= (sachThue.soLuong * sachThue.giaThue)
        tvTongSach.text = "Số lượng:\n0$mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_add_sach_thue.visibility = View.VISIBLE
            layout_luu.visibility = View.GONE
        } else if (mTongSach < 5) {
            layout_add_sach_thue.visibility = View.VISIBLE
        } else {
            layout_luu.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPhieuMuonTru(sachThue: SachThue) {
        mTongSach -= 1
        mThanhTien -= sachThue.giaThue
        tvTongSach.text = "Số lượng:\n0$mTongSach"
        tvThanhTien.text = "Thành tiền: " + moneyFormatter(mThanhTien)
        if (mTongSach == 0) {
            layout_add_sach_thue.visibility = View.VISIBLE
            layout_luu.visibility = View.GONE
        } else if (mTongSach < 5) {
            layout_add_sach_thue.visibility = View.VISIBLE
        } else {
            layout_luu.visibility = View.VISIBLE
        }
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

    private fun savePhieuMuon() {
        val listSachThueOld = convertStringToListSachThue(mPhieuMuon.listSachThue)
        val mapListSachCu: MutableMap<Int, SachThue> = mutableMapOf()
        val mapListSachMoi: MutableMap<Int, SachThue> = mutableMapOf()
        val mapListSachChung: MutableMap<Int, Int> = mutableMapOf()
//        Log.d(TAG, "listSachThueOld: $listSachThueOld")
//        Log.d(TAG, "mListSachThue: ${mListSachThue}")
        for (sachThue1 in listSachThueOld) {
            for (sachThue2 in mListSachThue) {
                if (sachThue1.maSach == sachThue2.maSach) {

                    val soLuongConLai =
                        getSoLuongConLai(sachThue1.maSach) + (sachThue1.soLuong - sachThue2.soLuong)
//                    PhieuMuonDAO().updateSoLuongSachConLai(sachThue1.maSach, soLuongConLai)
                    mapListSachChung[sachThue1.maSach] = soLuongConLai
                } else {
                    mapListSachCu[sachThue1.maSach] = sachThue1
                    mapListSachMoi[sachThue2.maSach] = sachThue2
                }
            }
        }
        mapListSachChung.forEach { chung ->
            mapListSachCu.remove(chung.key)
            mapListSachMoi.remove(chung.key)
        }
        mapListSachChung.forEach {
            PhieuMuonDAO().updateSoLuongSachConLai(it.key, it.value)
        }

        mapListSachCu.forEach {
            PhieuMuonDAO().updateSoLuongSachConLai(it.key,
                getSoLuongConLai(it.key) + it.value.soLuong)
            Log.d(TAG, "mapListSachCu: ${it.key} : ${it.value}")
        }
        mapListSachMoi.forEach {
            PhieuMuonDAO().updateSoLuongSachConLai(it.key,
                getSoLuongConLai(it.key) - it.value.soLuong)
            Log.d(TAG, "mapListSachMoi: ${it.key} : ${it.value}")
        }
        mPhieuMuon.listSachThue = convertListSachThueToString(mListSachThue)
        mPhieuMuon.soLuong = mTongSach
        mPhieuMuon.tongTien = mThanhTien
        PhieuMuonDAO().updatePhieuMuon(this, mPhieuMuon)
        DoanhThuDAO().addDoanhThu(DoanhThu(mThanhTien, mPhieuMuon.ngayThue))
    }

    private fun getSoLuongConLai(maSach: Int): Int {
        for (sach in mListSach) {
            if (sach.maSach == maSach) {
                return sach.soLuongConLai
            }
        }
        return 0
    }

    override fun onBackPressed() {
        val intent = Intent(this, ChiTietPhieuMuonActivity::class.java)
        intent.putExtra(Constant.PHIEUMUON.PHIEUMUON, mPhieuMuon)
        startActivity(intent)
        finish()
    }
}