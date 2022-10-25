package pham.hien.honeylibrary.View.Tab.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import pham.hien.honeylibrary.Model.*
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.Utils.convertStringToListSachThue
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.Home.Adapter.AdapterListSachHome
import pham.hien.honeylibrary.View.Tab.Home.Adapter.SachMuonNhieuAdapter
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.ViewModel.Main.HomeViewModel
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet


class HomeView : BaseView {

    private val TAG = "YingMing"
    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false
    private lateinit var viewpager2: ViewPager2
    private lateinit var handlers: Handler
    private lateinit var iconHours: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvThongKeLuotMuon: TextView
    private lateinit var tvThongKeTongSoSach: TextView
    private lateinit var tvThongKeThanhVien: TextView
    private lateinit var indicator: CircleIndicator3

    private val formatter = SimpleDateFormat("EEE, d MMM y", Locale("vi"))
    private lateinit var rcvListSachThue: RecyclerView
    private lateinit var adapterSachThue: AdapterListSachHome
    private lateinit var sachMuonNhieuAdapter: SachMuonNhieuAdapter
    private var listLuotMuon = ArrayList<DoanhThu>()
    private var listThanhVien = ArrayList<UserModel>()
    private var listSach = ArrayList<Sach>()
    private var mListSachMuonNhieu = ArrayList<Sach>()

    private lateinit var homeViewModel: HomeViewModel

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
        val rootView: View = inflater.inflate(R.layout.view_home, this)

        tvThongKeLuotMuon = rootView.findViewById(R.id.tv_thong_ke_luot_muon)
        tvThongKeThanhVien = rootView.findViewById(R.id.tv_thong_ke_thanh_vien)
        tvThongKeTongSoSach = rootView.findViewById(R.id.tv_thong_ke_tong_so_sach)
        rcvListSachThue = rootView.findViewById(R.id.top_sach_moi_ve)
        tvUserName = rootView.findViewById(R.id.tv_user_name)
        viewpager2 = rootView.findViewById(R.id.lv_sach_muon_nhieu)
        iconHours = rootView.findViewById(R.id.imv_sun)
        indicator = rootView.findViewById(R.id.indicator)
        tvTime = rootView.findViewById(R.id.tv_time_today)

    }


    override fun initViewModel(viewModel: ViewModel?) {
        homeViewModel = viewModel as HomeViewModel
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun initObserver(owner: LifecycleOwner?) {
        homeViewModel.mListSachLiveData.observe(owner!!) { list ->
            if (list.isNotEmpty()) {
                listSach.clear()
                mListSachMuonNhieu.clear()
                if (list.size <= 10) {
                    for (i in list.size - 1 downTo 0) {
                        listSach.add(list[i])
                        mListSachMuonNhieu.add(list[i])
                    }
                } else {
                    for (i in list.size - 1 downTo list.size - 10) {
                        listSach.add(list[i])
                        mListSachMuonNhieu.add(list[i])
                    }
                }
                indicator.setViewPager(viewpager2)
                adapterSachThue.setListSach(listSach)
                tvThongKeTongSoSach.text = list.size.toString()
                mListSachMuonNhieu.sortBy { it.soLuongConLai }
                sachMuonNhieuAdapter.setList(mListSachMuonNhieu)

            }
        }
        homeViewModel.mListDoanhThuLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                listLuotMuon = it
                tvThongKeLuotMuon.text = it.size.toString()
            }
        }
        homeViewModel.mListDocGiaLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                listThanhVien = it
                tvThongKeThanhVien.text = it.size.toString()
            }
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        homeViewModel.getListSachMoi()
        homeViewModel.getListDoanhThu()
        homeViewModel.getListDocGia()
        updateUserLogin(SharedPrefUtils.getUserData(mContext)!!)
        setViewPageMuonNhieu()
        initRecycleViewSachThue()
        val calendar = Calendar.getInstance()
        tvTime.text = formatter.format(calendar.timeInMillis)
        when (calendar[Calendar.HOUR_OF_DAY]) {
            in 0..12 -> {
                iconHours.setImageResource(R.drawable.ic_sun_home)
                tvUserName.text = "Good morning,"
            }
            in 12..17 -> {
                iconHours.setImageResource(R.drawable.ic_sun_home)
                tvUserName.text = "Good afternoon,"
            }
            in 17..24 -> {
                iconHours.setImageResource(R.drawable.icon_moon)
                tvUserName.text = "Good evening,"
            }
        }
    }

    private fun initRecycleViewSachThue() {
        adapterSachThue = AdapterListSachHome(mContext, listSach) {
            val intent = Intent(mContext, ChiTietSachActivity::class.java)
            intent.putExtra(Constant.SACH.SACH, it)
            mContext.startActivity(intent)
        }
        rcvListSachThue.layoutManager = LinearLayoutManager(mContext)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = adapterSachThue
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserLogin(user: UserModel) {
        if (SharedPrefUtils.getLogin(mContext)) {
            tvUserName.text = "${tvUserName.text} ${user.name}"
        } else {
            tvUserName.text = "${tvUserName.text}"
        }
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        mActivity = activity!!
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = false
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
        }
    }

    private fun setViewPageMuonNhieu() {
        handlers = Handler(Looper.myLooper()!!)
        if (!isOpening(this)) {
            handlers.removeCallbacks(runnable)
        } else {
            handlers.postDelayed(runnable, 5000)
        }
        sachMuonNhieuAdapter = SachMuonNhieuAdapter(mContext, mListSachMuonNhieu)
        viewpager2.adapter = sachMuonNhieuAdapter
        setupTransform()
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                val intent = Intent(mContext,ChiTietSachActivity::class.java)
//                intent.putExtra(Constant.SACH.SACH,)
                handlers.removeCallbacks(runnable)
                handlers.postDelayed(runnable, 5000)
            }
        })
    }

    private fun setupTransform() {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(80))
        transform.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewpager2.setPageTransformer(transform)
    }

    private var runnable = Runnable {
        if (viewpager2.currentItem == listSach.size - 1) {
            viewpager2.currentItem = 0
        } else {
            viewpager2.currentItem = viewpager2.currentItem + 1
        }
    }
}