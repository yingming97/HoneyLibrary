package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Option.Adapter.AdapterTroGiup
import pham.hien.honeylibrary.ViewModel.TroGiupViewModel

class TroGiupActivity : BaseActivity() {

    private var listDocGia = ArrayList<UserModel>()
    private var listNhanVien = ArrayList<UserModel>()
    private lateinit var rcvListDocGia: RecyclerView
    private lateinit var troGiupViewModel: TroGiupViewModel
    private lateinit var troGiupAdapter: AdapterTroGiup

    override fun getLayout(): Int {
        return R.layout.activity_tro_giup
    }

    override fun initView() {
        rcvListDocGia = findViewById(R.id.rcv_list_user)
    }

    override fun initListener() {
        initRecyclerView()
    }

    override fun initViewModel() {
        troGiupViewModel = ViewModelProvider(this)[TroGiupViewModel::class.java]
    }

    override fun initObserver() {
        troGiupViewModel.mListDocGiaLiveData.observe(this){
            listNhanVien = it
            troGiupAdapter.setListQuanLy(it)
            Log.d("ccccccc", "initObserver: ${it.size}")
        }
    }

    override fun initDataDefault() {
        troGiupViewModel.getListDocGia()
    }

    fun initRecyclerView(){
        troGiupAdapter = AdapterTroGiup(this, listNhanVien){
            var intent: Intent = Intent(this,MessageActivity::class.java)
            var bundle: Bundle = Bundle()
            bundle.putSerializable("user", it)
            Log.d("cccc", "initRecyclerView: bundle: ${bundle}")
            intent.putExtras(bundle)
            startActivity(intent)
            Log.d("cccc", "initRecyclerView: ${it.toString()}")
        }
        rcvListDocGia.layoutManager = LinearLayoutManager(this)
        rcvListDocGia.setHasFixedSize(false)
        rcvListDocGia.isNestedScrollingEnabled = false
        rcvListDocGia.adapter =troGiupAdapter
    }

}