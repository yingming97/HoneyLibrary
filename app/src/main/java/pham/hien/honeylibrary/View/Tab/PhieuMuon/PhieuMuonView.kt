package pham.hien.honeylibrary.View.Tab.PhieuMuon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yomaster.yogaforbeginner.View.Extention.CheckTimeUtils
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Login.LoginActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity.AddPhieuMuonActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity.ChiTietPhieuMuonActivity
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListPhieuDaTra
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListPhieuDangMuon
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListPhieuQuaHan
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class PhieuMuonView : BaseView {

    private val TAG = "YingMing"
    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var imv_add_new_phieu_muon: ImageView
    private lateinit var tv_title: TextView
    private lateinit var imv_search: ImageView
    private lateinit var imv_empty: ImageView
    private lateinit var ed_search_phieu_muon: EditText
    private lateinit var layout_phieu_muon: RelativeLayout
    private lateinit var layout_no_sign_in: RelativeLayout
    private lateinit var btn_dang_nhap: TextView

    private lateinit var tv_no_data_qua_han: TextView
    private lateinit var tv_no_data_dang_muon: TextView
    private lateinit var tv_no_data_da_tra: TextView
    private lateinit var tv_no_data: TextView

    private lateinit var rcv_list_qua_han: RecyclerView
    private lateinit var rcv_list_dang_muon: RecyclerView
    private lateinit var rcv_list_da_tra: RecyclerView
    private lateinit var pg_load_phieu_qua_han: ProgressBar
    private lateinit var pg_load_phieu_dang_muon: ProgressBar
    private lateinit var pg_load_phieu_da_tra: ProgressBar

    private lateinit var mUser: UserModel
    private lateinit var mPhieuQuaHanAdapter: AdapterListPhieuQuaHan
    private lateinit var mPhieuDangMuonAdapter: AdapterListPhieuDangMuon
    private lateinit var mPhieuDaTraAdapter: AdapterListPhieuDaTra
    private var mListPhieuMuon = ArrayList<PhieuMuon>()
    private var mListPhieuMuonQuaHan = ArrayList<PhieuMuon>()
    private var mListPhieuMuonDangMuon = ArrayList<PhieuMuon>()
    private var mListPhieuMuonDaTra = ArrayList<PhieuMuon>()

    private lateinit var mPhieuMuonViewModel: PhieuMuonViewModel

    constructor(context: Context?) : super(context) {
        if (context != null) {
            mContext = context
            initView(context, null)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        if (context != null) {
            mContext = context
            initView(context, attrs)
        }
    }

    override fun initView(context: Context?, attrs: AttributeSet?) {
        super.initView(context, attrs)
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = inflater.inflate(R.layout.view_phieu_muon, this)

        tool_bar = rootView.findViewById(R.id.tool_bar)
        tv_title = rootView.findViewById(R.id.tv_title)
        imv_add_new_phieu_muon = rootView.findViewById(R.id.imv_add_new_phieu_muon)
        imv_search = rootView.findViewById(R.id.imv_search)
        imv_empty = rootView.findViewById(R.id.imv_empty)
        ed_search_phieu_muon = rootView.findViewById(R.id.ed_search_phieu_muon)
        layout_no_sign_in = rootView.findViewById(R.id.layout_no_sign_in)
        btn_dang_nhap = rootView.findViewById(R.id.btn_dang_nhap)
        tv_no_data = rootView.findViewById(R.id.tv_no_data)

        tv_no_data_qua_han = rootView.findViewById(R.id.tv_no_data_qua_han)
        tv_no_data_dang_muon = rootView.findViewById(R.id.tv_no_data_dang_muon)
        tv_no_data_da_tra = rootView.findViewById(R.id.tv_no_data_da_tra)

        layout_phieu_muon = rootView.findViewById(R.id.layout_phieu_muon)
        rcv_list_qua_han = rootView.findViewById(R.id.rcv_list_qua_han)
        rcv_list_dang_muon = rootView.findViewById(R.id.rcv_list_dang_muon)
        rcv_list_da_tra = rootView.findViewById(R.id.rcv_list_da_tra)

        pg_load_phieu_qua_han = rootView.findViewById(R.id.pg_load_phieu_qua_han)
        pg_load_phieu_dang_muon = rootView.findViewById(R.id.pg_load_phieu_dang_muon)
        pg_load_phieu_da_tra = rootView.findViewById(R.id.pg_load_phieu_da_tra)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

        imv_add_new_phieu_muon.setOnClickListener(this)
        btn_dang_nhap.setOnClickListener(this)
        tool_bar.setOnClickListener(this)
        imv_empty.setOnClickListener(this)

        initRecycleView()
    }


    override fun initViewModel(viewModel: ViewModel?) {
        mPhieuMuonViewModel = viewModel!! as PhieuMuonViewModel
    }


    @SuppressLint("SetTextI18n")
    override fun initObserver(owner: LifecycleOwner?) {
        mPhieuMuonViewModel.mListPhieuMuonLiveData.observe(owner!!) {
            mListPhieuMuon = it
            checkPhieuMuon(it)
            pg_load_phieu_qua_han.visibility = View.GONE
            pg_load_phieu_dang_muon.visibility = View.GONE
            pg_load_phieu_da_tra.visibility = View.GONE
            layout_phieu_muon.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mActivity = activity!!
        mUser = SharedPrefUtils.getUserData(mContext)!!
        mPhieuMuonViewModel.getListPhieuMuon(mContext, mUser.type, mUser.firebaseId)
        loadView(mUser.type)
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = false
            initDataDefault(activity)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onClick(view: View) {
        when (view) {
            tool_bar -> {
                ed_search_phieu_muon.visibility = View.VISIBLE
                ed_search_phieu_muon.requestFocus()
                tv_title.visibility = View.GONE
                KeyBoardUtils.showKeyboard(mActivity, ed_search_phieu_muon)
            }
            imv_add_new_phieu_muon -> {
                mContext.startActivity(Intent(mContext, AddPhieuMuonActivity::class.java))
            }
            btn_dang_nhap -> {
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }
            imv_empty -> {
                ed_search_phieu_muon.setText("")
            }
        }
    }

    fun loadView(userType: Int) {
        pg_load_phieu_qua_han.visibility = View.VISIBLE
        pg_load_phieu_dang_muon.visibility = View.VISIBLE
        pg_load_phieu_da_tra.visibility = View.VISIBLE
        layout_phieu_muon.visibility = View.GONE
        mPhieuQuaHanAdapter.setListPhieuMuon(ArrayList())
        mPhieuDangMuonAdapter.setListPhieuMuon(ArrayList())
        mPhieuDaTraAdapter.setListPhieuMuon(ArrayList())
        tv_no_data_qua_han.visibility = View.GONE
        tv_no_data_dang_muon.visibility = View.GONE
        tv_no_data_da_tra.visibility = View.GONE
        when (userType) {
            Constant.QUYEN.KHACH -> {
                imv_add_new_phieu_muon.visibility = View.GONE
                layout_phieu_muon.visibility = View.GONE
                layout_no_sign_in.visibility = View.VISIBLE
            }
            Constant.QUYEN.DOC_GIA -> {
                imv_add_new_phieu_muon.visibility = View.GONE
                layout_phieu_muon.visibility = View.VISIBLE
                layout_no_sign_in.visibility = View.GONE
            }
            Constant.QUYEN.ADMIN, Constant.QUYEN.THU_THU -> {
                imv_add_new_phieu_muon.visibility = View.VISIBLE
                layout_phieu_muon.visibility = View.VISIBLE
                layout_no_sign_in.visibility = View.GONE
            }
        }
    }

    private fun checkPhieuMuon(listPhieuMuon: ArrayList<PhieuMuon>) {
        mListPhieuMuonQuaHan.clear()
        mListPhieuMuonDangMuon.clear()
        mListPhieuMuonDaTra.clear()
        val calender = Calendar.getInstance().timeInMillis
        for (phieuMuon in listPhieuMuon) {
            if (!CheckTimeUtils.isToday(phieuMuon.hanTra) && phieuMuon.hanTra < calender && phieuMuon.trangThai != Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
                phieuMuon.trangThai = Constant.PHIEUMUON.TRANGTHAI.QUA_HAN
                mListPhieuMuonQuaHan.add(phieuMuon)
            } else if (phieuMuon.trangThai == Constant.PHIEUMUON.TRANGTHAI.DANG_MUON) {
                mListPhieuMuonDangMuon.add(phieuMuon)
            } else if (phieuMuon.trangThai == Constant.PHIEUMUON.TRANGTHAI.DA_TRA) {
                mListPhieuMuonDaTra.add(phieuMuon)
            }
        }
        if (mListPhieuMuonQuaHan.isEmpty()) {
            tv_no_data_qua_han.visibility = View.VISIBLE
        } else {
            tv_no_data_qua_han.visibility = View.GONE
            mPhieuQuaHanAdapter.setListPhieuMuon(mListPhieuMuonQuaHan)
        }
        if (mListPhieuMuonDangMuon.isEmpty()) {
            tv_no_data_dang_muon.visibility = View.VISIBLE
        } else {
            tv_no_data_dang_muon.visibility = View.GONE
            mPhieuDangMuonAdapter.setListPhieuMuon(mListPhieuMuonDangMuon)
        }
        if (mListPhieuMuonDaTra.isEmpty()) {
            tv_no_data_da_tra.visibility = View.VISIBLE
        } else {
            tv_no_data_da_tra.visibility = View.GONE
            mPhieuDaTraAdapter.setListPhieuMuon(mListPhieuMuonDaTra)
        }
    }

    private fun initRecycleView() {
        mPhieuQuaHanAdapter = AdapterListPhieuQuaHan(mContext, mListPhieuMuonQuaHan) {
            val intent = Intent(mContext, ChiTietPhieuMuonActivity::class.java)
            intent.putExtra(Constant.PHIEUMUON.PHIEUMUON, it)
            mContext.startActivity(intent)
        }

        rcv_list_qua_han.layoutManager = LinearLayoutManager(mContext)
        rcv_list_qua_han.setHasFixedSize(false)
        rcv_list_qua_han.isNestedScrollingEnabled = false
        rcv_list_qua_han.adapter = mPhieuQuaHanAdapter

        mPhieuDangMuonAdapter = AdapterListPhieuDangMuon(mContext, mListPhieuMuonDangMuon) {
            val intent = Intent(mContext, ChiTietPhieuMuonActivity::class.java)
            intent.putExtra(Constant.PHIEUMUON.PHIEUMUON, it)
            mContext.startActivity(intent)
        }

        rcv_list_dang_muon.layoutManager = LinearLayoutManager(mContext)
        rcv_list_dang_muon.setHasFixedSize(false)
        rcv_list_dang_muon.isNestedScrollingEnabled = false
        rcv_list_dang_muon.adapter = mPhieuDangMuonAdapter

        mPhieuDaTraAdapter = AdapterListPhieuDaTra(mContext, mListPhieuMuonQuaHan) {
            val intent = Intent(mContext, ChiTietPhieuMuonActivity::class.java)
            intent.putExtra(Constant.PHIEUMUON.PHIEUMUON, it)
            mContext.startActivity(intent)
        }

        rcv_list_da_tra.layoutManager = LinearLayoutManager(mContext)
        rcv_list_da_tra.setHasFixedSize(false)
        rcv_list_da_tra.isNestedScrollingEnabled = false
        rcv_list_da_tra.adapter = mPhieuDaTraAdapter
    }

    private fun initSearchListener() {
        ed_search_phieu_muon.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                val str = s.toString()
                val listFilter = ArrayList<PhieuMuon>()
                for (phieuMuon in mListPhieuMuon) {
                    if (phieuMuon.maPhieuMuon.contains(str, true) || phieuMuon.tenDocGia.contains(
                            str,
                            true)
                        || phieuMuon.maDocGia.contains(str, true) || phieuMuon.maNhanVien.contains(
                            str,
                            true)
                    ) {
                        listFilter.add(phieuMuon)
                    }
                }
                if (s.isEmpty()) {
                    imv_search.visibility = View.VISIBLE
                    imv_empty.visibility = View.GONE
                    tv_no_data.visibility = View.GONE
                    mPhieuDangMuonAdapter.setListPhieuMuon(mListPhieuMuon)
                } else {
                    imv_empty.visibility = View.VISIBLE
                    imv_search.visibility = View.GONE
                    mPhieuDangMuonAdapter.setListPhieuMuon(listFilter)
                    if (listFilter.isEmpty()) {
                        tv_no_data.visibility = View.VISIBLE
                    } else {
                        tv_no_data.visibility = View.GONE
                    }
                }
            }
        })
    }
}