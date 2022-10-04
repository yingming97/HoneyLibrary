package pham.hien.honeylibrary.View.Menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import pham.hien.honeylibrary.R

class MenuBottomView : RelativeLayout, View.OnClickListener {

    private val mContext: Context
    private lateinit var layoutBottomNavigationMenu: LinearLayout
    private lateinit var layoutMenuSach: LinearLayout
    private lateinit var imvMenuSach: ImageView
    private lateinit var tvMenuSach: TextView
    private lateinit var layoutMenuPhieuMuon: LinearLayout
    private lateinit var imvMenuPhieuMuon: ImageView
    private lateinit var tvMenuPhieuMuon: TextView
    private lateinit var lnlMenuHoaDon: LinearLayout
    private lateinit var imvMenuHoaDon: ImageView
    private lateinit var tvMenuHoaDon: TextView
    private lateinit var layoutMenuOptions: LinearLayout
    private lateinit var imvMenuOptions: ImageView
    private lateinit var tvMenuOptions: TextView
    private lateinit var imvMenuHome: ImageView
    private lateinit var mBottomMenuBarListener: BottomMenuBarListener

    fun setListener(BottomMenuBarListener: BottomMenuBarListener) {
        mBottomMenuBarListener = BottomMenuBarListener
    }

    constructor(context: Context) : super(context) {
        mContext = context
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        initView()
    }

    private fun initView() {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView = inflater.inflate(R.layout.bottom_navigation_view, this)

        layoutBottomNavigationMenu = rootView.findViewById(R.id.layout_bottom_navigation_menu)
        layoutMenuSach = rootView.findViewById(R.id.layout_menu_sach)
        imvMenuSach = rootView.findViewById(R.id.imv_bottom_navigation_sach)
        tvMenuSach = rootView.findViewById(R.id.tv_bottom_navigation_sach)
        layoutMenuPhieuMuon = rootView.findViewById(R.id.layout_menu_phieu_muon)
        imvMenuPhieuMuon = rootView.findViewById(R.id.imv_bottom_navigation_phieu)
        tvMenuPhieuMuon = rootView.findViewById(R.id.tv_bottom_navigation_phieu)
        lnlMenuHoaDon = rootView.findViewById(R.id.layout_menu_the_loai)
        imvMenuHoaDon = rootView.findViewById(R.id.imv_bottom_navigation_hoa_don)
        tvMenuHoaDon = rootView.findViewById(R.id.tv_bottom_navigation_hoa_don)
        layoutMenuOptions = rootView.findViewById(R.id.layout_menu_options)
        imvMenuOptions = rootView.findViewById(R.id.imv_bottom_navigation_options)
        tvMenuOptions = rootView.findViewById(R.id.txv_bottom_navigation_options)
        imvMenuHome = rootView.findViewById(R.id.imv_bottom_navigation_home)

        layoutBottomNavigationMenu.clipToOutline = true
        layoutMenuSach.setOnClickListener(this)
        layoutMenuPhieuMuon.setOnClickListener(this)
        lnlMenuHoaDon.setOnClickListener(this)
        layoutMenuOptions.setOnClickListener(this)
        imvMenuHome.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === layoutMenuSach) {
            onClickItemExploreView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(0)
        } else if (v === layoutMenuPhieuMuon) {
            onClickItemMeditateView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(1)
        } else if (v === imvMenuHome) {
            onClickItemHomeView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(2)
        } else if (v === lnlMenuHoaDon) {
            onClickItemLeaderboardView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(3)
        } else if (v === layoutMenuOptions) {
            onClickItemOptionsView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(4)
        }
    }

    fun onClickItemExploreView() {
        imvMenuSach.isSelected = true
        imvMenuPhieuMuon.isSelected = false
        imvMenuHoaDon.isSelected = false
        imvMenuOptions.isSelected = false
        tvMenuSach.isSelected = true
        tvMenuPhieuMuon.isSelected = false
        tvMenuHoaDon.isSelected = false
        tvMenuOptions.isSelected = false
    }

    fun onClickItemMeditateView() {
        imvMenuSach.isSelected = false
        imvMenuPhieuMuon.isSelected = true
        imvMenuHoaDon.isSelected = false
        imvMenuOptions.isSelected = false
        tvMenuSach.isSelected = false
        tvMenuPhieuMuon.isSelected = true
        tvMenuHoaDon.isSelected = false
        tvMenuOptions.isSelected = false
    }

    fun onClickItemLeaderboardView() {
        imvMenuSach.isSelected = false
        imvMenuPhieuMuon.isSelected = false
        imvMenuHoaDon.isSelected = true
        imvMenuOptions.isSelected = false
        tvMenuSach.isSelected = false
        tvMenuPhieuMuon.isSelected = false
        tvMenuHoaDon.isSelected = true
        tvMenuOptions.isSelected = false
    }

    fun onClickItemOptionsView() {
        imvMenuSach.isSelected = false
        imvMenuPhieuMuon.isSelected = false
        imvMenuHoaDon.isSelected = false
        imvMenuOptions.isSelected = true
        tvMenuSach.isSelected = false
        tvMenuPhieuMuon.isSelected = false
        tvMenuHoaDon.isSelected = false
        tvMenuOptions.isSelected = true
    }

    fun onClickItemHomeView() {
        imvMenuSach.isSelected = false
        imvMenuPhieuMuon.isSelected = false
        imvMenuHoaDon.isSelected = false
        imvMenuOptions.isSelected = false
        tvMenuSach.isSelected = false
        tvMenuPhieuMuon.isSelected = false
        tvMenuHoaDon.isSelected = false
        tvMenuOptions.isSelected = false
    }

    interface BottomMenuBarListener {
        fun onSelectItemBottomMenuBar(position: Int)
    }
}