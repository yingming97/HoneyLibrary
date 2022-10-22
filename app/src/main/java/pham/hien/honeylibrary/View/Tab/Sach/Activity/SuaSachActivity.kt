package pham.hien.honeylibrary.View.Tab.Sach.Activity

import android.content.Intent
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListTheLoaiAddSach
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel

class SuaSachActivity : BaseActivity() {

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
    private lateinit var layout_luu: LinearLayout
    private lateinit var rcv_list_the_loai: RecyclerView
    private lateinit var nsv_list_the_loai: NestedScrollView

    private var isShowListTheLoai = false
    private lateinit var mSach: Sach
    private var mListAllSach = ArrayList<Sach>()
    private var mListTheLoai = ArrayList<TheLoai>()
    private lateinit var mAdapterTheLoai: AdapterListTheLoaiAddSach

    private lateinit var mSachViewModel: SachViewModel
    private lateinit var mTheLoaiViewModel: TheLoaiViewModel

    override fun getLayout(): Int {
        return R.layout.acitvity_sua_sach
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
        imv_change_photo = findViewById(R.id.imv_change_photo)
        layout_luu = findViewById(R.id.layout_luu)
        nsv_list_the_loai = findViewById(R.id.nsv_list_the_loai)
        rcv_list_the_loai = findViewById(R.id.rcv_list_the_loai)

        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvClose.setOnClickListener(this)
        imv_book.setOnClickListener(this)
        imv_change_photo.setOnClickListener(this)
        imv_show_the_loai.setOnClickListener(this)
        layout_the_loai.setOnClickListener(this)
        layout_luu.setOnClickListener(this)
    }

    override fun initViewModel() {
        mSachViewModel = ViewModelProvider(this)[SachViewModel::class.java]
        mTheLoaiViewModel = ViewModelProvider(this)[TheLoaiViewModel::class.java]
    }

    override fun initObserver() {
        mSach = intent.getSerializableExtra(Constant.SACH.SACH) as Sach
        mSachViewModel.mListAllSachLiveData.observe(this) {
            mListAllSach = it
        }
        mTheLoaiViewModel.mListTheLoaiLiveData.observe(this) {
            mListTheLoai = it
            mAdapterTheLoai.setList(it)
            getTenTheLoai(it) { tenTheLoai ->
                tv_the_loai.text = tenTheLoai
            }
        }
    }

    override fun initDataDefault() {
        mSachViewModel.getListAllSach()
        mTheLoaiViewModel.getListTheLoai()
        initRecycleViewTheLoai()
        loadDataSach()
        rcv_list_the_loai.visibility = View.GONE
        nsv_list_the_loai.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view) {
            imvClose -> {
                finish()
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
            layout_luu -> {
                val intent = Intent(this, ChiTietSachActivity::class.java)
                intent.putExtra(Constant.SACH.SACH, mSach)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getTenTheLoai(listTheLoai: ArrayList<TheLoai>, tenTheLoai: (String) -> Unit) {
        for (theLoai in listTheLoai) {
            if (mSach.maLoai == theLoai.maTheLoai) {
                tenTheLoai(theLoai.tenTheLoai)
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

    private fun loadDataSach() {
        Glide.with(this).load(mSach.anhBia).placeholder(R.drawable.img_sach_add_default)
            .into(imv_book)
        ed_gia_sach.setText(mSach.giaSach.toString())
        ed_gia_thue.setText(mSach.giaThue.toString())
        ed_so_luong.setText(mSach.soLuong.toString())
        ed_ten_sach.setText(mSach.tenSach)
        ed_gioi_thieu.setText(mSach.gioiThieu)
    }
}