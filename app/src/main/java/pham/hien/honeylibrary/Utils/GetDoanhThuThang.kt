package pham.hien.honeylibrary.Utils

import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.Model.DoanhThu
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet

class GetDoanhThuThang {

    fun getDoanhThu(listDoanhThu:ArrayList<DoanhThu>) {
        val today = Calendar.getInstance()
        var doanhThuTuan = 0
        var doanhThuThang = 0
        var doanhThuTatCa = 0
        for (doanhThu in listDoanhThu) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = doanhThu.time
            if (calendar[Calendar.WEEK_OF_YEAR] == today[Calendar.WEEK_OF_YEAR] && calendar[Calendar.YEAR] == today[Calendar.YEAR]) {
                doanhThuTuan += doanhThu.soTien
            }
            if (calendar[Calendar.MONTH] == today[Calendar.MONTH] && calendar[Calendar.YEAR] == today[Calendar.YEAR]) {
                doanhThuThang += doanhThu.soTien
            }
            doanhThuTatCa += doanhThu.soTien
        }
    }

}