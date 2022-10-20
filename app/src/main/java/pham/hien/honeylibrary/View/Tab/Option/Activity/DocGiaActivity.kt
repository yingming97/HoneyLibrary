package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListUser
import pham.hien.honeylibrary.ViewModel.DocGiaViewModel

class DocGiaActivity : BaseActivity() {
    private var mIdUser: Int = 0
    private lateinit var toolBar: RelativeLayout
    private lateinit var imvAddDocGia: ImageView
//    private lateinit var tvName: TextView
//    private lateinit var tvSdt: TextView
//    private lateinit var avtDocGia: CircleImageView
    private lateinit var rcvListDocGia: RecyclerView
    private lateinit var mUserAdapter: AdapterListUser
    private lateinit var mListUser: ArrayList<UserModel>
    private lateinit var mDocGiaViewModel: DocGiaViewModel
    private lateinit var mDocGia: UserModel


    override fun getLayout(): Int {
        return R.layout.activity_doc_gia
    }

    override fun initView() {

        toolBar = findViewById(R.id.tool_bar)
        imvAddDocGia = findViewById(R.id.imv_add_doc_gia)
        rcvListDocGia = findViewById(R.id.rcv_list_doc_gia)
//
        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        imvAddDocGia.setOnClickListener(this)
        initRecycleViewDocGia()

    }

    override fun initViewModel() {
        mDocGiaViewModel = ViewModelProvider(this)[DocGiaViewModel::class.java]
    }

    override fun initObserver() {
        mDocGiaViewModel.mListDocGiaLiveData.observe(this) {
            mListUser = it
            mIdUser = mListUser.last().userId + 1
            var mListDocGia = ArrayList<UserModel>()
            for(user in it ){
                if (user.type == Constant.QUYEN.DOC_GIA){
                    mListDocGia.add(user)
                }
            }
            mUserAdapter.setListUser(mListDocGia)//
        }

    }

    override fun initDataDefault() {
        mDocGiaViewModel.getListDocGia()//Load list DocGia
    }

    private fun initRecycleViewDocGia() {
        mListUser = ArrayList()
        mUserAdapter = AdapterListUser(this, mListUser) { user ->
            // ở đây trả về user khi click vào item trên recycleview
            DialogSuaDocGia(this,user){
                mDocGiaViewModel.getListDocGia()
            }.show()
        }
        rcvListDocGia.layoutManager = LinearLayoutManager(this)
        rcvListDocGia.setHasFixedSize(false)
        rcvListDocGia.isNestedScrollingEnabled = false
        rcvListDocGia.adapter = mUserAdapter
    }

    override fun onClick(view: View?) {
        when(view){
            imvAddDocGia -> {
                var intent : Intent = Intent(this,AddDocGiaActivity::class.java)
                intent.putExtra("idUser",mIdUser)
                Log.d("zzz", mIdUser.toString())
                startActivity(intent)
            }
        }
    }
}