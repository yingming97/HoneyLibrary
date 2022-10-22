package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Option.Adapter.AdapterListQuanLy
import pham.hien.honeylibrary.ViewModel.NhanVienViewModel

class NhanVienActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var toolBar: RelativeLayout
    private lateinit var edSearchNhanVien: EditText
    private lateinit var imvClose: ImageView
    private lateinit var imvSearch: ImageView
    private lateinit var imvEmpty: ImageView
    private lateinit var imvAddNewNhanVien: ImageView
    private lateinit var ncvQuanLyNhanVien: NestedScrollView
    private lateinit var rcvListNhanVien: RecyclerView
    private lateinit var tvNoData: TextView

    private var mListNhanVien = ArrayList<UserModel>()
    private lateinit var mUserAdapter: AdapterListQuanLy
    private lateinit var mNhanVienViewModel: NhanVienViewModel

    override fun getLayout(): Int {
        return R.layout.activity_nhan_vien
    }

    override fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        edSearchNhanVien = findViewById(R.id.ed_search_nhan_vien)
        imvClose = findViewById(R.id.imv_close)
        imvSearch = findViewById(R.id.imv_search)
        imvEmpty = findViewById(R.id.imv_empty)
        imvAddNewNhanVien = findViewById(R.id.imv_add_new_nhan_vien)
        ncvQuanLyNhanVien = findViewById(R.id.ncv_quan_ly_nhan_vien)
        rcvListNhanVien = findViewById(R.id.rcv_list_nhan_vien)
        tvNoData = findViewById(R.id.tv_no_data)

        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvAddNewNhanVien.setOnClickListener(this)
        imvEmpty.setOnClickListener(this)
        recyclerViewNhanVien()
    }

    override fun initViewModel() {
        mNhanVienViewModel = ViewModelProvider(this)[NhanVienViewModel::class.java]
    }

    override fun initObserver() {
        mNhanVienViewModel.nhanvienModel.observe(this) {
            mUserAdapter.setListQuanLy(it)
            mListNhanVien = it
        }
    }

    override fun initDataDefault() {
        mNhanVienViewModel.getListNhanVien()
    }

    override fun onClick(view: View?) {
        when (view) {
            imvAddNewNhanVien -> {
                startActivity(Intent(this, ThemNhanVienActivity::class.java))

            }
            imvClose -> {
                finish()
            }
        }
    }

    private fun recyclerViewNhanVien() {
        mUserAdapter = AdapterListQuanLy(this, mListNhanVien) {
            val intent = Intent(applicationContext, ChiTietNhanVienActivity::class.java)
            intent.putExtra(Constant.USER.USER, it)
            startActivity(intent)
        }
        rcvListNhanVien.layoutManager = LinearLayoutManager(this)
        rcvListNhanVien.setHasFixedSize(false)
        rcvListNhanVien.isNestedScrollingEnabled = false
        rcvListNhanVien.adapter = mUserAdapter
    }

    override fun onResume() {
        super.onResume()
        mNhanVienViewModel.getListNhanVien()
    }
}