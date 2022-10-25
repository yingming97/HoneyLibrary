package pham.hien.honeylibrary.View.Tab.Option.Activity.DocGia

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListUser
import pham.hien.honeylibrary.ViewModel.DocGiaViewModel

class DocGiaActivity : BaseActivity() {
    private lateinit var toolBar: RelativeLayout
    private lateinit var imvAddDocGia: ImageView
    private lateinit var imvBack: ImageView
    private lateinit var rcvListDocGia: RecyclerView
    private lateinit var mUserAdapter: AdapterListUser
    private lateinit var mUserModel: DocGiaViewModel
    private lateinit var edSearch: EditText
    private lateinit var mArrDocGia: ArrayList<UserModel>
    private lateinit var imvSearch: ImageView
    private lateinit var imvEmpty: ImageView
    private lateinit var tvNoData: TextView


    override fun getLayout(): Int {
        return R.layout.activity_doc_gia
    }

    override fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        imvAddDocGia = findViewById(R.id.imv_add_doc_gia)
        imvBack = findViewById(R.id.imv_back)
        rcvListDocGia = findViewById(R.id.rcv_list_doc_gia)
        edSearch = findViewById(R.id.ed_search_doc_gia)
        imvSearch = findViewById(R.id.imv_search)
        imvEmpty = findViewById(R.id.imv_empty)
        tvNoData = findViewById(R.id.tv_no_data)
        ScreenUtils().setMarginStatusBar(this, toolBar)
        initSearchNhanVien()
    }

    override fun initListener() {
        imvAddDocGia.setOnClickListener(this)
        imvBack.setOnClickListener(this)
    }

    override fun initViewModel() {
        mUserModel = ViewModelProvider(this)[DocGiaViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver() {
        mUserModel.mListDocGiaLiveData.observe(this) {
            mArrDocGia = it
            mUserAdapter = AdapterListUser(this, mArrDocGia) { its ->
                val intent = Intent(this, ChiTietDocGiaActivity::class.java)
                intent.putExtra(Constant.USER.USER, its)
                startActivity(intent)
            }
            rcvListDocGia.adapter = mUserAdapter
            mUserAdapter.notifyDataSetChanged()
        }

    }

    override fun initDataDefault() {
        mUserModel.getListDocGia()
    }

    override fun onClick(view: View?) {
        when (view) {
            imvAddDocGia -> {
                val intent = Intent(this, AddDocGiaActivity::class.java)
                startActivity(intent)
            }
            imvBack -> {
                onBackPressed()
            }
        }
    }

    private fun initSearchNhanVien() {
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                val idNhanvien = try {
                    Integer.parseInt(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val listDocGia = ArrayList<UserModel>()
                for (docGia in mArrDocGia) {
                    if (docGia.userId == idNhanvien || docGia.name.contains(str, true) ||
                        docGia.email.contains(str, true) || docGia.sdt.contains(str, true)
                    ) {
                        listDocGia.add(docGia)
                    }
                    if (s.isNullOrEmpty()) {
                        imvSearch.visibility = View.VISIBLE
                        imvEmpty.visibility = View.GONE
                        mUserAdapter.setListDocgia(listDocGia)
                    } else {
                        imvSearch.visibility = View.GONE
                        imvEmpty.visibility = View.VISIBLE
                        mUserAdapter.setListDocgia(listDocGia)
                        if (listDocGia.isEmpty()) {
                            tvNoData.visibility = View.VISIBLE
                        } else {
                            tvNoData.visibility = View.GONE
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mUserModel.getListDocGia()
    }
}