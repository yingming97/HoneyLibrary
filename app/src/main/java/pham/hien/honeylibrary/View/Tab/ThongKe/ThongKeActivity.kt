package pham.hien.honeylibrary.View.Tab.ThongKe

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.ThongKeViewModel
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ThongKeActivity : BaseActivity() {

    private lateinit var thongKeViewModel: ThongKeViewModel
    private var listDoanhThu = ArrayList<DoanhThu>()
    private var tongTien: Int = 0
    private lateinit var barChart: BarChart
    private lateinit var barDataSet: BarDataSet
    private lateinit var barData: BarData
    private var barArrayList = ArrayList<BarEntry>()
    val today = Calendar.getInstance()
    var doanhThuTuan = 0
    var doanhThuThang = 0
    var doanhThuTatCa = 0

    override fun getLayout(): Int {
        return R.layout.activity_thong_ke
    }

    override fun initView() {
        initBarChart()
    }

    override fun initListener() {
    }

    override fun initViewModel() {
        thongKeViewModel = ViewModelProvider(this)[ThongKeViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initObserver() {

        thongKeViewModel.mListDoanhThuLiveData.observe(this) {
            listDoanhThu = it
            getDoanhThu(it)
            barArrayList.add(BarEntry(10f, doanhThuThang.toFloat()))
            setBarData(barArrayList)
            Log.d("cccc", "getDoanhThu: thang: ${barArrayList.toString()}")
            initBarChart()
        }
    }

    override fun initDataDefault() {
        thongKeViewModel.getListDoanhThu()
    }

    fun initBarChart() {
        barArrayList.add(BarEntry(1f, 200f))
        barArrayList.add(BarEntry(2f, 500f))
        barArrayList.add(BarEntry(3f, 600f))
        barArrayList.add(BarEntry(4f, 200f))
        barArrayList.add(BarEntry(5f, 700f))
        barArrayList.add(BarEntry(6f, 300f))
        barArrayList.add(BarEntry(7f, 900f))
        barArrayList.add(BarEntry(8f, 700f))
        barArrayList.add(BarEntry(9f, 600f))
        barArrayList.add(BarEntry(11f, 0f))
        barArrayList.add(BarEntry(12f, 0f))

        barChart = findViewById(R.id.bar_chart)
        setBarData(barArrayList)
        barDataSet.setColor(titleColor, 250)
        barDataSet.valueTextColor = titleColor
        barDataSet.valueTextSize = 15f
    }

    private fun setBarData(barArrayList: ArrayList<BarEntry>){
        barDataSet = BarDataSet(barArrayList, "Doanh thu theo tháng")
        barData = BarData(barDataSet)
        barChart.data = barData
    }

    fun getDoanhThu(listDoanhThu: ArrayList<DoanhThu>) {
        for (doanhThu in listDoanhThu) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = doanhThu.time
            if (calendar[Calendar.WEEK_OF_YEAR] == today[Calendar.WEEK_OF_YEAR] && calendar[Calendar.YEAR] == today[Calendar.YEAR]) {
                doanhThuTuan += doanhThu.soTien
//                Log.d("cccc", "getDoanhThu: tuan: ${doanhThuTuan}")
            }
            if (calendar[Calendar.MONTH] == today[Calendar.MONTH] && calendar[Calendar.YEAR] == today[Calendar.YEAR]) {
                doanhThuThang += doanhThu.soTien

            }
            doanhThuTatCa += doanhThu.soTien
//            Log.d("cccc", "getDoanhThu: tatca: ${doanhThuTatCa}")
        }
    }
}