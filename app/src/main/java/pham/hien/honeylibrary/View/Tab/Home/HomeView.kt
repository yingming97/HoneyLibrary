package pham.hien.honeylibrary.View.Tab.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
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
import pham.hien.honeylibrary.Model.*
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.Utils.date2String
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.Home.Adapter.AdapterListSachHome
import pham.hien.honeylibrary.View.Tab.Home.Adapter.HomeAdapter
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.ViewModel.Main.HomeViewModel
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeView : BaseView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false
    private lateinit var viewpager2: ViewPager2
    private lateinit var handlers: Handler
    private lateinit var iconHours: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvThongKeLuotMuon: TextView
    private lateinit var tvThongKeTongSoSach: TextView
    private lateinit var tvThongKeThanhVien: TextView

    private val formatter = SimpleDateFormat("EEE, d MMM y", Locale("vi"))
    private lateinit var rcvListSachThue: RecyclerView
    private lateinit var adapterSachThue: AdapterListSachHome
    private var listLuotMuon = ArrayList<DoanhThu>()
    private var listThanhVien = ArrayList<UserModel>()
    private var listSach = ArrayList<Sach>()

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
        tvThongKeLuotMuon = findViewById(R.id.tv_thong_ke_luot_muon)
        tvThongKeThanhVien = findViewById(R.id.tv_thong_ke_thanh_vien)
        tvThongKeTongSoSach = findViewById(R.id.tv_thong_ke_tong_so_sach)
        rcvListSachThue = findViewById(R.id.top_sach_moi_ve)
        tvUserName = findViewById(R.id.tv_user_name)
        viewpager2 = findViewById(R.id.lv_sach_muon_nhieu)
        iconHours = findViewById(R.id.imv_sun)
        val tvTime = findViewById<TextView>(R.id.tv_time_today)
        handlers = Handler(Looper.myLooper()!!)
        val arrFlipData: ArrayList<ViewFlipper> = arrayListOf()
        arrFlipData.add(ViewFlipper(R.drawable.img_sach_demo,
            "Đắc nhân tâm là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại."))
        arrFlipData.add(ViewFlipper(R.drawable.img_sach_demo,
            "Đắc nhân tâm là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại."))

        val homeAdapter = HomeAdapter(arrFlipData, viewpager2)
        viewpager2.adapter = homeAdapter
        viewpager2.offscreenPageLimit = 3
        viewpager2.clipChildren = false
        viewpager2.clipToPadding = false
        viewpager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setupTransform()
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlers.removeCallbacks(runnable)
                handlers.postDelayed(runnable, 5000)
            }
        })

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
        initRecycleViewSachThue()
    }


    override fun initViewModel(viewModel: ViewModel?) {
        homeViewModel = viewModel as HomeViewModel
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun initObserver(owner: LifecycleOwner?) {
        homeViewModel.mListSachLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                listSach = it
                adapterSachThue.setListSach(it)
                tvThongKeTongSoSach.text = it.size.toString()
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
        if (!isOpening) {
            handlers.removeCallbacks(runnable)
        } else {
            handlers.postDelayed(runnable, 5000)
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        homeViewModel.getListSach()
        homeViewModel.getListDoanhThu()
        homeViewModel.getListDocGia()
        updateUserLogin(SharedPrefUtils.getUserData(mContext)!!)
    }

    fun initRecycleViewSachThue() {
        adapterSachThue = AdapterListSachHome(mContext, listSach) {
            val intent = Intent(mContext, ChiTietSachActivity::class.java)
            intent.putExtra(Constant.SACH.SACH,it)
            mContext.startActivity(intent)
        }
        rcvListSachThue.layoutManager = LinearLayoutManager(mContext)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = adapterSachThue
    }

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
            checkFirstLaunchView = true
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
        }
    }

    private fun setupTransform() {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(40))
        transform.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewpager2.setPageTransformer(transform)
    }

    val isOpening: Boolean
        get() = visibility == NestedScrollView.VISIBLE

    private var runnable = Runnable {
        viewpager2.currentItem = viewpager2.currentItem + 1
    }
}