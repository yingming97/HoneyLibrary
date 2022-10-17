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

    private lateinit var imgThemNv: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: NhanVienViewModel
    private var quanLyList = ArrayList<UserModel>()
    private lateinit var mUser: AdapterListQuanLy

    override fun getLayout(): Int {
        return R.layout.activity_nhan_vien
    }

    override fun initView() {
        imgThemNv = findViewById(R.id.image_ThemNhanVien)
        recyclerView = findViewById(R.id.recyclerNhanVien)
    }

    override fun initListener() {
        imgThemNv.setOnClickListener(this)
        recyclerViewNhanVien()
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this)[NhanVienViewModel::class.java]
    }

    override fun initObserver() {
        mViewModel.nhanvienModel.observe(this) {
            mUser.setListQuanLy(it)
            quanLyList = it
        }
    }

    override fun initDataDefault() {
        mViewModel.getAll()
        Log.e("tuvm", "lít" + quanLyList.size)
    }

    private fun recyclerViewNhanVien() {
        mUser = AdapterListQuanLy(this, quanLyList) {

        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = mUser
    }

    override fun onClick(view: View?) {
        imgThemNv.setOnClickListener {
            startActivity(Intent(applicationContext, ThemNhanVienActivity::class.java))
        }
    }
}