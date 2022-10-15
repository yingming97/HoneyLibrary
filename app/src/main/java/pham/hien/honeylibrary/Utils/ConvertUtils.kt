package pham.hien.honeylibrary.Utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pham.hien.honeylibrary.Model.SachThue

fun convertListSachThueToString(listSachThue: ArrayList<SachThue>): String {
    val gson = Gson()
    return gson.toJson(listSachThue)
}

fun convertStringToListSachThue(listSachThueString: String): ArrayList<SachThue> {
    val gson = Gson()
    val type = object : TypeToken<ArrayList<SachThue>>() {}.type
    return gson.fromJson(listSachThueString, type)
}