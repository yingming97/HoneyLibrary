package pham.hien.honeylibrary.View.Menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.bottom_navigation_view.view.*
import pham.hien.honeylibrary.R

class MenuBottomView : RelativeLayout, View.OnClickListener {

    private val mContext: Context
    private var mBottomMenuBarListener: BottomMenuBarListener? = null

    fun setListener(BottomMenuBarListener: BottomMenuBarListener?) {
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

        layout_bottom_navigation__background_conner_top!!.clipToOutline = true
        layout_bottom_navigation_explore!!.setOnClickListener(this)
        layout_bottom_navigation_meditate!!.setOnClickListener(this)
        layout_bottom_navigation_leaderboard!!.setOnClickListener(this)
        layout_bottom_navigation_options!!.setOnClickListener(this)
        imv_bottom_navigation_home!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            layout_bottom_navigation_explore -> {
                onClickItemExploreView()
                mBottomMenuBarListener!!.onSelectItemBottomMenuBar(0)
            }
            layout_bottom_navigation_meditate -> {
                onClickItemMeditateView()
                mBottomMenuBarListener!!.onSelectItemBottomMenuBar(1)
            }
            imv_bottom_navigation_home -> {
                onClickItemHomeView()
                mBottomMenuBarListener!!.onSelectItemBottomMenuBar(2)
            }
            layout_bottom_navigation_leaderboard -> {
                onClickItemLeaderboardView()
                mBottomMenuBarListener!!.onSelectItemBottomMenuBar(3)
            }
            layout_bottom_navigation_options -> {
                onClickItemOptionsView()
                mBottomMenuBarListener!!.onSelectItemBottomMenuBar(4)
            }
        }
    }

    fun onClickItemExploreView() {
        imv_bottom_navigation_explore!!.isSelected = true
        imv_bottom_navigation_meditate!!.isSelected = false
        imv_bottom_navigation_leaderboard!!.isSelected = false
        imv_bottom_navigation_options!!.isSelected = false
        tv_bottom_navigation_explore!!.isSelected = true
        tv_bottom_navigation_meditate!!.isSelected = false
        tv_bottom_navigation_leaderboard!!.isSelected = false
        txv_bottom_navigation_options!!.isSelected = false
    }

    fun onClickItemMeditateView() {
        imv_bottom_navigation_explore!!.isSelected = false
        imv_bottom_navigation_meditate!!.isSelected = true
        imv_bottom_navigation_leaderboard!!.isSelected = false
        imv_bottom_navigation_options!!.isSelected = false
        tv_bottom_navigation_explore!!.isSelected = false
        tv_bottom_navigation_meditate!!.isSelected = true
        tv_bottom_navigation_leaderboard!!.isSelected = false
        txv_bottom_navigation_options!!.isSelected = false
    }

    fun onClickItemLeaderboardView() {
        imv_bottom_navigation_explore!!.isSelected = false
        imv_bottom_navigation_meditate!!.isSelected = false
        imv_bottom_navigation_leaderboard!!.isSelected = true
        imv_bottom_navigation_options!!.isSelected = false
        tv_bottom_navigation_explore!!.isSelected = false
        tv_bottom_navigation_meditate!!.isSelected = false
        tv_bottom_navigation_leaderboard!!.isSelected = true
        txv_bottom_navigation_options!!.isSelected = false
    }

    fun onClickItemOptionsView() {
        imv_bottom_navigation_explore!!.isSelected = false
        imv_bottom_navigation_meditate!!.isSelected = false
        imv_bottom_navigation_leaderboard!!.isSelected = false
        imv_bottom_navigation_options!!.isSelected = true
        tv_bottom_navigation_explore!!.isSelected = false
        tv_bottom_navigation_meditate!!.isSelected = false
        tv_bottom_navigation_leaderboard!!.isSelected = false
        txv_bottom_navigation_options!!.isSelected = true
    }

    fun onClickItemHomeView() {
        imv_bottom_navigation_explore!!.isSelected = false
        imv_bottom_navigation_meditate!!.isSelected = false
        imv_bottom_navigation_leaderboard!!.isSelected = false
        imv_bottom_navigation_options!!.isSelected = false
        tv_bottom_navigation_explore!!.isSelected = false
        tv_bottom_navigation_meditate!!.isSelected = false
        tv_bottom_navigation_leaderboard!!.isSelected = false
        txv_bottom_navigation_options!!.isSelected = false
    }

    interface BottomMenuBarListener {
        fun onSelectItemBottomMenuBar(position: Int)
    }
}