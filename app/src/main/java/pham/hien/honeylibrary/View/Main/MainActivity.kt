package pham.hien.honeylibrary.View.Main

import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import pham.hien.honeylibrary.Animation.closeViewToLeft
import pham.hien.honeylibrary.Animation.closeViewToRight
import pham.hien.honeylibrary.Animation.openViewFromLeft
import pham.hien.honeylibrary.Animation.openViewFromRight
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Menu.MenuBottomView
import pham.hien.honeylibrary.View.Tab.HoaDon.HoaDonView
import pham.hien.honeylibrary.View.Tab.Home.HomeView
import pham.hien.honeylibrary.View.Tab.Sach.SachView
import pham.hien.honeylibrary.View.Tab.Option.OptionView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.PhieuMuonView
import pham.hien.honeylibrary.ViewModel.Main.*


class MainActivity : BaseActivity(), MenuBottomView.BottomMenuBarListener {


    private lateinit var layoutHomeView: HomeView
    private lateinit var layoutSachView: SachView
    private lateinit var layoutPhieuMuonView: PhieuMuonView
    private lateinit var layoutHoaDonView: HoaDonView
    private lateinit var layoutOptionView: OptionView

    private lateinit var layoutMenuBottomView: MenuBottomView


    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var exploreViewModel: SachViewModel
    private lateinit var meditateViewModel: PhieuMuonViewModel
    private lateinit var rankingViewModel: HoaDonViewModel
    private lateinit var optionViewModel: OptionViewModel


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        this.layoutSachView = findViewById(R.id.layout_tab_explore)
        this.layoutPhieuMuonView = findViewById(R.id.layout_tab_meditate)
        this.layoutHomeView = findViewById(R.id.layout_tab_home)
        this.layoutHoaDonView = findViewById(R.id.layout_tab_society)
        this.layoutOptionView = findViewById(R.id.layout_tab_option)
        this.layoutMenuBottomView = findViewById(R.id.layout_bottom_navigation_menu)
    }

    override fun initListener() {
        this.layout_bottom_navigation_menu.setListener(this)
    }

    override fun initViewModel() {
        this.mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        this.homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        this.exploreViewModel = ViewModelProvider(this)[SachViewModel::class.java]
        this.meditateViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
        this.rankingViewModel = ViewModelProvider(this)[HoaDonViewModel::class.java]
        this.optionViewModel = ViewModelProvider(this)[OptionViewModel::class.java]

        this.layout_tab_home.initViewModel(homeViewModel)
        this.layout_tab_explore.initViewModel(exploreViewModel)
        this.layout_tab_meditate.initViewModel(meditateViewModel)
        this.layout_tab_society.initViewModel(rankingViewModel)
        this.layout_tab_option.initViewModel(optionViewModel)
    }

    override fun initObserver() {
        this.layout_tab_home.initObserver(this)
        this.layout_tab_meditate.initObserver(this)
        this.layout_tab_society.initObserver(this)
        this.layout_tab_option.initObserver(this)
    }

    override fun initDataDefault() {
        this.layout_tab_home.openForTheFirstTime(this)
    }

    override fun onClick(view: View?) {

    }

    override fun onSelectItemBottomMenuBar(position: Int) {
        when (position) {
            Constant.POSITION.VIEW_EXPLORE -> {
                layoutSachView.openForTheFirstTime(this)
                openViewFromLeft(layoutSachView, layoutSachView.width, 300)
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    closeViewToRight(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutHoaDonView)) {
                    closeViewToRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    closeViewToRight(layoutHomeView, layoutHomeView.width, 300)
                }
            }
            Constant.POSITION.VIEW_MEDITATE -> {
                layoutPhieuMuonView.openForTheFirstTime(this)
                if (BaseView.isOpening(layoutSachView)) {
                    openViewFromRight(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutHoaDonView)) {
                    openViewFromLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    openViewFromLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    openViewFromLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToRight(layoutHomeView, layoutHomeView.width, 300)
                }
            }
            Constant.POSITION.VIEW_HOME -> {
                if (BaseView.isOpening(layoutSachView)) {
                    openViewFromRight(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    openViewFromRight(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutHoaDonView)) {
                    openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
            }
            Constant.POSITION.VIEW_RANKING -> {
                layoutHoaDonView.openForTheFirstTime(this)
                if (BaseView.isOpening(layoutSachView)) {
                    openViewFromRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    openViewFromRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                    closeViewToLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    openViewFromLeft(layoutHoaDonView, layoutHoaDonView.width, 300)
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    openViewFromRight(layoutHoaDonView, layoutHoaDonView.width, 300)
                    closeViewToLeft(layoutHomeView, layoutHomeView.width, 300)
                }
            }
            Constant.POSITION.VIEW_OPTION -> {
                layout_tab_option.openForTheFirstTime(this)
                openViewFromRight(layoutOptionView, layoutOptionView.width, 300)
                if (BaseView.isOpening(layoutSachView)) {
                    closeViewToLeft(layoutSachView,layoutSachView.width,300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    closeViewToLeft(layoutPhieuMuonView,layoutPhieuMuonView.width,300)
                }
                if (BaseView.isOpening(layoutHoaDonView)) {
                    closeViewToLeft(layoutHoaDonView,layoutHoaDonView.width,300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    closeViewToLeft(layoutHomeView,layoutHomeView.width,300)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (BaseView.isOpening(layout_tab_explore)) {
            layout_bottom_navigation_menu.onClickItemHomeView()
            BaseView.closeViewGroup(layout_tab_explore)
            BaseView.openViewGroup(layout_tab_home, 400)
        } else if (BaseView.isOpening(layout_tab_meditate)) {
            layout_bottom_navigation_menu.onClickItemHomeView()
            BaseView.closeViewGroup(layout_tab_meditate)
            BaseView.openViewGroup(layout_tab_home, 400)
        } else if (BaseView.isOpening(layout_tab_society)) {
            layout_bottom_navigation_menu.onClickItemHomeView()
            BaseView.closeViewGroup(layout_tab_society)
            BaseView.openViewGroup(layout_tab_home, 400)
        } else if (BaseView.isOpening(layout_tab_option)) {
            layout_bottom_navigation_menu.onClickItemHomeView()
            BaseView.closeViewGroup(layout_tab_option)
            BaseView.openViewGroup(layout_tab_home, 400)
        } else {
            finish()
        }
    }

}