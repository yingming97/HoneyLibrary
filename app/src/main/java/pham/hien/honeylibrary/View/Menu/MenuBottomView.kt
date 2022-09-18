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
    private lateinit var layoutBottomNavigationBackgroundConnerTop: LinearLayout
    private lateinit var layoutBottomNavigationSach: LinearLayout
    private lateinit var imvBottomNavigationSach: ImageView
    private lateinit var tvBottomNavigationSach: TextView
    private lateinit var layoutBottomNavigationPhieuMuon: LinearLayout
    private lateinit var imvBottomNavigationPhieuMuon: ImageView
    private lateinit var tvBottomNavigationPhieuMuon: TextView
    private lateinit var lnlBottomNavigationHoaDon: LinearLayout
    private lateinit var imvBottomNavigationHoaDon: ImageView
    private lateinit var tvBottomNavigationHoaDon: TextView
    private lateinit var layoutBottomNavigationOptions: LinearLayout
    private lateinit var imvBottomNavigationOptions: ImageView
    private lateinit var tvBottomNavigationOptions: TextView
    private lateinit var imvBottomNavigationHome: ImageView
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
        layoutBottomNavigationBackgroundConnerTop =
            rootView.findViewById(R.id.layout_bottom_navigation__background_conner_top)
        layoutBottomNavigationSach =
            rootView.findViewById(R.id.layout_bottom_navigation_sach)
        imvBottomNavigationSach =
            rootView.findViewById(R.id.imv_bottom_navigation_sach)
        tvBottomNavigationSach =
            rootView.findViewById(R.id.tv_bottom_navigation_sach)
        layoutBottomNavigationPhieuMuon =
            rootView.findViewById(R.id.layout_bottom_navigation_phieu)
        imvBottomNavigationPhieuMuon =
            rootView.findViewById(R.id.imv_bottom_navigation_phieu)
        tvBottomNavigationPhieuMuon =
            rootView.findViewById(R.id.tv_bottom_navigation_phieu)
        lnlBottomNavigationHoaDon =
            rootView.findViewById(R.id.layout_bottom_navigation_hoa_don)
        imvBottomNavigationHoaDon =
            rootView.findViewById(R.id.imv_bottom_navigation_hoa_don)
        tvBottomNavigationHoaDon =
            rootView.findViewById(R.id.tv_bottom_navigation_hoa_don)
        layoutBottomNavigationOptions =
            rootView.findViewById(R.id.layout_bottom_navigation_options)
        imvBottomNavigationOptions =
            rootView.findViewById(R.id.imv_bottom_navigation_options)
        tvBottomNavigationOptions =
            rootView.findViewById(R.id.txv_bottom_navigation_options)
        imvBottomNavigationHome =
            rootView.findViewById(R.id.imv_bottom_navigation_home)

        layoutBottomNavigationBackgroundConnerTop.clipToOutline = true
        layoutBottomNavigationSach.setOnClickListener(this)
        layoutBottomNavigationPhieuMuon.setOnClickListener(this)
        lnlBottomNavigationHoaDon.setOnClickListener(this)
        layoutBottomNavigationOptions.setOnClickListener(this)
        imvBottomNavigationHome.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === layoutBottomNavigationSach) {
            onClickItemExploreView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(0)
        } else if (v === layoutBottomNavigationPhieuMuon) {
            onClickItemMeditateView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(1)
        } else if (v === imvBottomNavigationHome) {
            onClickItemHomeView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(2)
        } else if (v === lnlBottomNavigationHoaDon) {
            onClickItemLeaderboardView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(3)
        } else if (v === layoutBottomNavigationOptions) {
            onClickItemOptionsView()
            mBottomMenuBarListener.onSelectItemBottomMenuBar(4)
        }
    }

    fun onClickItemExploreView() {
        imvBottomNavigationSach.isSelected = true
        imvBottomNavigationPhieuMuon.isSelected = false
        imvBottomNavigationHoaDon.isSelected = false
        imvBottomNavigationOptions.isSelected = false
        tvBottomNavigationSach.isSelected = true
        tvBottomNavigationPhieuMuon.isSelected = false
        tvBottomNavigationHoaDon.isSelected = false
        tvBottomNavigationOptions.isSelected = false
    }

    fun onClickItemMeditateView() {
        imvBottomNavigationSach.isSelected = false
        imvBottomNavigationPhieuMuon.isSelected = true
        imvBottomNavigationHoaDon.isSelected = false
        imvBottomNavigationOptions.isSelected = false
        tvBottomNavigationSach.isSelected = false
        tvBottomNavigationPhieuMuon.isSelected = true
        tvBottomNavigationHoaDon.isSelected = false
        tvBottomNavigationOptions.isSelected = false
    }

    fun onClickItemLeaderboardView() {
        imvBottomNavigationSach.isSelected = false
        imvBottomNavigationPhieuMuon.isSelected = false
        imvBottomNavigationHoaDon.isSelected = true
        imvBottomNavigationOptions.isSelected = false
        tvBottomNavigationSach.isSelected = false
        tvBottomNavigationPhieuMuon.isSelected = false
        tvBottomNavigationHoaDon.isSelected = true
        tvBottomNavigationOptions.isSelected = false
    }

    fun onClickItemOptionsView() {
        imvBottomNavigationSach.isSelected = false
        imvBottomNavigationPhieuMuon.isSelected = false
        imvBottomNavigationHoaDon.isSelected = false
        imvBottomNavigationOptions.isSelected = true
        tvBottomNavigationSach.isSelected = false
        tvBottomNavigationPhieuMuon.isSelected = false
        tvBottomNavigationHoaDon.isSelected = false
        tvBottomNavigationOptions.isSelected = true
    }

    fun onClickItemHomeView() {
        imvBottomNavigationSach.isSelected = false
        imvBottomNavigationPhieuMuon.isSelected = false
        imvBottomNavigationHoaDon.isSelected = false
        imvBottomNavigationOptions.isSelected = false
        tvBottomNavigationSach.isSelected = false
        tvBottomNavigationPhieuMuon.isSelected = false
        tvBottomNavigationHoaDon.isSelected = false
        tvBottomNavigationOptions.isSelected = false
    }

    interface BottomMenuBarListener {
        fun onSelectItemBottomMenuBar(position: Int)
    }
}