package pham.hien.honeylibrary.View.Main

import android.view.View
import androidx.lifecycle.ViewModelProvider
import pham.hien.honeylibrary.Animation.closeViewToLeft
import pham.hien.honeylibrary.Animation.closeViewToRight
import pham.hien.honeylibrary.Animation.openViewFromLeft
import pham.hien.honeylibrary.Animation.openViewFromRight
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Menu.MenuBottomView
import pham.hien.honeylibrary.View.Tab.TheLoai.TheLoaiView
import pham.hien.honeylibrary.View.Tab.Home.HomeView
import pham.hien.honeylibrary.View.Tab.Sach.SachView
import pham.hien.honeylibrary.View.Tab.Option.OptionView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.PhieuMuonView
import pham.hien.honeylibrary.ViewModel.Main.*


class MainActivity : BaseActivity(), MenuBottomView.BottomMenuBarListener {


    private lateinit var layoutHomeView: HomeView
    private lateinit var layoutSachView: SachView
    private lateinit var layoutPhieuMuonView: PhieuMuonView
    private lateinit var layoutTheLoaiView: TheLoaiView
    private lateinit var layoutOptionView: OptionView

    private lateinit var layoutMenuBottomView: MenuBottomView


    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sachViewModel: SachViewModel
    private lateinit var phieuMuonViewModel: PhieuMuonViewModel
    private lateinit var theLoaViewModel: TheLoaViewModel
    private lateinit var optionViewModel: OptionViewModel


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        this.layoutSachView = findViewById(R.id.layout_tab_sach)
        this.layoutPhieuMuonView = findViewById(R.id.layout_tab_phieu_muon)
        this.layoutHomeView = findViewById(R.id.layout_tab_home)
        this.layoutTheLoaiView = findViewById(R.id.layout_tab_hoa_don)
        this.layoutOptionView = findViewById(R.id.layout_tab_option)
        this.layoutMenuBottomView = findViewById(R.id.layoutMenuBottomView)
    }

    override fun initListener() {
        this.layoutMenuBottomView.setListener(this)
    }

    override fun initViewModel() {
        this.mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        this.homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        this.sachViewModel = ViewModelProvider(this)[SachViewModel::class.java]
        this.phieuMuonViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
        this.theLoaViewModel = ViewModelProvider(this)[TheLoaViewModel::class.java]
        this.optionViewModel = ViewModelProvider(this)[OptionViewModel::class.java]

        this.layoutHomeView.initViewModel(homeViewModel)
        this.layoutSachView.initViewModel(sachViewModel)
        this.layoutPhieuMuonView.initViewModel(phieuMuonViewModel)
        this.layoutTheLoaiView.initViewModel(theLoaViewModel)
        this.layoutOptionView.initViewModel(optionViewModel)
    }

    override fun initObserver() {
        this.layoutHomeView.initObserver(this)
        this.layoutSachView.initObserver(this)
        this.layoutPhieuMuonView.initObserver(this)
        this.layoutTheLoaiView.initObserver(this)
        this.layoutOptionView.initObserver(this)
    }

    override fun initDataDefault() {
        this.layoutHomeView.openForTheFirstTime(this)
    }

    override fun onClick(view: View?) {

    }

    override fun onSelectItemBottomMenuBar(position: Int) {
        when (position) {
            Constant.POSITION.VIEW_SACH -> {
                layoutSachView.openForTheFirstTime(this)
                if (!BaseView.isOpening(layoutSachView)) {
                    openViewFromLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    closeViewToRight(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutTheLoaiView)) {
                    closeViewToRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    closeViewToRight(layoutHomeView, layoutHomeView.width, 300)
                }
            }
            Constant.POSITION.VIEW_PHIEU_MUON -> {
                layoutPhieuMuonView.openForTheFirstTime(this)
                if (BaseView.isOpening(layoutSachView)) {
                    openViewFromRight(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutTheLoaiView)) {
                    openViewFromLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                    closeViewToRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
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
                if (BaseView.isOpening(layoutTheLoaiView)) {
                    openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
            }
            Constant.POSITION.VIEW_HOA_DON -> {
                layoutTheLoaiView.openForTheFirstTime(this)
                if (BaseView.isOpening(layoutSachView)) {
                    openViewFromRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    openViewFromRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                    closeViewToLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutOptionView)) {
                    openViewFromLeft(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                    closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    openViewFromRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                    closeViewToLeft(layoutHomeView, layoutHomeView.width, 300)
                }
            }
            Constant.POSITION.VIEW_OPTION -> {
                layoutOptionView.openForTheFirstTime(this)
                if (!BaseView.isOpening(layoutOptionView)) {
                    openViewFromRight(layoutOptionView, layoutOptionView.width, 300)
                }
                if (BaseView.isOpening(layoutSachView)) {
                    closeViewToLeft(layoutSachView, layoutSachView.width, 300)
                }
                if (BaseView.isOpening(layoutPhieuMuonView)) {
                    closeViewToLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
                }
                if (BaseView.isOpening(layoutTheLoaiView)) {
                    closeViewToLeft(layoutTheLoaiView, layoutTheLoaiView.width, 300)
                }
                if (BaseView.isOpening(layoutHomeView)) {
                    closeViewToLeft(layoutHomeView, layoutHomeView.width, 300)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (BaseView.isOpening(layoutSachView)) {
            layoutMenuBottomView.onClickItemHomeView()
            openViewFromRight(layoutHomeView, layoutHomeView.width, 300)
            closeViewToLeft(layoutSachView, layoutSachView.width, 300)
        } else if (BaseView.isOpening(layoutPhieuMuonView)) {
            layoutMenuBottomView.onClickItemHomeView()
            openViewFromRight(layoutHomeView, layoutHomeView.width, 300)
            closeViewToLeft(layoutPhieuMuonView, layoutPhieuMuonView.width, 300)
        } else if (BaseView.isOpening(layoutTheLoaiView)) {
            layoutMenuBottomView.onClickItemHomeView()
            openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
            closeViewToRight(layoutTheLoaiView, layoutTheLoaiView.width, 300)
        } else if (BaseView.isOpening(layoutOptionView)) {
            layoutMenuBottomView.onClickItemHomeView()
            openViewFromLeft(layoutHomeView, layoutHomeView.width, 300)
            closeViewToRight(layoutOptionView, layoutOptionView.width, 300)
        } else {
            finish()
        }
    }

}