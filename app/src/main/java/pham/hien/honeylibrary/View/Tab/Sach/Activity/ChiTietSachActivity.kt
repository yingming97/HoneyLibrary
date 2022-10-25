package pham.hien.honeylibrary.View.Tab.Sach.Activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel
import pham.yingming.honeylibrary.Dialog.XacNhanThuHoiDialog

class ChiTietSachActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout
    private lateinit var imv_book: ImageView
    private lateinit var imv_change_photo: ImageView
    private lateinit var tv_gia_thue: TextView
    private lateinit var tv_gia_sach: TextView
    private lateinit var tv_so_luong: TextView
    private lateinit var tv_so_luong_con_lai: TextView
    private lateinit var tv_ten_sach: TextView
    private lateinit var tv_the_loai: TextView
    private lateinit var tv_content_gioi_thieu: TextView

    private lateinit var layout_thu_hoi: RelativeLayout
    private lateinit var layout_luu_hanh: RelativeLayout
    private lateinit var layout_chinh_sua: RelativeLayout

    private lateinit var mSach: Sach
    private var mListTheLoai = ArrayList<TheLoai>()
    private lateinit var mTheLoaiViewModel: TheLoaiViewModel

    override fun getLayout(): Int {
        return R.layout.acitvity_chi_tiet_sach
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)
        imv_book = findViewById(R.id.imv_book)
        tv_gia_thue = findViewById(R.id.tv_gia_thue)
        tv_gia_sach = findViewById(R.id.tv_gia_sach)
        tv_so_luong = findViewById(R.id.tv_so_luong)
        tv_so_luong_con_lai = findViewById(R.id.tv_so_luong_con_lai)
        tv_ten_sach = findViewById(R.id.tv_ten_sach)
        tv_the_loai = findViewById(R.id.tv_the_loai)
        tv_content_gioi_thieu = findViewById(R.id.tv_content_gioi_thieu)
        imv_change_photo = findViewById(R.id.imv_change_photo)
        layout_thu_hoi = findViewById(R.id.layout_thu_hoi)
        layout_luu_hanh = findViewById(R.id.layout_luu_hanh)
        layout_chinh_sua = findViewById(R.id.layout_chinh_sua)

        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        layout_chinh_sua.setOnClickListener(this)
        layout_thu_hoi.setOnClickListener(this)
        layout_luu_hanh.setOnClickListener(this)
    }

    override fun initViewModel() {
        mTheLoaiViewModel = ViewModelProvider(this)[TheLoaiViewModel::class.java]
    }

    override fun initObserver() {
        mSach = intent.getSerializableExtra(Constant.SACH.SACH) as Sach
        mTheLoaiViewModel.mListTheLoaiLiveData.observe(this) {
            mListTheLoai = it
            getTenTheLoai(it) { tenTheLoai ->
                tv_the_loai.text = tenTheLoai
            }
        }
    }

    override fun initDataDefault() {
        mTheLoaiViewModel.getListTheLoai()
        val user = SharedPrefUtils.getUserData(this)!!
        loadDataSach(user)

    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> finish()
            layout_chinh_sua -> {
                val intent = Intent(this, SuaSachActivity::class.java)
                intent.putExtra(Constant.SACH.SACH, mSach)
                startActivity(intent)
                finish()
            }
            layout_thu_hoi -> {
                mSach.thuHoi = true
                XacNhanThuHoiDialog(this,
                    "Bạn có muốn thu hồi sách này ?",
                    "Sách thu hổi sẽ không được hiển thị với độc giả !!") {
                    SachDAO().updateThuHoiSach(mSach)
                    layout_luu_hanh.visibility = View.VISIBLE
                    layout_thu_hoi.visibility = View.GONE
                }.show()
            }
            layout_luu_hanh -> {
                mSach.thuHoi = false
                XacNhanThuHoiDialog(this,
                    "Bạn có muốn lưu hành sách này ?",
                    "Sách lưu hành sẽ được hiển thị với độc giả !!") {
                    SachDAO().updateThuHoiSach(mSach)
                    layout_luu_hanh.visibility = View.GONE
                    layout_thu_hoi.visibility = View.VISIBLE
                }.show()
            }
        }
    }

    private fun loadDataSach(user: UserModel) {
        when (user.type) {
            Constant.QUYEN.ADMIN, Constant.QUYEN.THU_THU -> {
                layout_thu_hoi.visibility = View.VISIBLE
                layout_chinh_sua.visibility = View.VISIBLE
                layout_luu_hanh.visibility = View.VISIBLE
                if (!mSach.thuHoi) {
                    layout_thu_hoi.visibility = View.VISIBLE
                    layout_luu_hanh.visibility = View.GONE
                } else {
                    layout_luu_hanh.visibility = View.VISIBLE
                    layout_thu_hoi.visibility = View.GONE
                }
            }
            else -> {
                layout_thu_hoi.visibility = View.GONE
                layout_chinh_sua.visibility = View.GONE
                layout_luu_hanh.visibility = View.GONE
            }
        }
        Glide.with(this).load(mSach.anhBia).placeholder(R.drawable.img_sach_add_default)
            .into(imv_book)
        tv_gia_sach.text = mSach.giaSach.toString()
        tv_ten_sach.text = mSach.tenSach
        tv_so_luong.text = mSach.soLuong.toString()
        tv_gia_thue.text = mSach.giaThue.toString()
        tv_so_luong_con_lai.text = mSach.soLuongConLai.toString()
        tv_content_gioi_thieu.text = mSach.gioiThieu
    }

    private fun getTenTheLoai(listTheLoai: ArrayList<TheLoai>, tenTheLoai: (String) -> Unit) {
        for (theLoai in listTheLoai) {
            if (mSach.maLoai == theLoai.maTheLoai) {
                tenTheLoai(theLoai.tenTheLoai)
            }
        }
    }
}