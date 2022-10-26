package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.util.TimeUtils
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
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.*
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachThueChiTiet
import pham.hien.honeylibrary.View.Tab.PhieuMuon.CustomView.SachTraThieuView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.XacNhanTraSachDialog
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Dialog.XacNhanXoaPhieuDialog
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import pham.yingming.honeylibrary.Dialog.XacNhanDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChiTietPhieuMuonActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout

    private lateinit var layoutInfoPhieu: RelativeLayout
    private lateinit var layout_trang_thai: RelativeLayout
    private lateinit var imvAvatar: ImageView
    private lateinit var imv_edit_phieu: ImageView
    private lateinit var imv_delete_phieu: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvTongSach: TextView
    private lateinit var tvThanhTien: TextView
    private lateinit var tv_tien_phat: TextView
    private lateinit var tvNgayMuon: TextView
    private lateinit var tvHanTra: TextView
    private lateinit var tv_trang_thai: TextView
    private lateinit var tv_da_tra: TextView
    private lateinit var switch_tra_sach: SwitchCompat

    private lateinit var rcvListSachThue: RecyclerView
    private lateinit var ctv_sach_tra_thieu: SachTraThieuView

    private val formatter = SimpleDateFormat("dd/MM/yy", Locale("vi"))
    private var tienPhat = 0
    private lateinit var mPhieuMuon: PhieuMuon
    private var mListSachThue = ArrayList<SachThue>()
    private var mListSach = ArrayList<Sach>()
    private lateinit var mSachThueAdapter: AdapterListSachThueChiTiet

    private lateinit var mSachViewModel: SachViewModel

    override fun getLayout(): Int {
        return R.layout.acitvity_chi_tiet_phieu_muon
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)

        layoutInfoPhieu = findViewById(R.id.layout_info_phieu)
        layout_trang_thai = findViewById(R.id.layout_trang_thai)
        imvAvatar = findViewById(R.id.imv_avatar)
        imv_edit_phieu = findViewById(R.id.imv_edit_phieu)
        tvName = findViewById(R.id.tv_name)
        tvTongSach = findViewById(R.id.tv_tong_sach)
        tvThanhTien = findViewById(R.id.tv_thanh_tien)
        tv_tien_phat = findViewById(R.id.tv_tien_phat)
        tvNgayMuon = findViewById(R.id.tv_ngay_muon)
        tvHanTra = findViewById(R.id.tv_han_tra)
        tv_trang_thai = findViewById(R.id.tv_trang_thai)
        imv_delete_phieu = findViewById(R.id.imv_delete_phieu)
        switch_tra_sach = findViewById(R.id.switch_tra_sach)
        tv_da_tra = findViewById(R.id.tv_da_tra)

        rcvListSachThue = findViewById(R.id.list_sach_thue)
        ctv_sach_tra_thieu = findViewById(R.id.ctv_sach_tra_thieu)

        ScreenUtils().setMarginStatusBar(this, toolBar)

        initRecycleViewSachThue()
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        imv_delete_phieu.setOnClickListener(this)
        imv_edit_phieu.setOnClickListener(this)
        switch_tra_sach.setOnCheckedChangeListener(this)
        ctv_sach_tra_thieu.setListener(object : SachTraThieuView.SaveListSachTraThieu {
            override fun onSave(map: MutableMap<Int, Int>) {
                map.forEach {
                    for (sach in mListSach) {
                        if (sach.maSach == it.key) {
                            sach.soLuong = it.value
                            SachDAO().addSachThieu(sach)
                            tienPhat += sach.giaSach * it.value
                            tv_tien_phat.text = moneyFormatter(tienPhat)
                            tv_tien_phat.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    override fun initViewModel() {
        mSachViewModel = ViewModelProvider(this)[SachViewModel::class.java]
    }

    override fun initObserver() {
        mSachViewModel.mListAllSachLiveData.observe(this) {
            mListSach = it
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault() {
        mSachViewModel.getListAllSach()
        val user = SharedPrefUtils.getUserData(this)!!
        loadView(user)
        val calender = Calendar.getInstance().timeInMillis
        mPhieuMuon = intent.getSerializableExtra(Constant.PHIEUMUON.PHIEUMUON) as PhieuMuon
        Glide.with(this).load(mPhieuMuon.photoDocGia).placeholder(R.drawable.ic_user_photo_default)
            .into(imvAvatar)
        tvName.text = mPhieuMuon.tenDocGia
        tvTongSach.text = "Số lượng:\n0${mPhieuMuon.soLuong}"
        tvThanhTien.text = "Thành tiền: ${moneyFormatter(mPhieuMuon.tongTien)}"
        tvNgayMuon.text = "Ngày mượn:\n${formatter.format(mPhieuMuon.ngayThue)}"
        tvHanTra.text = "Hạn trả:\n${formatter.format(mPhieuMuon.hanTra)}"
        if (!CheckTimeUtils.isToday(mPhieuMuon.hanTra) && mPhieuMuon.hanTra < calender || mPhieuMuon.trangThai == Constant.PHIEUMUON.TRANGTHAI.QUA_HAN) {
            tv_trang_thai.text = Constant.PHIEUMUON.TRANGTHAI.QUA_HAN
            mPhieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.QUA_HAN
            PhieuMuonDAO().updateTrangThaiPhieuMuon(mPhieuMuon,
                updateDone = {
                },
                updateNotDone = {
                })
            tienPhat = ((calender - mPhieuMuon.hanTra) / DateUtils.DAY_IN_MILLIS).toInt() * 1000
            tv_tien_phat.text = "Tiền phạt quá hạn: ${moneyFormatter(tienPhat)}"
            tv_tien_phat.visibility = View.VISIBLE
        } else {
            tv_tien_phat.visibility = View.GONE
            tv_trang_thai.text = mPhieuMuon.trangThai
        }
        switch_tra_sach.isChecked = mPhieuMuon.trangThai == Constant.PHIEUMUON.TRANGTHAI.DA_TRA
        mListSachThue = convertStringToListSachThue(mPhieuMuon.listSachThue)
        mSachThueAdapter.setListSachThue(mListSachThue)
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> {
                finish()
            }
            imv_edit_phieu -> {
                val intent = Intent(this, SuaPhieuMuonActivity::class.java)
                intent.putExtra(Constant.PHIEUMUON.PHIEUMUON, mPhieuMuon)
                startActivity(intent)
                finish()
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
                imv_delete_phieu.visibility = View.VISIBLE
                imv_edit_phieu.visibility = View.VISIBLE
                layout_trang_thai.visibility = View.VISIBLE
            }
            Constant.QUYEN.DOC_GIA -> {
                layout_trang_thai.visibility = View.GONE
                imv_delete_phieu.visibility = View.GONE
                imv_edit_phieu.visibility = View.GONE
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if (p1 && mPhieuMuon.trangThai != Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
            switch_tra_sach.isChecked = p1
            XacNhanTraSachDialog(this,
                traThieu = {
//                    switch_tra_sach.isChecked = true
                    BaseView.openViewGroup(ctv_sach_tra_thieu, 400)
                    ctv_sach_tra_thieu.setListSachThue(mListSachThue)
                },
                traDu = {
                    mPhieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.DA_TRA
                    tv_trang_thai.text = mPhieuMuon.trangThai
                    PhieuMuonDAO().updateTrangThaiPhieuMuon(mPhieuMuon,
                        updateDone = {
                            switch_tra_sach.isChecked = true
                            Toast.makeText(this, "Đã cập nhập thành công", Toast.LENGTH_LONG).show()
                        },
                        updateNotDone = {
                            Toast.makeText(this, "Cập nhập không thành công", Toast.LENGTH_LONG)
                                .show()
                        })
                },
                huy = {
                    switch_tra_sach.isChecked = false
                    tv_trang_thai.text = mPhieuMuon.trangThai
                }
            ).show()
        } else if (!p1) {
            XacNhanDialog(this,
                "Hủy trả phiếu",
                "Bạn muốn chuyển phiếu sang trạng thái đang mượn ?",
                dongY = {
                    switch_tra_sach.isChecked = false
                    mPhieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.DANG_MUON
                    tv_trang_thai.text = mPhieuMuon.trangThai
                    PhieuMuonDAO().updateTrangThaiPhieuMuon(mPhieuMuon,
                        updateDone = {
                            Toast.makeText(this, "Đã cập nhập thành công", Toast.LENGTH_LONG).show()
                        },
                        updateNotDone = {
                            Toast.makeText(this, "Cập nhập không thành công", Toast.LENGTH_LONG)
                                .show()
                        })
                }, huy = {
                    tv_trang_thai.text = mPhieuMuon.trangThai
                    switch_tra_sach.isChecked = true
                }).show()
        }
    }
}