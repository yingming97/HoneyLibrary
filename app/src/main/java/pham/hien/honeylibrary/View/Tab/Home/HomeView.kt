package pham.hien.honeylibrary.View.Tab.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import com.google.firebase.firestore.auth.User
import pham.hien.honeylibrary.Model.*
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.Utils.date2String
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.ViewModel.DoanhThuViewModel
import pham.hien.honeylibrary.ViewModel.Main.HomeViewModel
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import java.lang.Math.abs
import java.time.Duration
import java.util.Calendar
import java.util.Date


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
    private var listLuotMuon = ArrayList<DoanhThu>()
    private var listThanhVien = ArrayList<UserModel>()
    private var listSach = ArrayList<Sach>()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rcvListSachThue: RecyclerView
    private lateinit var adapterSachThue: AdapterListSachQuanLy

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
        arrFlipData.add(ViewFlipper(R.drawable.img_sach_demo, "Đắc nhân tâm là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại."))
        arrFlipData.add(ViewFlipper(R.drawable.img_sach_demo, "Đắc nhân tâm là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại."))

        val homeAdapter = HomeAdapter(arrFlipData, viewpager2)
        viewpager2.adapter = homeAdapter
        viewpager2.offscreenPageLimit = 3
        viewpager2.clipChildren = false
        viewpager2.clipToPadding = false
        viewpager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setupTransform()
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlers.removeCallbacks(runnable)
                handlers.postDelayed(runnable, 5000)
            }
        })

        val calendar = Calendar.getInstance()
        tvTime.text = calendar.time.date2String("E, d MMM")
        when {
            calendar[Calendar.HOUR_OF_DAY] in 6..18 -> {
                iconHours.setImageResource(R.drawable.ic_sun_home)
                tvUserName.text = "Good morning,"
            }

            else->{
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
        homeViewModel.mListSachLiveData.observe(owner !!){
            if(it.isNotEmpty()) {
                listSach = it
                adapterSachThue.setListSach(it)
                tvThongKeLuotMuon.text = it.size.toString()
            }
        }
        homeViewModel.mListDoanhThuLiveData.observe(owner!!) {
            if(it.isNotEmpty()) {
                listLuotMuon = it
                tvThongKeTongSoSach.text = it.size.toString()
            }
        }
        homeViewModel.mListDocGiaLiveData.observe(owner!!) {
            if(it.isNotEmpty()) {
                listThanhVien = it
                tvThongKeThanhVien.text = it.size.toString()
            }
        }
        if(!isOpening){
            handlers.removeCallbacks(runnable)
        }else{
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

    fun initRecycleViewSachThue(){
        adapterSachThue = AdapterListSachQuanLy(mContext, listSach) {
//         val intent = Intent()
//            intent.putExtra(Constant.SACH.SACH,it)
        }
        rcvListSachThue.layoutManager = LinearLayoutManager(mContext)
        rcvListSachThue.setHasFixedSize(false)
        rcvListSachThue.isNestedScrollingEnabled = false
        rcvListSachThue.adapter = adapterSachThue
    }

    private fun updateUserLogin(user: UserModel) {
        if(SharedPrefUtils.getLogin(mContext)){
            tvUserName.text = "${tvUserName.text} ${user.name}"
        }else{
            tvUserName.text = "${tvUserName.text} User"
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

    private fun setupTransform(){
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(40))
        transform.addTransformer{ page, position ->
            val r = 1- abs(position)
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