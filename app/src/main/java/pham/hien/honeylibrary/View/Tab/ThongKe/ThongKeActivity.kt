package pham.hien.honeylibrary.View.Tab.ThongKe

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.listener.BarLineChartTouchListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.yomaster.yogaforbeginner.View.Extention.CheckTimeUtils
import pham.hien.honeylibrary.Extention.CustomBarChartRender
import pham.hien.honeylibrary.Extention.CustomMarkerChartView
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.View.Tab.ThongKe.Adapter.AdapterSachThieu
import pham.hien.honeylibrary.ViewModel.ThongKeViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ThongKeActivity : BaseActivity() {

    private lateinit var imvNextTimeChart: ImageView
    private lateinit var imvLastTimeChart: ImageView
    private lateinit var tvTimeChart: TextView
    private lateinit var toolBar: RelativeLayout

    private lateinit var barChart: BarChart
    private var mYear = 0
    private var mMonth = 0

    private var mWeek = 0
    private var mDayOfMonth = 0
    private var typeChart = ""
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    private var listDoanhThu = ArrayList<DoanhThu>()
    private var listSachThieu = ArrayList<Sach>()
    private lateinit var thongKeViewModel: ThongKeViewModel
    private lateinit var rcvSachThieu: RecyclerView
    private lateinit var adapterListSachThieu: AdapterSachThieu

    override fun getLayout(): Int {
        return R.layout.activity_thong_ke
    }

    override fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        imvNextTimeChart = findViewById(R.id.imv_report_view__next_time_chart)
        imvLastTimeChart = findViewById(R.id.imv_report_view__last_time_chart)
        barChart = findViewById(R.id.bar_chart)
        tvTimeChart = findViewById(R.id.txv_report_view__time_chart)
        rcvSachThieu = findViewById(R.id.rcv_sach_thieu)
        initRecycleViewSachThieu()

        ScreenUtils().setMarginStatusBar(this, toolBar)

    }

    override fun initListener() {
        imvNextTimeChart.setOnClickListener(this)
        imvLastTimeChart.setOnClickListener(this)

    }

    override fun initViewModel() {
        thongKeViewModel = ViewModelProvider(this)[ThongKeViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initObserver() {
        thongKeViewModel.mListDoanhThuLiveData.observe(this) {
            listDoanhThu = it
            initCharView(it)
        }

        thongKeViewModel.mListSachThieuLiveData.observe(this){
            listSachThieu = it
            Log.d("cccc", "initObserver: sach: ${it}")
            adapterListSachThieu.setListSach(it)
        }
    }

    override fun initDataDefault() {
        thongKeViewModel.getListDoanhThu()
        thongKeViewModel.getListSachThieu()
    }

    override fun onClick(view: View?) {
        when (view) {
            imvLastTimeChart -> {
                if (mMonth == 0) {
                    setUpMonthlyChart(11, mYear - 1, listDoanhThu)
                } else {
                    setUpMonthlyChart(mMonth - 1, mYear, listDoanhThu)
                }
            }
            imvNextTimeChart -> {
                if (mMonth == 11) {
                    setUpMonthlyChart(0, mYear + 1, listDoanhThu)
                } else {
                    setUpMonthlyChart(mMonth + 1, mYear, listDoanhThu)
                }
            }
        }
    }

    fun initRecycleViewSachThieu(){
        adapterListSachThieu = AdapterSachThieu(this, listSachThieu)
        rcvSachThieu.layoutManager = LinearLayoutManager(this)
        rcvSachThieu.setHasFixedSize(false)
        rcvSachThieu.isNestedScrollingEnabled = false
        rcvSachThieu.adapter = adapterListSachThieu
    }

    private fun initCharView(listDoanhThu: ArrayList<DoanhThu>) {
        barChart.setDrawBarShadow(false)
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.setScaleEnabled(false)
        barChart.setPinchZoom(false)
        barChart.legend.setDrawInside(false)
        barChart.legend.isEnabled = false
        barChart.setFitBars(true)
        barChart.animateXY(1000, 1000, Easing.EaseInOutExpo)
        barChart.extraBottomOffset = 22f
        barChart.setNoDataText("Loading Data...")
        val p = barChart.getPaint(Chart.PAINT_INFO)
        p.textSize = 22f
        p.color = Color.parseColor("#7265E3")
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.parseColor("#B4B7CC")
        xAxis.yOffset = 20f
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawLabels(true)
        xAxis.setCenterAxisLabels(false)
        xAxis.isGranularityEnabled = true
        xAxis.axisLineColor = Color.parseColor("#D0D0D0")
        xAxis.setDrawAxisLine(false)
        val leftAxis = barChart.axisLeft
        leftAxis.textColor = Color.parseColor("#B4B7CC")
        leftAxis.setLabelCount(4, false)
        leftAxis.xOffset = 16f
        leftAxis.spaceTop = 20f
        leftAxis.axisMinimum = 0f
        leftAxis.axisLineColor = Color.parseColor("#00D0D0D0")
        leftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString() + ""
            }
        }
        val rightAxis = barChart.axisRight
        rightAxis.isEnabled = false
        val calendarToday = Calendar.getInstance()
        setUpMonthlyChart(
            calendarToday[Calendar.MONTH],
            calendarToday[Calendar.YEAR], listDoanhThu
        )
    }

    private fun setUpMonthlyChart(
        month: Int,
        year: Int,
        listDoanhThu: ArrayList<DoanhThu>,
    ) {
        (barChart.onTouchListener as BarLineChartTouchListener).stopDeceleration()
        mMonth = month
        mYear = year
        typeChart = "MONTHLY"
        var totalMinuteWorkout = 0
        tvTimeChart.text =
            CheckTimeUtils.mDecimalFormat.format(month + 1).toString() + "/" + year
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = 0
        calendar[Calendar.MONTH] = month
        calendar[Calendar.YEAR] = year
        val numDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        if (mDayOfMonth > numDaysInMonth) {
            mDayOfMonth = numDaysInMonth - 2
        }
        calendar[Calendar.DAY_OF_MONTH] = mDayOfMonth + 1
        mWeek = calendar[Calendar.WEEK_OF_YEAR]
        val valuesPushUp = java.util.ArrayList<BarEntry>()
        var maxMinute = 0
        val dayOfMonthList = arrayOfNulls<String>(numDaysInMonth)
        val valueDayOfMonthList = IntArray(numDaysInMonth)
        for (i in 0 until numDaysInMonth) {
            val day = CheckTimeUtils.mDecimalFormat.format(i + 1)
                .toString() + "/" + CheckTimeUtils.mDecimalFormat.format(month + 1) + "/" + year
            var valueMoney = 0
            for (doanhThu in listDoanhThu) {
                if (formatter.format(doanhThu.time) == day) {
                    valueMoney += doanhThu.soTien
                }
            }
            dayOfMonthList[i] = (i + 1).toString() + ""
            valueDayOfMonthList[i] = valueMoney
        }
        totalMinuteWorkout /= 60
        for (i in dayOfMonthList.indices.reversed()) {
            valuesPushUp.add(0, BarEntry(i.toFloat(), valueDayOfMonthList[i].toFloat()))
            if (valueDayOfMonthList[i] > maxMinute) {
                maxMinute = valueDayOfMonthList[i]
            }
        }
        if (maxMinute < 20) {
            maxMinute = 20
        }
        barChart.axisLeft.axisMaximum = maxMinute + 100000f
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value.toInt() >= dayOfMonthList.size) {
                    ""
                } else dayOfMonthList[value.toInt()]!!
            }
        }
        val dataSet = BarDataSet(valuesPushUp, "")
        dataSet.color = Color.parseColor("#7265E3")
        dataSet.highLightAlpha = 0
        val data = BarData(dataSet)
        data.barWidth = 0.58f
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })
        data.setValueTextSize(11f)
        data.setValueTextColor(resources.getColor(R.color.transparent))
        data.isHighlightEnabled = true
        val barChartRender = CustomBarChartRender(
            barChart,
            barChart.animator,
            barChart.viewPortHandler
        )
        barChartRender.setRadius(16)
        barChart.renderer = barChartRender
        barChart.fitScreen()
        if (barChart.data != null) {
            barChart.data.clearValues()
        }
        barChart.notifyDataSetChanged()
        barChart.clear()
        barChart.data = data
        barChart.setVisibleXRangeMaximum(7f)
        barChart.isHorizontalScrollBarEnabled = true
        barChart.setTouchEnabled(true)
        barChart.isDragEnabled = true
        val marker: IMarker = CustomMarkerChartView(this, R.layout.market_chart_view)
        barChart.marker = marker
        barChart.setDrawMarkerViews(true)
        if (Calendar.getInstance()[Calendar.MONTH] == month) {
            barChart.moveViewToX(Calendar.getInstance()[Calendar.DAY_OF_MONTH].toFloat())
        } else {
            barChart.moveViewToX(0f)
        }
        barChart.invalidate()
    }
}