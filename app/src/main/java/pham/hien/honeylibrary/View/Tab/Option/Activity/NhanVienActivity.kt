package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Option.Adapter.AdapterListQuanLy
import pham.hien.honeylibrary.ViewModel.NhanVienViewModel

class NhanVienActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var imgThemNv: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: NhanVienViewModel
    private lateinit var back: ImageView
    private var quanLyList = ArrayList<UserModel>()
    private lateinit var mUserAdapter: AdapterListQuanLy

    override fun getLayout(): Int {
        return R.layout.activity_nhan_vien
    }

    override fun initView() {
        imgThemNv = findViewById(R.id.image_ThemNhanVien)
        recyclerView = findViewById(R.id.recyclerNhanVien)
        back = findViewById(R.id.image_Back)
    }

    override fun initListener() {
        imgThemNv.setOnClickListener(this)
        back.setOnClickListener(this)
        recyclerViewNhanVien()
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this)[NhanVienViewModel::class.java]
    }

    override fun initObserver() {
        mViewModel.nhanvienModel.observe(this) {
            mUserAdapter.setListQuanLy(it)
            quanLyList = it
            Log.d(TAG, "lít" + it.size)

        }
    }

    override fun initDataDefault() {
        mViewModel.getListNhanVien()
        Log.e("tuvm", "lít" + quanLyList.size)
    }

    private fun recyclerViewNhanVien() {
        mUserAdapter = AdapterListQuanLy(applicationContext, quanLyList) {
            var intent = Intent(applicationContext, ChiTietNhanVienActivity::class.java)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = mUserAdapter
    }

    override fun onClick(view: View?) {
        imgThemNv.setOnClickListener {
            startActivity(Intent(applicationContext, ThemNhanVienActivity::class.java))
        }
        back.setOnClickListener {
            Log.e("tuvm", "onclick")
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getListNhanVien()
    }
}