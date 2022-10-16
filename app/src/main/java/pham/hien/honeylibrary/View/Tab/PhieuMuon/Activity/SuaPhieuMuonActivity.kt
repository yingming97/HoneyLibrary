package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.*
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachThue
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachThueChiTiet
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.XacNhanDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SuaPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout

    private lateinit var layoutInfoPhieu: RelativeLayout
    private lateinit var imvAvatar: ImageView
    private lateinit var imv_edit_phieu: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvTongSach: TextView
    private lateinit var tvThanhTien: TextView
    private lateinit var tvNgayMuon: TextView
    private lateinit var tvHanTra: TextView
    private lateinit var tv_trang_thai: TextView
    private lateinit var tvXoaPhieu: TextView

    private lateinit var rcvListSachThue: RecyclerView

    private val formatter = SimpleDateFormat("EEE, d MMM y", Locale("vi"))
    private lateinit var mPhieuMuon: PhieuMuon
    private var mListSachThue = ArrayList<SachThue>()
    private lateinit var mSachThueAdapter: AdapterListSachThueChiTiet

    override fun getLayout(): Int {
        return R.layout.acitvity_sua_phieu_muon
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)

        layoutInfoPhieu = findViewById(R.id.layout_info_phieu)
        imvAvatar = findViewById(R.id.imv_avatar)
        imv_edit_phieu = findViewById(R.id.imv_edit_phieu)
        tvName = findViewById(R.id.tv_name)
        tvTongSach = findViewById(R.id.tv_tong_sach)
        tvThanhTien = findViewById(R.id.tv_thanh_tien)
        tvNgayMuon = findViewById(R.id.tv_ngay_muon)
        tvHanTra = findViewById(R.id.tv_han_tra)
        tv_trang_thai = findViewById(R.id.tv_trang_thai)
        tvXoaPhieu = findViewById(R.id.tv_xoa_phieu)

        rcvListSachThue = findViewById(R.id.list_sach_thue)

        ScreenUtils().setMarginStatusBar(this, toolBar)

        initRecycleViewSachThue()
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        tvXoaPhieu.setOnClickListener(this)
        imv_edit_phieu.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault() {
        val user = SharedPrefUtils.getUserData(this)!!
        loadView(user)
        mPhieuMuon = intent.getSerializableExtra(Constant.PHIEUMUON.PHIEUMUON) as PhieuMuon
        Glide.with(this).load(mPhieuMuon.photoDocGia).placeholder(R.drawable.ic_user_photo_default)
            .into(imvAvatar)
        tvName.text = mPhieuMuon.tenDocGia
        tvTongSach.text = "Số lượng : ${mPhieuMuon.soLuong}"
        tvThanhTien.text = "Thành tiền ${moneyFormatter(mPhieuMuon.tongTien)}"
        tvNgayMuon.text = "Ngày mượn : ${formatter.format(mPhieuMuon.ngayThue)}"
        tvHanTra.text = "Hạn trả : ${formatter.format(mPhieuMuon.hanTra)}"
        tv_trang_thai.text = "Trạng thái : ${mPhieuMuon.trangThai}"
        mListSachThue = convertStringToListSachThue(mPhieuMuon.listSachThue)
        mSachThueAdapter.setListSachThue(mListSachThue)
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> {
                finish()
            }
            imv_edit_phieu -> {

            }
            tvXoaPhieu -> {
                XacNhanDialog(this,
                    getString(R.string.ban_co_chac_chan_muon_xoa),
                    getString(R.string.du_lieu_khong_the_phuc_hoi_sau_khi_xoa)) {
                    if (mPhieuMuon.trangThai != Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
                        FailDialog(this,
                            getString(R.string.loi),
                            getString(R.string.chi_co_the_xoa_phieu_muon_da_tra)).show()
                    } else {
                        PhieuMuonDAO().deletePhieuMuon(this, mPhieuMuon)
                    }
                }.show()
            }
        }
    }

    private fun initRecycleViewSachThue() {
        mSachThueAdapter = AdapterListSachThueChiTiet(this, mListSachThue)
        rcvListSachThue.layoutManager = LinearLayoutManager(this)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = mSachThueAdapter
    }

    private fun loadView(user: UserModel) {
        when (user.type) {
            Constant.QUYEN.ADMIN, Constant.QUYEN.THU_THU -> {
                tvXoaPhieu.visibility = View.VISIBLE
                imv_edit_phieu.visibility = View.VISIBLE
            }
            Constant.QUYEN.KHACH -> {
                tvXoaPhieu.visibility = View.GONE
                imv_edit_phieu.visibility = View.GONE
            }
        }
    }
}