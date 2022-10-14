package pham.hien.honeylibrary.Utils

import java.text.DecimalFormat

fun moneyFormatter(money: Int): String {
    val formatter = DecimalFormat("###,###,###,###,###")

    return "${formatter.format(money)} đ"

}