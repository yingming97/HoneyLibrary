package pham.hien.honeylibrary.View.Tab.Sach.Activity

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.graphics.*
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.Storage.Images
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ImagesUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.URIPathHelper
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListTheLoaiAddSach
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.io.IOException


class AddSachActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imvClose: ImageView
    private lateinit var toolBar: RelativeLayout
    private lateinit var imv_book: ImageView
    private lateinit var ed_gia_thue: EditText
    private lateinit var ed_gia_sach: EditText
    private lateinit var ed_so_luong: EditText
    private lateinit var ed_ten_sach: EditText
    private lateinit var ed_gioi_thieu: EditText
    private lateinit var tv_the_loai: TextView
    private lateinit var imv_show_the_loai: ImageView
    private lateinit var imv_change_photo: ImageView
    private lateinit var layout_the_loai: RelativeLayout
    private lateinit var tv_them_sach: TextView
    private lateinit var rcv_list_the_loai: RecyclerView
    private lateinit var nsv_list_the_loai: NestedScrollView

    private lateinit var mProgressBar: ProgressBarLoading
    private var isShowListTheLoai = false
    private lateinit var mSach: Sach
    private var mListAllSach = ArrayList<Sach>()
    private var mListTheLoai = ArrayList<TheLoai>()
    private lateinit var mAdapterTheLoai: AdapterListTheLoaiAddSach

    private lateinit var mSachViewModel: SachViewModel
    private lateinit var mTheLoaiViewModel: TheLoaiViewModel

    override fun getLayout(): Int {
        return R.layout.acitvity_add_sach
    }

    override fun initView() {
        imvClose = findViewById(R.id.imv_close)
        toolBar = findViewById(R.id.tool_bar)
        imv_book = findViewById(R.id.imv_book)
        ed_gia_thue = findViewById(R.id.ed_gia_thue)
        ed_gia_sach = findViewById(R.id.ed_gia_sach)
        ed_so_luong = findViewById(R.id.ed_so_luong)
        ed_ten_sach = findViewById(R.id.ed_ten_sach)
        ed_gioi_thieu = findViewById(R.id.ed_gioi_thieu)
        tv_the_loai = findViewById(R.id.tv_the_loai)
        imv_show_the_loai = findViewById(R.id.imv_show_the_loai)
        layout_the_loai = findViewById(R.id.layout_the_loai)
        tv_them_sach = findViewById(R.id.tv_them_sach)
        imv_change_photo = findViewById(R.id.imv_change_photo)
        rcv_list_the_loai = findViewById(R.id.rcv_list_the_loai)
        nsv_list_the_loai = findViewById(R.id.nsv_list_the_loai)

        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        imv_book.setOnClickListener(this)
        imv_change_photo.setOnClickListener(this)
        imv_show_the_loai.setOnClickListener(this)
        layout_the_loai.setOnClickListener(this)
        tv_them_sach.setOnClickListener(this)
    }

    override fun initViewModel() {
        mSachViewModel = ViewModelProvider(this)[SachViewModel::class.java]
        mTheLoaiViewModel = ViewModelProvider(this)[TheLoaiViewModel::class.java]
    }

    override fun initObserver() {
        mSachViewModel.mListAllSachLiveData.observe(this) {
            mListAllSach = it
        }
        mTheLoaiViewModel.mListTheLoaiLiveData.observe(this) {
            mListTheLoai = it
            mAdapterTheLoai.setList(it)
        }
    }

    override fun initDataDefault() {
        mSach = Sach()
        mSachViewModel.getListAllSach()
        mTheLoaiViewModel.getListTheLoai()
        mProgressBar = ProgressBarLoading(this)
        Glide.with(this).load(R.drawable.img_sach_add_default).into(imv_book)
        initRecycleViewTheLoai()
        rcv_list_the_loai.visibility = View.GONE
        nsv_list_the_loai.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> finish()
            imv_book, imv_change_photo -> {
                ImagesUtils().checkPermissionChonAnh(this, imv_book)
            }
            layout_the_loai -> {
                isShowListTheLoai = true
                rcv_list_the_loai.visibility = View.VISIBLE
                nsv_list_the_loai.visibility = View.VISIBLE
                imv_show_the_loai.rotation = -90f
            }
            imv_show_the_loai -> {
                if (!isShowListTheLoai) {
                    isShowListTheLoai = true
                    rcv_list_the_loai.visibility = View.VISIBLE
                    nsv_list_the_loai.visibility = View.VISIBLE
                    imv_show_the_loai.rotation = -90f
                } else {
                    isShowListTheLoai = false
                    rcv_list_the_loai.visibility = View.GONE
                    nsv_list_the_loai.visibility = View.GONE
                    imv_show_the_loai.rotation = 90f
                }
            }
            tv_them_sach -> {
                mProgressBar.showLoading()
                checkSachValidate {
                    if (it) {
                        addNewSach()
                    }
                }
            }
        }
    }

    private fun addNewSach() {
        mSach.maSach = mListAllSach.last().maSach + 1
        mSach.soLuong = ed_so_luong.text.toString().toInt()
        mSach.soLuongConLai = ed_so_luong.text.toString().toInt()
        mSach.tenSach = ed_ten_sach.text.toString()
        mSach.giaSach = ed_gia_sach.text.toString().toInt()
        mSach.giaThue = ed_gia_thue.text.toString().toInt()
        mSach.gioiThieu = ed_gioi_thieu.text.toString()

        Images().uploadImage(imv_book, Constant.SACH.TB_NAME, mSach.maSach.toString()) {
            mSach.anhBia = it
            SachDAO().addSach(this, mSach) { addDone ->
                if (addDone) {
                    mProgressBar.hideLoading()
                    mSach = Sach()
                    Glide.with(this).load(R.drawable.img_sach_add_default).into(imv_book)
                    ed_gia_sach.setText("")
                    ed_gia_thue.setText("")
                    ed_so_luong.setText("")
                    ed_ten_sach.setText("")
                    ed_gioi_thieu.setText("")
                    mSachViewModel.getListAllSach()
                }
            }
        }
    }

    private fun initRecycleViewTheLoai() {
        mAdapterTheLoai = AdapterListTheLoaiAddSach(this, mListTheLoai) {
            tv_the_loai.text = it.tenTheLoai
            mSach.maLoai = it.maTheLoai
            nsv_list_the_loai.visibility = View.GONE
            imv_show_the_loai.rotation = 90f
        }
        rcv_list_the_loai.layoutManager = LinearLayoutManager(this)
        rcv_list_the_loai.setHasFixedSize(false)
        rcv_list_the_loai.isNestedScrollingEnabled = false
        rcv_list_the_loai.adapter = mAdapterTheLoai
    }

    private fun checkSachValidate(checkDone: (Boolean) -> Unit) {
        var strError = ""
        if (ed_gia_sach.text.toString().isEmpty()) {
            strError += "Bạn chưa thêm giá sách.\n"
        } else if (ed_gia_sach.text.toString().toInt() <= 0) {
            strError += "Giá sách phải lớn hơn 0.\n"
        }
        if (ed_ten_sach.text.toString().isEmpty()) {
            strError += "Bạn chưa thêm tên sách.\n"
        } else {
            for (sach in mListAllSach) {
                if (ed_ten_sach.text.toString().equals(sach.tenSach, true)) {
                    strError += "Tên sách đã có trong dữ liệu.\n"
                }
            }
        }
        if (ed_gia_thue.text.toString().isEmpty()) {
            strError += "Bạn chưa thêm giá thuê.\n"
        } else if (ed_gia_thue.text.toString().toInt() <= 0) {
            strError += "Giá thuê phải lớn hơn 0.\n"
        }
        if (ed_so_luong.text.toString().isEmpty()) {
            strError += "Số lượng sách không được bỏ trống.\n"
        } else if (ed_so_luong.text.toString().toInt() <= 0) {
            strError += "Số lượng sách phải lớn hơn 0"
        }
        if (tv_the_loai.length() == 0) {
            strError += "Bạn chưa chọn thể loại.\n"
        }
        if (strError.isEmpty()) {
            checkDone(true)
        } else {
            mProgressBar.hideLoading()
            checkDone(false)
            FailDialog(this, "Lỗi", strError).show()
        }
    }

}